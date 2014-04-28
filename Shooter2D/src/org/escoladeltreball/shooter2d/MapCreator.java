package org.escoladeltreball.shooter2d;

import java.util.ArrayList;

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
import org.andengine.util.adt.color.Color;
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
	public Scene loadMap(AssetManager assets, TextureManager texture, VertexBufferObjectManager vbo, Scene scene, BoundCamera camera){
		TMXTiledMap mTMXTiledMap = null;
		try {
			final TMXLoader tmxLoader = new TMXLoader(assets, texture, TextureOptions.BILINEAR_PREMULTIPLYALPHA, vbo);
			mTMXTiledMap = tmxLoader.loadFromAsset("tmx/base.tmx");
			mTMXTiledMap.setOffsetCenter(0, 0);
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		scene.attachChild(mTMXTiledMap);

		// Muestra sobre el mapa rectangulos que son areas de colision
		ArrayList<Rectangle> rectangles = createUnwalkableObjects(mTMXTiledMap, vbo, scene);
		for (Rectangle rect : rectangles){
			scene.attachChild(rect);
		}

		// La camara no execede el tamaño del mapa
		final TMXLayer tmxLayer = mTMXTiledMap.getTMXLayers().get(0);
		camera.setBounds(0, 0, tmxLayer.getWidth(), tmxLayer.getHeight());
		camera.setBoundsEnabled(true);
		return scene;
	}

	/**
	 * Crea los rectangulos que no podrán ser atravesados
	 * @param map
	 * @param vbo
	 * @return
	 */
	public ArrayList<Rectangle> createUnwalkableObjects(TMXTiledMap map, VertexBufferObjectManager vbo, Scene scene){
		// Loop through the object groups
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		for(final TMXObjectGroup group: map.getTMXObjectGroups()) {
			if(group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
				// This is our "wall" layer. Create the boxes from it
				for(final TMXObject object : group.getTMXObjects()) {
					final Rectangle rect = new Rectangle(object.getX(), object.getY(), object.getWidth(), object.getHeight(), vbo);
					rect.setVisible(true);
					rect.setColor(Color.RED);
					rectangles.add(rect);
				}
			}
		}
		return rectangles;
	}

}
