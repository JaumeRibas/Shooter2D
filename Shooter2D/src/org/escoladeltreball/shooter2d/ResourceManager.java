package org.escoladeltreball.shooter2d;

import java.io.IOException;
import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;
import org.escoladeltreball.shooter2d.constants.SpriteConstants;

import android.content.Context;
import android.content.res.AssetManager;

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

	/** la instancia unica de ResourceManager*/
	public static ResourceManager instance;

	/** la base del analog conrol */
	public ITextureRegion analogControlBaseTextureRegion;
	/** el centro del analog control */
	public ITextureRegion analogControlKnobTextureRegion;
	/** la sprite del player */
	public TiledTextureRegion playerTextureRegion;
	/** la sprite del zombie */
	public TiledTextureRegion zombieTextureRegion;
	/** la sprite de la bala */
	public TiledTextureRegion bulletTextureRegion;
	
	//musica
	public Music musicIntro;
	
	//efectos sonido
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
		try {
			//jugador
			this.playerTextureRegion = loadTiledTextureRegion(engine.getTextureManager(), context.getAssets(), SpriteConstants.PLAYER_SPRITE, SpriteConstants.PLAYER_SPRITE_COLUMNS, SpriteConstants.PLAYER_SPRITE_ROWS);
			
			//zombie
			//cambiar en caso de que el zombie sea una sprite diferente al jugador
			this.zombieTextureRegion = this.playerTextureRegion;
			
			//bala
			this.bulletTextureRegion = loadTiledTextureRegion(engine.getTextureManager(), context.getAssets(), SpriteConstants.BULLET_SPRITE, SpriteConstants.BULLET_SPRITE_COLUMNS, SpriteConstants.BULLET_SPRITE_ROWS);
			
			//analogs
			//Se usa el texture atlas porque es mas eficiente
			BuildableBitmapTextureAtlas analogControlTextureAtlas = new BuildableBitmapTextureAtlas(engine.getTextureManager(), 250, 150);
			//la base del analog stick
			this.analogControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  SpriteConstants.CONTROL_BASE_SPRITE);			
			//el centro del analog stick
			this.analogControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(analogControlTextureAtlas, context,  SpriteConstants.CONTROL_KNOB_SPRITE);

			//cargar aqui el resto de texturas del juego
			analogControlTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 1));
			analogControlTextureAtlas.load();
		} catch (IOException e) {
			Debug.e(e);
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
	
	/**
	 * Carga recursos relacionados con la entidad.
	 * 
	 * @param textureManger un TextureManager
	 * @param assets un AssetManager
	 * @param spritePath un String, la direccion al sprite.
	 * @param spriteCols un integer, las columnas del sprite
	 * @param spriteRows un integer, las filas del sprite
	 * @throws IOException
	 */
	private static TiledTextureRegion loadTiledTextureRegion(TextureManager textureManger, AssetManager assets, String spritePath, int spriteCols, int spriteRows) throws IOException{
		AssetBitmapTexture mGameEntityTexture = new AssetBitmapTexture(textureManger, assets, spritePath);
		TiledTextureRegion mGameEntityTextureRegion = TextureRegionFactory.extractTiledFromTexture(mGameEntityTexture, spriteCols, spriteRows);
		mGameEntityTexture.load();
		return mGameEntityTextureRegion;
	}
}
