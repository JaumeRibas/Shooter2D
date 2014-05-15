package org.escoladeltreball.shooter2d.weapons;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.BulletLoader;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.entities.loader.ZombieLoader;

import com.badlogic.gdx.math.Vector2;


/**
 * La clase Gun es la encargada de posicionar las balas que disparan las ActorEntity.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Gun {
	
	/** La diferencia en x de la posicion inicial de la bala en relacion al shooter (en coordenadas locales del shooter) */
	private static final float BULLET_OFFSET_X = 0;
	/** La diferencia en y de la posicion inicial de la bala en relacion al shooter (en coordenadas locales del shooter) */
	private static final float BULLET_OFFSET_Y = 50;
	private static final float GUN_COOLDOWN_TIME = 0.35f;
	private Cooldown gunCooldown;
	private Scene scene;
	private ActorEntity shooter;
	private Engine engine;
	
	public Gun(Scene scene, Engine engine){
		this.gunCooldown = new Cooldown(GUN_COOLDOWN_TIME);
		this.scene = scene;
		this.engine = engine;
	}
	
	public Cooldown getGunCooldown() {
		return gunCooldown;
	}
	
	/**
	 * Dispara una bala. Colocará la bala en el mapa con respecto al angulo del shooter.
	 */
	public synchronized void shoot(){
		if (this.gunCooldown.cooldownReady()) {
			double hypo = Math.sqrt(Math.pow(BULLET_OFFSET_X,2) + Math.pow(BULLET_OFFSET_Y, 2));
			double bulletOffsetAngle = Math.atan2(BULLET_OFFSET_X, BULLET_OFFSET_Y);
			double shooterAngle = -Math.toRadians(this.shooter.getRotation());
			double bulletAngle = - (shooterAngle + bulletOffsetAngle);
			Bullet newBullet = BulletLoader.loadBullet((float)(this.shooter.getX() + Math.sin(bulletAngle) * hypo), (float)(shooter.getY() + Math.cos(bulletAngle) * hypo), (float)shooterAngle, 3, engine);
			newBullet.getBody().setLinearVelocity((float)(Bullet.SPEED * Math.sin(bulletAngle)), (float)(Bullet.SPEED * Math.cos(bulletAngle)));
			scene.attachChild(newBullet);
		}
	}

	public ActorEntity getShooter() {
		return shooter;
	}

	public void setShooter(ActorEntity shooter) {
		this.shooter = shooter;
	}
}
