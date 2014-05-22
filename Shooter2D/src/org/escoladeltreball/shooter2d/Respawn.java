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
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.ZombieSprinter;
import org.escoladeltreball.shooter2d.entities.GameEntity;
import org.escoladeltreball.shooter2d.entities.Zombie;
import org.escoladeltreball.shooter2d.entities.loader.ZombieLoader;

public class Respawn extends Thread {
	
	private Engine engine;
	private Scene scene;
	private ActorEntity player;
	private long spawnMiliSeconds;
	private long spawnAcceleration;
	private Rectangle area;
	private String unit;
	private int unitlimit;
	private TMXTiledMap map;
	

	public Respawn(Scene scene, int x, int y, int width, int heigth, Engine engine, TMXTiledMap map, ActorEntity player, long spawnMiliSeconds, long spawnAcceleration, String unit, int unitlimit) {
		this.scene = scene;
		
		this.area = new Rectangle(x, y, width, heigth, engine.getVertexBufferObjectManager());
		this.area.setColor(Color.RED);
		this.area.setAlpha(0.25f);
		this.area.setVisible(false);
		
		this.engine = engine;
		this.scene.attachChild(area);
		
		this.player = player;
		this.map = map;
		this.spawnMiliSeconds = spawnMiliSeconds;		
		this.spawnAcceleration = spawnAcceleration;
		this.unit = unit;
		this.unitlimit = unitlimit;
	}

	public void spawn(String monster_name) {
		
		int[] spawn = calculateRandomCoordinate(area);
		GameEntity entity = null;
		if(monster_name.equals(Zombie.RESPAWN_NAME)){
			entity = ZombieLoader.loadZombie(spawn[0], spawn[1], engine, player);	
		} else if(monster_name.equals(ZombieSprinter.RESPAWN_NAME)){
			entity = ZombieLoader.loadZombieSprinter(spawn[0], spawn[1], engine, player);
		} else {
			System.out.println("Monstruo invalido");
			this.unitlimit = 0;
		}
		
		scene.attachChild(entity);
	}

	private int[] calculateRandomCoordinate(Rectangle area) {
		int[] coordinate = new int[2];
		int x = (int)(area.getX() + Math.random() * area.getWidth() - getMap().getTileWidth());
		int y = (int)(area.getY() + Math.random() * area.getHeight() - getMap().getTileHeight());
		coordinate[0] = x;
		coordinate[1] = y;
		return coordinate;
	}

	public void run() {
		int unitsSpawned = 0;
		while(unitsSpawned != unitlimit){
			try {
				long time = spawnMiliSeconds - (unitsSpawned * spawnAcceleration);
				if(time < 500){
					time = 500;
				}
				Thread.sleep(time);
				spawn(this.unit);
				unitsSpawned++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}	
	}

	public TMXTiledMap getMap() {
		return map;
	}

	public void setMap(TMXTiledMap map) {
		this.map = map;
	}
}
