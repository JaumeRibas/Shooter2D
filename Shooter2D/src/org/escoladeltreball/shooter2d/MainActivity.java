package org.escoladeltreball.shooter2d;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.BaseGameActivity;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.physics.GameContactListener;
import org.escoladeltreball.shooter2d.scenes.FirstLevel;
import org.escoladeltreball.shooter2d.scenes.GameScene;
import org.escoladeltreball.shooter2d.scenes.MainMenuScene;
import org.escoladeltreball.shooter2d.scenes.SplashScreen;
import org.escoladeltreball.shooter2d.ui.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;

public class MainActivity extends BaseGameActivity {
	
	public static MainActivity activity;
	public BoundCamera camera;
	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	public static final int STEPS_PER_SECOND = 60;

	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int VELOCITY_INTERACTIONS = 8;
	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int POSITION_INTERACTIONS = 3;

	public FixedStepPhysicsWorld mPhysicsWorld;
	private Player player;

	private GameScene firstLevel;
	private MainMenuScene menuScene;
	private SplashScreen splashScreen;

	private boolean isGameSaved;
	private boolean populateFinished = false;

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		activity = this;
		return new FixedStepEngine(pEngineOptions, STEPS_PER_SECOND);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		checkCompatibilityMultiTouch();
		camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().getConfigChooserOptions()
				.setRequestedMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		ResourceManager.getInstance().loadGameTextures(mEngine, this);
		ResourceManager.getInstance().loadMusic(mEngine, this);
		ResourceManager.getInstance().musicIntro.play();
		ResourceManager.getInstance().loadFonts(mEngine, this);
		MapCreator.loadMap(mEngine, this, this.camera, player);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		this.firstLevel = new FirstLevel();
		this.menuScene = new MainMenuScene(this.camera, mEngine, this);
		this.splashScreen = new SplashScreen(mEngine);
		pOnCreateSceneCallback.onCreateSceneFinished(this.splashScreen);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		//populate splash
		this.splashScreen.populate();
		//populate menu
		this.menuScene.populate(); 
		this.mPhysicsWorld = new FixedStepPhysicsWorld(MainActivity.STEPS_PER_SECOND,
				new Vector2(0f, 0), false, VELOCITY_INTERACTIONS,
				POSITION_INTERACTIONS);
		this.mPhysicsWorld.setContactListener(GameContactListener.getInstance());
		BodyFactory.setPhysicsWorld(this.mPhysicsWorld);
		//populate primer nivel
		this.firstLevel.populate();
		//Cuando se termina de cargar se abre el menu principal
		if (this.splashScreen.getAnimationFinished())
			this.openMainMenu();
		this.populateFinished = true;
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	/**
	 * Comprueba si existe compatibilidad con multitouch
	 */
	public void checkCompatibilityMultiTouch() {
		if (MultiTouch.isSupported(this)) {
			if (!MultiTouch.isSupportedDistinct(this)) {
				Toast.makeText(
						this,
						"MultiTouch detected, but your device has problems distinguishing between fingers.",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this,
					"Sorry your device does NOT support MultiTouch!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		if (this.isGameLoaded()) {
			// Pausa la reproducción de la música en caso de estar
			// reproduciendose
			if (ResourceManager.getInstance().musicIntro != null
					&& ResourceManager.getInstance().musicIntro.isPlaying()) {
				ResourceManager.getInstance().musicIntro.pause();
			}
		}
		//saveGame();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		System.gc();
		if (this.isGameLoaded()) {
			// Reanuda la reproducción de la música
			if (ResourceManager.getInstance().musicIntro != null) {
				ResourceManager.getInstance().musicIntro.play();
			}
		}
	}

	/**
	 * Mueve la aplicación a segundo plano al pulsar el botón atrás
	 */
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
		//saveGame();
		// Pausa la reproducción de la música en caso de estar reproduciendose
		if (ResourceManager.getInstance().musicIntro != null
				&& ResourceManager.getInstance().musicIntro.isPlaying()) {
			ResourceManager.getInstance().musicIntro.pause();
		}
	}

	public void saveGame() {
		// Prepara el archivo sharedPreferences
		SharedPreferences settings = getSharedPreferences("dbJuego",
				Context.MODE_PRIVATE);
		// Escribe datos
		Editor edit = settings.edit();
		edit.putFloat("posXPlayer", player.getX());
		edit.putFloat("posYPlayer", player.getY());
		edit.apply();
		this.isGameSaved = true;
	}

	public void loadGame() {
		// Prepara el archivo sharedPreferences
		SharedPreferences settings = getSharedPreferences("dbJuego",
				Context.MODE_PRIVATE);
		// Lee datos
		float x = settings.getFloat("posXPlayer", 50);
		float y = settings.getFloat("posYPlayer", 50);
		if (!(x >= 0 || y >= 0)) {
			player.setX(x);
			player.setY(y);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//loadGame();
	}	
	
	public void startGame() {
		mEngine.setScene((Scene) this.firstLevel);
		this.camera.setHUD(UI.getHUD());
	}
	
	public static MainActivity getInstance() {
		return activity;
	}

	public void openMainMenu() {
		mEngine.setScene(this.menuScene);		
	}

	public boolean getPopulateFinished() {
		return this.populateFinished;
	}
}

