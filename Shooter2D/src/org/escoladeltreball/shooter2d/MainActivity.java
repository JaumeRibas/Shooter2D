package org.escoladeltreball.shooter2d;

import java.io.IOException;
import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.BaseGameActivity;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;
import org.escoladeltreball.shooter2d.entities.loader.BulletLoader;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.entities.loader.ZombieLoader;
import org.escoladeltreball.shooter2d.physics.GameContactListener;
import org.escoladeltreball.shooter2d.ui.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MainActivity extends BaseGameActivity {
	private BoundCamera camera;
	private MapCreator mapCreator;
	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	public static final int STEPS_PER_SECOND = 60;

	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int VELOCITY_INTERACTIONS = 8;
	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int POSITION_INTERACTIONS = 3;

	public static FixedStepPhysicsWorld mPhysicsWorld;
	public Body wallBody;
	private Player player;
	private ArrayList<Zombie> zombies;
	private Scene scene;
	private ArrayList<Bullet> bullets;
	private boolean isGameSaved;

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new FixedStepEngine(pEngineOptions, STEPS_PER_SECOND);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		checkCompatibilityMultiTouch();
		camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.setHUD(UI.getHUD());
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
		this.mapCreator = new MapCreator();
		this.zombies = new ArrayList<Zombie>();
		this.bullets = new ArrayList<Bullet>();
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		ResourceManager.getInstance().loadGameTextures(mEngine, this);
		ResourceManager.getInstance().loadMusic(mEngine, this);
		ResourceManager.getInstance().musicIntro.play();
		ResourceManager.getInstance().loadFonts(mEngine);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		this.scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {

		mPhysicsWorld = new FixedStepPhysicsWorld(STEPS_PER_SECOND,
				new Vector2(0f, 0), false, VELOCITY_INTERACTIONS,
				POSITION_INTERACTIONS);
		this.scene.registerUpdateHandler(mPhysicsWorld);
		mPhysicsWorld.setContactListener(GameContactListener.getInstance());

		Bullet playerbullet = BulletLoader.loadBullet(camera, CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2, this.getTextureManager(), this.getAssets(),
				this.getVertexBufferObjectManager(), 0, 3);

		this.player = PlayerLoader.loadPlayer(CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2, getVertexBufferObjectManager(), scene,
				playerbullet);
		// Muestra el mapa en la pantalla
		scene = this.mapCreator.loadMap(getAssets(), getTextureManager(),
				getVertexBufferObjectManager(), scene, this.camera);
		// La camara sigue al jugador
		this.camera.setChaseEntity(player);
		scene.attachChild(player);

		this.zombies.add(ZombieLoader.loadZombie(camera, 50, 100,
				this.getTextureManager(), this.getAssets(),
				this.getVertexBufferObjectManager(), player));
		this.zombies.add(ZombieLoader.loadZombie(camera, 50, 300,
				this.getTextureManager(), this.getAssets(),
				this.getVertexBufferObjectManager(), player));
		for (Zombie zombie : this.zombies) {
			scene.attachChild(zombie);
		}

		// Añade la UI
		UI.getInstance().createUI(this.getVertexBufferObjectManager());
		// Se pone a la UI como observador del player 
		this.player.setGameObserver(UI.getInstance());

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
		saveGame();
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
		saveGame();
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
}
