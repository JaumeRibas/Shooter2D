package org.escoladeltreball.shooter2d.scenes;

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
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.tmx.TMXObject;
import org.andengine.extension.tmx.TMXObjectGroup;
import org.andengine.extension.tmx.TMXProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.GameManager;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.MapCreator;
import org.escoladeltreball.shooter2d.Respawn;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;
import org.escoladeltreball.shooter2d.constants.NotificationConstants;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.ColisionableEntity;
import org.escoladeltreball.shooter2d.entities.IAEntity;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.ui.GameObserver;
import org.escoladeltreball.shooter2d.weapons.WeaponFactory;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * El primer nivel
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class FirstLevel extends Level implements GameScene, GameObserver {
	
	public FirstLevel(GameObserver observer) {
		super(observer);
	}

	private Player player;

	private int enemyCount = 0;
	
	private static final float START_PLAYER_X = 3000;
	private static final float START_PLAYER_Y = 0;
	private static final float START_PLAYER_ANGLE = 0;
	
	public static final String MAP_PATH = "tmx/base.tmx";
	
	@Override
	public void createScene() {
		Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		setScene(scene);
	}	
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void populate() {
		getScene().registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
		// Muestra el mapa en la pantalla
		getScene().attachChild(getMap());
		// La camara sigue al jugador
		MainActivity.getInstance().camera.setChaseEntity(player);
		//ponemos al jugador en su posicion inicial SI SE MUEVE PETA AUN NO SE PORQUE
//		this.player.setPosition(START_PLAYER_X, START_PLAYER_Y);
//		this.player.setRotation(START_PLAYER_ANGLE);
//		this.player.getBody().setTransform(START_PLAYER_X, START_PLAYER_Y, START_PLAYER_ANGLE);
		//el jugador tendra una pistola
		this.player.setGun(WeaponFactory.getGun(getScene(), MainActivity.getInstance().getEngine()));
		//crea los objetos del mapa
		createMapObjects();	
		//metemos al player
		getScene().attachChild(player);	
	}

	@Override
	public void restart() {
//		int childCount = getScene().getChildCount();
//		for (int i = 0; i < childCount; i++) {
//			final IEntity iEntity = getScene().getChildByIndex(i);
//			if (iEntity instanceof ColisionableEntity) {
//				((ColisionableEntity) iEntity).remove();
//			}
//		}
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		setMap(MapCreator.loadMap(FirstLevel.MAP_PATH, MainActivity.getInstance().getEngine(), MainActivity.getInstance(), MainActivity.getInstance().camera));
//		createScene();
//		getScene().registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
//		// Muestra el mapa en la pantalla
//		getScene().attachChild(getMap());
//		getScene().attachChild(player);
//		//crea los objetos del mapa
//		MapCreator.createMapObjects(getMap(), getScene(), MainActivity.getInstance().getEngine(), MainActivity.getInstance().getVertexBufferObjectManager(), player);
		
		//reiniciamos la activity
		Intent mStartActivity = new Intent(MainActivity.getInstance(), MainActivity.class);
		int mPendingIntentId = 123456;
		PendingIntent mPendingIntent = PendingIntent.getActivity(MainActivity.getInstance(), mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager mgr = (AlarmManager)MainActivity.getInstance().getSystemService(Context.ALARM_SERVICE);
		mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
		System.exit(0);
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
	private void createMapObjects(){
		GameObserver[] observers = {this};
		// Loop through the object groups
		for(final TMXObjectGroup group: getMap().getTMXObjectGroups()) {
			if(group.getTMXObjectGroupProperties().containsTMXProperty("wall", "true")){
				MapCreator.createWalls(group, getMap(), MainActivity.getInstance().getVertexBufferObjectManager());
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
					
					int posX = object.getX() + getMap().getTileWidth();
					int posY = (int) (getMap().getHeight()-object.getHeight()-object.getY()) + getMap().getTileWidth(); 
					int width = object.getWidth() + 32;
					int heigh = object.getHeight() + 32;
					
					Respawn res = new Respawn(getScene(), posX, posY, width, heigh, MainActivity.getInstance().getEngine(), getMap(), target, spawnMiliSeconds, spawnAcceleration, unit, unitlimit, observers, new Command() {
						
						@Override
						public void execute() {
							FirstLevel.this.enemyAdded();
							
						}
					});
					res.start();
				}
			}
		}
	}

	protected void enemyAdded() {
		this.enemyCount ++;		
		System.out.println("#########enemy added\nenemyCount=" + enemyCount);
	}

	@Override
	public void notify(Object notifier, Object data) {
		if (data instanceof Short) {
			short notification = ((Short) data).shortValue();
			if (notifier instanceof IAEntity) {
				IAEntity enemy = (IAEntity)notifier;
				switch (notification) {
				case NotificationConstants.CHANGE_HEALTH:
					if (enemy.getHealthpoints() <= 0)  {
						this.enemyCount--;
						System.out.println("#########enemy removed\nenemyCount=" + enemyCount);
						if (this.enemyCount <= 0) {
							notify(NotificationConstants.LEVEL_WON);
						}
					}
					break;
				}
			} 
		}		
	}
}
