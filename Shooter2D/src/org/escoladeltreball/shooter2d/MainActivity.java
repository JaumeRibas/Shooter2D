package org.escoladeltreball.shooter2d;

import java.io.IOException;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;

import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity
{
	private BoundCamera camera;
	private UI ui;
	private MapCreator mapCreator;
	private PlayerLoader playerLoader;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	private Player player;


	@Override
	public EngineOptions onCreateEngineOptions()
	{
		checkCompatibilityMultiTouch();
		camera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		this.ui = new UI();
		this.mapCreator = new MapCreator();
		this.playerLoader = new PlayerLoader();
		return engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException
	{
		this.ui.loadResources(this.getTextureManager(), this.getAssets());
		this.playerLoader.loadResources(this.getTextureManager(), this.getAssets());
	}

	@Override
	protected Scene onCreateScene()
	{
		Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		//este listener es un ejemplo. Habria que crear clases a parte que extendieran de IAnalogOnScreenControlListener y se comunicaran con el personaje.
		//Como mínimo dos, una para el analog izquierdo y otra para el derecho,
		//pero se pueden crear más para distintas configuraciones de controles.
		IAnalogOnScreenControlListener ejemploListener = new IAnalogOnScreenControlListener() {
			
			@Override
			public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,
					float pValueX, float pValueY) {
				player.setPosition(player.getX() + pValueX*10, player.getY() + pValueY*10);
			}
			
			@Override
			public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
				// TODO Auto-generated method stub
				
			}
		};
		// Muestra los controles a la pantalla
		scene.setChildScene(this.ui.createAnalogControls(this.camera, this.getVertexBufferObjectManager(), ejemploListener, ejemploListener));
		// Muestra el mapa en la pantalla
		TMXTiledMap map = this.mapCreator.loadMap(getAssets(), getTextureManager(), getVertexBufferObjectManager());
		scene.attachChild(map);
		this.player = playerLoader.loadPlayer(camera,  getTextureManager(), getAssets(), getVertexBufferObjectManager());
		
		// La camara no execede el tama�o del mapa
        final TMXLayer tmxLayer = map.getTMXLayers().get(0);
        this.camera.setBounds(0, 0, tmxLayer.getWidth(), tmxLayer.getHeight());
        this.camera.setBoundsEnabled(true);
		// La camara sigue al jugador
		this.camera.setChaseEntity(player);
		scene.attachChild(player);
		return scene;
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
}
