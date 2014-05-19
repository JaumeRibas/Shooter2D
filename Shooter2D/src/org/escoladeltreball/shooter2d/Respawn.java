package org.escoladeltreball.shooter2d;

import org.andengine.engine.Engine;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
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
	

	public Respawn(Scene scene, int x, int y, int width, int heigth, Engine engine, ActorEntity player, long spawnMiliSeconds, long spawnAcceleration, String unit, int unitlimit) {
		this.scene = scene;
		
		this.area = new Rectangle(x, y, width, heigth, engine.getVertexBufferObjectManager());
		this.area.setColor(Color.RED);
		this.area.setAlpha(0.25f);
		this.area.setVisible(false);
		
		this.engine = engine;
		this.scene.attachChild(area);
		
		this.player = player;
		
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
		int x = (int)(area.getX() + Math.random() * area.getWidth() - MapCreator.getCurrentMap().getTileWidth());
		int y = (int)(area.getY() + Math.random() * area.getHeight() - MapCreator.getCurrentMap().getTileHeight());
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
}
