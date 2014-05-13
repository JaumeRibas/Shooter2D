package org.escoladeltreball.shooter2d.weapons;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.loader.BulletLoader;

import com.badlogic.gdx.math.Vector2;


/**
 * La clase Gun es la encargada de posicionar las balas que disparan las ActorEntity.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Gun implements Runnable {
	
	/** La diferencia en x de la posicion inicial de la bala en relacion al shooter (en coordenadas locales del shooter) */
	private static final float BULLET_OFFSET_X = 0;
	/** La diferencia en y de la posicion inicial de la bala en relacion al shooter (en coordenadas locales del shooter) */
	private static final float BULLET_OFFSET_Y = 5;
	private Cooldown gunCooldown;
	private Scene scene;
	private ActorEntity shooter;
	private volatile boolean shoot = true;
	private VertexBufferObjectManager vertexBufferObjectManager;
	private Engine engine;
	
	public Gun(long gunCooldownTime, Scene scene, ActorEntity shooter, Engine engine){
		this.gunCooldown = new Cooldown(gunCooldownTime);
		this.shooter = shooter;
		this.scene = scene;
		this.engine = engine;
	}
	
	public Cooldown getGunCooldown() {
		return gunCooldown;
	}
	
	/**
	 * Dispara una bala. Colocará la bala en el mapa con respecto al angulo del shooter.
	 */
	private void shootBullet(){
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector2 bulletPosition = shooter.getBody().getWorldPoint(Vector2Pool.obtain(BULLET_OFFSET_X, BULLET_OFFSET_Y));
		float bulletAngle = shooter.getBody().getAngle();
		Bullet newBullet = BulletLoader.loadBullet(bulletPosition.x, bulletPosition.y, bulletAngle, 3, engine);
		scene.attachChild(newBullet);
	}
	
	public void shoot() {
		//this.shoot = true;
	}


	@Override
	public void run() {
//		boolean coolReady = this.gunCooldown.cooldownReady();
//		System.out.println("Gun onUpdate " + coolReady + " thread: " + Thread.currentThread().getName());//DEBUG
//		if (this.shoot && coolReady) {
//			this.shoot = false;
//			shootBullet();
//		}
	}
}
