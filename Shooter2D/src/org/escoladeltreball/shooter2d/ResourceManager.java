package org.escoladeltreball.shooter2d;

import java.io.IOException;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.debug.Debug;

import android.content.Context;

/**
 * La clase ResourceManager contiene los recursos del juego
 * (sprites, fuentes, sonidos...) y se ocupa de cargalos y 'descargarlos' de memoria.
 * Utiliza el patron singleton.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class ResourceManager {
	/** la instancia unica */
	public static ResourceManager instance;
	
	/** la base del analog conrol */
	public ITextureRegion analogControlBaseTextureRegion;
	/** el centro del analog control */
	public ITextureRegion analogControlKnobTextureRegion;
	
	private ResourceManager(){}
	
	/**
	 * Devuelve la unica instancia de ResourceManager.
	 * Si no existe la crea.
	 * 
	 * @return la unica instancia de ResourceManager
	 */
	public static ResourceManager getInstance() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	/**
	 * Carga las texturas del juego.
	 * 
	 * @param engine 
	 * @param context
	 */
	public synchronized void loadGameTextures(Engine engine, Context context) {
		
		try {
			//la base del analog stick
			AssetBitmapTexture analogControlBaseTexture = new AssetBitmapTexture(engine.getTextureManager(), context.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
			this.analogControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(analogControlBaseTexture);
			analogControlBaseTexture.load();
			
			//el centro del analog stick
			AssetBitmapTexture analogControlKnobTexture = new AssetBitmapTexture(engine.getTextureManager(), context.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
			this.analogControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(analogControlKnobTexture);
			analogControlKnobTexture.load();
			
			
		} catch (IOException e) {
			Debug.e(e);
		}
	}
	
	/**
	 * 'Descarga' las texturas del juego.
	 * 
	 * @param engine 
	 * @param context
	 */
	public synchronized void unloadGameTextures(Engine engine, Context context) {
		//la base del analog control
		this.analogControlBaseTextureRegion.getTexture().unload();
		//el centro del analog control
		this.analogControlKnobTextureRegion.getTexture().unload();
	}
}
