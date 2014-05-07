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
	public static TiledTextureRegion loadResources(TextureManager textureManger, AssetManager assets, String spritePath, int spriteCols, int spriteRows) throws IOException{
		AssetBitmapTexture mGameEntityTexture = new AssetBitmapTexture(textureManger, assets, spritePath);
		TiledTextureRegion mGameEntityTextureRegion = TextureRegionFactory.extractTiledFromTexture(mGameEntityTexture, spriteCols, spriteRows);
		mGameEntityTexture.load();
		return mGameEntityTextureRegion;
	}
}
