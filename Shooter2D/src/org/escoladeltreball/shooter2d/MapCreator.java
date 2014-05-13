package org.escoladeltreball.shooter2d;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.escoladeltreball.shooter2d.physics.BodyFactory;

import android.content.Context;
import android.content.res.AssetManager;

public class MapCreator {

	private static final int TILE_SIZE = 32;
	private static TMXTiledMap currentMap;

	/**
	 * Devuelve el mapa preparado para ser mostrado en la pantalla
	 * @param assets
	 * @param texture
	 * @param vbo
	 * @return el mapa cargado para ser añadido a la scene
	 */
	public static void loadMap(Engine engine, Context context, BoundCamera camera){
		TMXTiledMap mTMXTiledMap = null;
		try {
			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), engine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, engine.getVertexBufferObjectManager());
			mTMXTiledMap = tmxLoader.loadFromAsset("tmx/base.tmx");
			mTMXTiledMap.setOffsetCenter(0, 0);
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		// La camara no execede el tamaño del mapa
		final TMXLayer tmxLayer = mTMXTiledMap.getTMXLayers().get(0);
		camera.setBounds(0, 0, tmxLayer.getWidth(), tmxLayer.getHeight());
		camera.setBoundsEnabled(true);
		currentMap = mTMXTiledMap;
	}

	/**
	 * Crea los rectangulos que no podrán ser atravesados y los añade a la scene
	 * @param map
	 * @param vbo 
	 * @return
	 */
	public static void createMapObjects(VertexBufferObjectManager vbo){
		// Loop through the object groups
		for(final TMXObjectGroup group: currentMap.getTMXObjectGroups()) {
			if(group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
				// This is our "wall" layer. Create the boxes from it
				for(final TMXObject object : group.getTMXObjects()) {
					Rectangle rect = new Rectangle(object.getX(), currentMap.getHeight()-object.getHeight()-object.getY(), object.getWidth()+TILE_SIZE, object.getHeight()+TILE_SIZE, vbo);
					rect.setOffsetCenter(0, 0);
					BodyFactory.createRectangleWallBody(rect);
				}
			}
		}
	}

	public static TMXTiledMap getCurrentMap() {
		return currentMap;
	}
}
