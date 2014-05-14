package org.escoladeltreball.shooter2d;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.entities.GameEntity;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;
import org.escoladeltreball.shooter2d.entities.loader.ZombieLoader;

import android.content.res.AssetManager;

public class Respawn extends Thread {
	
	private Scene scene;
	private TextureManager textureManager;
	private AssetManager assets;
	private VertexBufferObjectManager vertexBufferObjectManager;
	private Player player;
	private long spawnMiliSeconds;
	private long spawnAcceleration;
	private Rectangle area;
	private String unit;
	private int unitlimit;
	

	public Respawn(Scene scene, int x, int y, int width, int heigth, TextureManager textureManager,
			AssetManager assets,
			VertexBufferObjectManager vertexBufferObjectManager,
			FixedStepPhysicsWorld mPhysicsWorld, Player player, long spawnMiliSeconds, long spawnAcceleration, String unit, int unitlimit) {
		this.scene = scene;
		
		this.area = new Rectangle(x, y, width, heigth, vertexBufferObjectManager);
		this.area.setColor(Color.RED);
		this.area.setAlpha(0.25f);
		
		this.scene.attachChild(area);
		
		this.textureManager = textureManager;
		this.assets = assets;
		this.vertexBufferObjectManager = vertexBufferObjectManager;
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
			entity = ZombieLoader.loadZombie(spawn[0], spawn[1], this.textureManager, this.assets,
					vertexBufferObjectManager, player);	
		} else {
			System.out.println("Monstruo invalido");
			this.unitlimit = 0;
		}
		
		scene.attachChild(entity);
	}

	private int[] calculateRandomCoordinate(Rectangle area) {
		int[] coordinate = new int[2];
		int x = (int)(area.getX() + Math.random() * area.getWidth());
		int y = (int)(area.getY() + Math.random() * area.getHeight());
		coordinate[0] = x;
		coordinate[1] = y;
		return coordinate;
	}

	public void run() {
		int zombiesSpawned = 0;
		while(zombiesSpawned != unitlimit){
			try {
				long time = spawnMiliSeconds - (zombiesSpawned * spawnAcceleration);
				if(time < 500){
					time = 500;
				}
				Thread.sleep(time);
				spawn(this.unit);
				zombiesSpawned++;
				System.out.println("HAN POPEADO " + zombiesSpawned + " ZOMBIES!");
			} catch (InterruptedException e) {
				System.out.println("ALGO MALO HA PASADO...");
				e.printStackTrace();
			}	
		}	
	}
}
