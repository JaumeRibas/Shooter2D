package org.escoladeltreball.shooter2d.entities.loader;

import java.io.IOException;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.res.AssetManager;

/**
 * La clase abstracta se encarga cargar los recursos de las entidades.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public class EntityLoader {
	private AssetBitmapTexture mGameEntityTexture;
	private TiledTextureRegion mGameEntityTextureRegion;

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
	public TiledTextureRegion loadResources(TextureManager textureManger, AssetManager assets, String spritePath, int spriteCols, int spriteRows) throws IOException{
		this.mGameEntityTexture = new AssetBitmapTexture(textureManger, assets, spritePath);
		this.mGameEntityTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mGameEntityTexture, spriteCols, spriteRows);
		this.mGameEntityTexture.load();
		return mGameEntityTextureRegion;
	}
}
