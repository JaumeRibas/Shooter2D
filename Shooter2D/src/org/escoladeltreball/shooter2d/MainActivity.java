package org.escoladeltreball.shooter2d;

/*
 * This file is part of shooter2d, a cenital shooter 2D game.
 *
 * Copyright (C) 2014	
 * 						Elvis Puertas <epuertas@gmail.com>
 *						Jaume Ribas <r.ribas.jaume@gmail.com>
 *						Carlos Serrano <arquak@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.engine.FixedStepEngine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.ui.activity.BaseGameActivity;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.physics.GameContactListener;
import org.escoladeltreball.shooter2d.scenes.FirstLevel;
import org.escoladeltreball.shooter2d.scenes.Level;
import org.escoladeltreball.shooter2d.scenes.PauseMenuScene;
import org.escoladeltreball.shooter2d.scenes.RetryMenuScene;
import org.escoladeltreball.shooter2d.scenes.SplashScreen;
import org.escoladeltreball.shooter2d.scenes.StartMenuScene;
import org.escoladeltreball.shooter2d.ui.UI;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;

public class MainActivity extends BaseGameActivity {
	
	public static MainActivity activity;
	public BoundCamera camera;
	public static final int CAMERA_WIDTH = 840;
	public static final int CAMERA_HEIGHT = 480;
	public static final int STEPS_PER_SECOND = 60;

	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int VELOCITY_INTERACTIONS = 8;
	/** se usa al crear el FixedStepPhysicsWorld */
	private static final int POSITION_INTERACTIONS = 3;

	public FixedStepPhysicsWorld mPhysicsWorld;
	private Player player;

	private FirstLevel firstLevel;
	private StartMenuScene startMenuScene;
	private PauseMenuScene pauseMenuScene;
	private RetryMenuScene retryMenuScene;
	private SplashScreen splashScreen;

	private boolean isGameSaved;
	private boolean populateFinished = false;
	private Level currentLevel;
	private HUD currentHUD;
	public static Vibrator vibrator;


	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		activity = this;
		return new FixedStepEngine(pEngineOptions, STEPS_PER_SECOND);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
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
		this.firstLevel = new FirstLevel();
		this.currentLevel = this.firstLevel;
		ResourceManager.getInstance().loadGameTextures(mEngine, this);
		ResourceManager.getInstance().loadMusic(mEngine, this);
		ResourceManager.getInstance().loadSounds(mEngine, this);
		ResourceManager.getInstance().musicIntro.play();
		ResourceManager.getInstance().loadFonts(mEngine, this);
		this.firstLevel.setMap(MapCreator.loadMap(FirstLevel.MAP_PATH, mEngine, this, this.camera));
		this.vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) {
		this.firstLevel.createScene();
		this.startMenuScene = new StartMenuScene(this.camera, mEngine, this, GameManager.getInstance());
		this.pauseMenuScene = new PauseMenuScene(this.camera, mEngine, this, GameManager.getInstance());
		this.retryMenuScene = new RetryMenuScene(this.camera, mEngine, this, GameManager.getInstance());
		this.splashScreen = new SplashScreen(mEngine);
		pOnCreateSceneCallback.onCreateSceneFinished(this.splashScreen);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		//populate splash
		this.splashScreen.populate();
		//populate menu de inicio
		this.startMenuScene.populate(); 
		this.mPhysicsWorld = new FixedStepPhysicsWorld(MainActivity.STEPS_PER_SECOND,
				new Vector2(0f, 0), false, VELOCITY_INTERACTIONS,
				POSITION_INTERACTIONS);
		this.mPhysicsWorld.setContactListener(GameContactListener.getInstance());
		BodyFactory.setPhysicsWorld(this.mPhysicsWorld);
		// Añade la UI
		UI.getInstance().createUI(MainActivity.getInstance().camera, getVertexBufferObjectManager(), this);
		//crea el player TIENE QUE ESTAR EN ESTA POSICIÓN SI NO PETA AUN NO SE PORQUE
		this.player = PlayerLoader.loadPlayer((float)(MainActivity.CAMERA_WIDTH / 2.0), (float)(MainActivity.CAMERA_HEIGHT / 2.0), mEngine);
		// Se pone a la UI como observador del player 
		this.player.addGameObserver(UI.getInstance());
		// Se pone al GameManager como observador del player 
		this.player.addGameObserver(GameManager.getInstance());
		//populate primer nivel
		this.firstLevel.setPlayer(this.player);
		this.firstLevel.populate();
		//populate menu de pausa
		this.pauseMenuScene.populate();
		//populate menu de reinicio
		this.retryMenuScene.populate();
		//Cuando se termina de cargar se abre el menu principal
		if (this.splashScreen.getAnimationFinished())
			this.openMenu();
		this.populateFinished = true;
		pOnPopulateSceneCallback.onPopulateSceneFinished();
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

//	public void saveGame() {
//		// Prepara el archivo sharedPreferences
//		SharedPreferences settings = getSharedPreferences("dbJuego",
//				Context.MODE_PRIVATE);
//		// Escribe datos
//		Editor edit = settings.edit();
//		edit.putFloat("posXPlayer", player.getX());
//		edit.putFloat("posYPlayer", player.getY());
//		edit.apply();
//		this.isGameSaved = true;
//	}
//
//	public void loadGame() {
//		// Prepara el archivo sharedPreferences
//		SharedPreferences settings = getSharedPreferences("dbJuego",
//				Context.MODE_PRIVATE);
//		// Lee datos
//		float x = settings.getFloat("posXPlayer", 50);
//		float y = settings.getFloat("posYPlayer", 50);
//		if (!(x >= 0 || y >= 0)) {
//			player.setX(x);
//			player.setY(y);
//		}
//	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//loadGame();
	}	
	
	public void openGame() {
		mEngine.setScene(this.currentLevel.getScene());
		this.camera.setHUD(this.currentHUD);
	}
	
	public static MainActivity getInstance() {
		return activity;
	}

	public void openMenu() {
		if (GameManager.getInstance().isFinished()) {
			mEngine.setScene(this.retryMenuScene);
			//quitamos el hud
			this.camera.setHUD(null);
		} else if (GameManager.getInstance().isStarted()) {
			mEngine.setScene(this.pauseMenuScene);
			//quitamos el hud
			this.camera.setHUD(null);
		} else {
			mEngine.setScene(this.startMenuScene);
		}
	}

	public boolean getPopulateFinished() {
		return this.populateFinished;
	}

	public void closeActivity() {
		android.os.Process.killProcess(android.os.Process.myPid());		
	}

	public void setCurrentHUD(HUD hud) {
		this.currentHUD = hud;		
		if (mEngine.getScene() == this.currentLevel.getScene()) {
			this.camera.setHUD(this.currentHUD);
		}
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_MENU) {
	    	if (mEngine.getScene() == this.currentLevel.getScene()) {
				openMenu();
			}		
	        return true;
	    }
	    return super.onKeyUp(keyCode, event);
	}
	
	/**
	 * Abre el menu o cierra la activity si el menu ya esta abierto
	 */
	@Override
	public void onBackPressed() {
		moveTaskToBack (true);
	}

	public Level getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}
}

