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
import org.escoladeltreball.shooter2d.commands.CommandFactory;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;
import org.escoladeltreball.shooter2d.entities.loader.BulletLoader;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.entities.loader.ZombieLoader;
import org.escoladeltreball.shooter2d.physics.GameContactListener;
import org.escoladeltreball.shooter2d.ui.UI;

import android.widget.Toast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;

public class MainActivity extends BaseGameActivity
{
	private BoundCamera camera;
	private MapCreator mapCreator;
	private PlayerLoader playerLoader;
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
	private ZombieLoader zombieLoader;
	private ArrayList<Zombie> zombies;
	private Scene scene;
	private BulletLoader bulletLoader;
	private ArrayList<Bullet> bullets;


	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return new FixedStepEngine(pEngineOptions, STEPS_PER_SECOND);
	}

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		checkCompatibilityMultiTouch();
		camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		camera.setHUD(UI.getHUD());
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getRenderOptions().setDithering(true);
		engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		this.mapCreator = new MapCreator();
		this.playerLoader = new PlayerLoader();
		this.zombieLoader = new ZombieLoader();
		this.bulletLoader = new BulletLoader();
		this.zombies = new ArrayList<Zombie>();
		this.bullets = new ArrayList<Bullet>();
		return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws IOException
	{
		ResourceManager.getInstance().loadGameTextures(mEngine, this);
		ResourceManager.getInstance().loadMusic(mEngine, this);
		ResourceManager.getInstance().musicIntro.play();
		this.playerLoader.loadResources(this.getTextureManager(), this.getAssets());
		this.bulletLoader.loadResources(this.getTextureManager(), this.getAssets());
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		this.scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws IOException {

		mPhysicsWorld = new FixedStepPhysicsWorld(STEPS_PER_SECOND, new Vector2(0f, 0), false, VELOCITY_INTERACTIONS, POSITION_INTERACTIONS);
		this.scene.registerUpdateHandler(mPhysicsWorld);
		mPhysicsWorld.setContactListener(GameContactListener.getInstance());
		this.player = playerLoader.loadPlayer(camera,  getTextureManager(), getAssets(), getVertexBufferObjectManager());
		// Muestra el mapa en la pantalla
		scene = this.mapCreator.loadMap(getAssets(), getTextureManager(), getVertexBufferObjectManager(), scene, this.camera);
		// La camara sigue al jugador
		this.camera.setChaseEntity(player);
		scene.attachChild(player);
		this.zombies.add(zombieLoader.loadZombie(camera, 50, 100, this.getTextureManager(), this.getAssets(), this.getVertexBufferObjectManager(), player));
		this.zombies.add(zombieLoader.loadZombie(camera, 50, 300, this.getTextureManager(), this.getAssets(), this.getVertexBufferObjectManager(), player));
		
		for(Zombie zombie : this.zombies){
			scene.attachChild(zombie);
		}
		this.bullets.add(bulletLoader.loadBullet(camera,500, 100, this.getTextureManager(), this.getAssets(), this.getVertexBufferObjectManager(), 0, 3));
		scene.attachChild(bullets.get(0));
		
		// Añade los controles con commandos a la escena 
		UI.getInstance().createAnalogControls(this.camera, this.getVertexBufferObjectManager(), CommandFactory.getSetPlayerVelocity(this.player), CommandFactory.getDoNothingCommand(), CommandFactory.getDoNothingAnalogCommand(), CommandFactory.getDoNothingCommand());
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	/**
	 * Comprueba si existe compatibilidad con multitouch
	 */
	public void checkCompatibilityMultiTouch(){
		if(MultiTouch.isSupported(this)) {  
			if(!MultiTouch.isSupportedDistinct(this)) { 
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.", Toast.LENGTH_LONG).show();  
			}  
		} else {  
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!", Toast.LENGTH_LONG).show();  
		}  
	}

	@Override
	public synchronized void onPauseGame() {
		super.onPauseGame();
		if (this.isGameLoaded()){
			// Pausa la reproducción de la música en caso de estar reproduciendose
			if(ResourceManager.getInstance().musicIntro != null && ResourceManager.getInstance().musicIntro.isPlaying()){
				ResourceManager.getInstance().musicIntro.pause();
			}
		}
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		System.gc();
		if (this.isGameLoaded()){
			// Reanuda la reproducción de la música
			if(ResourceManager.getInstance().musicIntro != null){
				ResourceManager.getInstance().musicIntro.play();
			}
		}
	}	

	public boolean isBodyContacted(Body pBody, Contact pContact) {
		if (pContact.getFixtureA().getBody().equals(pBody) || pContact.getFixtureB().getBody().equals(pBody)) {
			return true;
		}
		return false;
	}

	public boolean areBodiesContacted(Body pBody1, Body pBody2, Contact pContact) {
		if (pContact.getFixtureA().getBody().equals(pBody1) || 
				pContact.getFixtureB().getBody().equals(pBody1)){
			if (pContact.getFixtureA().getBody().equals(pBody2) || 
					pContact.getFixtureB().getBody().equals(pBody2)) {
				return true;				
			}
		}
		return false;
	}

	/**
	 * Mueve la aplicación a segundo plano al pulsar el botón atrás
	 */
	@Override
	public void onBackPressed() {
		moveTaskToBack(true);
	}
}
