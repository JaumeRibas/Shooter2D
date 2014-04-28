package org.escoladeltreball.shooter2d;

import java.io.IOException;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
	private static final String CONTROL_KNOB_SPRITE = "gfx/onscreen_control_knob.png";
	private static final String CONTROL_BASE_SPRITE = "gfx/onscreen_control_base.png";

	/** la instancia unica */
	public static ResourceManager instance;

	/** la base del analog conrol */
	public ITextureRegion analogControlBaseTextureRegion;
	/** el centro del analog control */
	public ITextureRegion analogControlKnobTextureRegion;
	public Music musicIntro;
	public Sound sound;

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
		this.analogControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  CONTROL_BASE_SPRITE);			
		//el centro del analog stick
		this.analogControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  CONTROL_KNOB_SPRITE);

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

	/**
	 * Carga los sonidos del juego.
	 * 
	 * @param engine
	 * @param context
	 */
	public synchronized void loadSounds(Engine engine, Context context){
		SoundFactory.setAssetBasePath("sfx/");
		try {
			this.sound = SoundFactory.createSoundFromAsset(engine.getSoundManager(), context, "sound.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carga la música del juego.
	 * 
	 * @param engine
	 * @param context
	 */
	public synchronized void loadMusic(Engine engine, Context context){
		MusicFactory.setAssetBasePath("sfx/");
		try {
			this.musicIntro = MusicFactory.createMusicFromAsset(engine.getMusicManager(), context, "intro.mp3");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
