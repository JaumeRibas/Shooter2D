package org.escoladeltreball.shooter2d;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
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
	
	//añadir aqui los recursos del juego
	
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
		//Se usa el texture atlas porque es mas eficiente
		BuildableBitmapTextureAtlas analogControlTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 250, 150);
		
		//la base del analog stick
		this.analogControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  "gfx/onscreen_control_base.png");			
		//el centro del analog stick
		this.analogControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  "gfx/onscreen_control_knob.png");
		
		//cargar aqui el resto de texturas del juego

		try {
			analogControlTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			analogControlTextureAtlas.load();
		} catch (TextureAtlasBuilderException e) {
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
		//el atlas de las texturas del analog control
		((BuildableBitmapTextureAtlas)this.analogControlBaseTextureRegion.getTexture()).unload();
		
		//descargar aqui las texturas
		
		//llamamos al garbage collector
		System.gc();
	}
}
