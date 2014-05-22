package org.escoladeltreball.shooter2d;

/*
 * This file is part of shooter2d, a cenital shooter 2D game.
 *
 * Copyright (C) 2014	
 * 						Elvis Puertas <epuertas@gmail.com>
 *						Jaume Ribas <r.ribas.jaume@gmail.com>
 *						Carlos Serrano <arquak@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.physics.BodyFactory;

import android.content.Context;

public class MapCreator {

	private static final int TILE_SIZE = 32;

	/**
	 * Devuelve el mapa preparado para ser mostrado en la pantalla
	 * @param assets
	 * @param texture
	 * @param vbo
	 * @return el mapa cargado para ser añadido a la scene
	 */

	public static TMXTiledMap loadMap(String mapPath, Engine engine, Context context, BoundCamera camera){

		TMXTiledMap mTMXTiledMap = null;
		try {
			final TMXLoader tmxLoader = new TMXLoader(context.getAssets(), engine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, engine.getVertexBufferObjectManager());
			mTMXTiledMap = tmxLoader.loadFromAsset(mapPath);
			mTMXTiledMap.setOffsetCenter(0, 0);
		} catch (final TMXLoadException e) {
			Debug.e(e);
		}
		// La camara no execede el tamaño del mapa
		final TMXLayer tmxLayer = mTMXTiledMap.getTMXLayers().get(0);
		camera.setBounds(0, 0, tmxLayer.getWidth(), tmxLayer.getHeight());
		camera.setBoundsEnabled(true);
		return mTMXTiledMap;
	}
	
	/**
	 * Crea las paredes de un mapa
	 * 
	 * @param wallsGroup
	 * @param map
	 * @param vbo
	 */
	public static void createWalls(TMXObjectGroup wallsGroup, TMXTiledMap map, VertexBufferObjectManager vbo) {
		if(wallsGroup.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
			// This is our "wall" layer. Create the boxes from it
			for(final TMXObject object : wallsGroup.getTMXObjects()) {
				Rectangle rect = new Rectangle(object.getX(), map.getHeight()-object.getHeight()-object.getY(), object.getWidth()+TILE_SIZE, object.getHeight()+TILE_SIZE, vbo);
				rect.setOffsetCenter(0, 0);
				BodyFactory.createRectangleWallBody(rect);
			}
		}
	}
}
