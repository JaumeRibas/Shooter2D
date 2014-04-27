package org.escoladeltreball.shooter2d;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
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

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import android.content.res.AssetManager;

public class MapCreator {

	private PhysicsWorld mPhysicsWorld;

	/**
	 * Devuelve el mapa preparado para ser mostrado en la pantalla
	 * @param assets
	 * @param texture
	 * @param vbo
	 * @return el mapa cargado para ser a√±adido a la scene
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

/**
 * Crea los rectangulos que no podr·n ser atravesados
 * @param map
 * @param vbo
 * @return
 */
	public ArrayList<Rectangle> createUnwalkableObjects(TMXTiledMap map, VertexBufferObjectManager vbo){
		// Loop through the object groups
		ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
		for(final TMXObjectGroup group: map.getTMXObjectGroups()) {
			if(group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
				// This is our "wall" layer. Create the boxes from it
				for(final TMXObject object : group.getTMXObjects()) {
					final Rectangle rect = new Rectangle((float)object.getX(), (float)object.getY(),(float)object.getWidth(), (float)object.getHeight(), vbo);
					rect.setVisible(true);
					rect.setColor(Color.RED);
					rectangles.add(rect);
				}
			}
		}
		return rectangles;
	}

}
