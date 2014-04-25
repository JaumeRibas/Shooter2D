package org.escoladeltreball.shooter2d;

import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import android.content.res.AssetManager;

public class MapCreator {
	
	/**
	 * Devuelve el mapa preparado para ser mostrado en la pantalla
	 * @param assets
	 * @param texture
	 * @param vbo
	 * @return el mapa cargado para ser añadido a la scene
	 */
	public TMXTiledMap loadMap(AssetManager assets, TextureManager texture, VertexBufferObjectManager vbo){
		TMXTiledMap mTMXTiledMap = null;
		try {
			final TMXLoader tmxLoader = new TMXLoader(assets, texture, TextureOptions.BILINEAR_PREMULTIPLYALPHA, vbo);
			mTMXTiledMap = tmxLoader.loadFromAsset("tmx/base.tmx");
			mTMXTiledMap.setOffsetCenter(0, 0);
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		return mTMXTiledMap;
		
		
	}

}
