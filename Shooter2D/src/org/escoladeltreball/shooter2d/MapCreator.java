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
	 * Crea los objetos de la escena, paredes y respawns.
	 * 
	 * @param scene a {@link Scene}
	 * @param engine a {@link Engine}
	 * @param map a {@link TMXTiledMap}
	 * @param vbo a {@link VertexBufferObjectManager}
	 * @param player a {@link ActorEntity} for the enemies to target.
	 */
	public static void createMapObjects(TMXTiledMap map, Scene scene, Engine engine, VertexBufferObjectManager vbo, ActorEntity player){
		// Loop through the object groups
		for(final TMXObjectGroup group: map.getTMXObjectGroups()) {
			if(group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
				// This is our "wall" layer. Create the boxes from it
				for(final TMXObject object : group.getTMXObjects()) {
					Rectangle rect = new Rectangle(object.getX(), map.getHeight()-object.getHeight()-object.getY(), object.getWidth()+TILE_SIZE, object.getHeight()+TILE_SIZE, vbo);
					rect.setOffsetCenter(0, 0);
					BodyFactory.createRectangleWallBody(rect);
				}
			}
			
			if(group.getTMXObjectGroupProperties().containsTMXProperty("respawn", "true")){
				// Creamos cada respawn con sus características.
				for(final TMXObject object : group.getTMXObjects()) {
					long spawnMiliSeconds = 10;
					long spawnAcceleration = 0;
					String unit = null;
					int unitlimit = 1;
					ActorEntity target = player;
					for(TMXProperty property : object.getTMXObjectProperties()){
						String name = property.getName();
						String value = property.getValue();
						if(name.equals("spawntime")){
							System.out.println("Spawntime: " + value);
							spawnMiliSeconds = Long.parseLong(value);
						} else if(name.equals("spawnacceleration")){
							System.out.println("SpawnAcceleration: " + value);
							spawnAcceleration = Long.parseLong(value);
						} else if(name.equals("quantity")){
							System.out.println("UnitLimit: " + value);
							unitlimit = Integer.parseInt(value);
							if(unitlimit < 0){
								spawnMiliSeconds = 2000;
							}
						} else if(name.equals("type")){
							System.out.println("Unit Type: " + value);
							unit = value;
						} else if(name.equals("target")){
							System.out.println("Target: " + value);
							if(value.equals("player")){
								target = player;
							} else if(value.equals("none")){
								target = null;
							}
						}
					}
					
					int posX = object.getX() + map.getTileWidth();
					int posY = (int) (map.getHeight()-object.getHeight()-object.getY()) + map.getTileWidth(); 
					int width = object.getWidth() + 32;
					int heigh = object.getHeight() + 32;
					
					Respawn res = new Respawn(scene, posX, posY, width, heigh, engine, map, target, spawnMiliSeconds, spawnAcceleration, unit, unitlimit);
					res.start();
				}
			}
		}
	}
}
