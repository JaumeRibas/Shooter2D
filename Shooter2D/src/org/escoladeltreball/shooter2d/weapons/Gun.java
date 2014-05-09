package org.escoladeltreball.shooter2d.weapons;

import org.andengine.entity.scene.Scene;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;


/**
 * La clase Gun es la encargada de posicionar las balas que disparan las ActorEntity.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Gun {
	
	private Cooldown gunCooldown;
	private Bullet bullet;
	private Scene scene;
	private ActorEntity shooter;
	private boolean bulletShot;
	
	public Gun(float gunCooldownTime, Scene scene, Player player, Bullet playerbullet){
		this.gunCooldown = new Cooldown(gunCooldownTime);
		this.shooter = player;
		this.bullet = playerbullet;
		this.scene = scene;
	
	}
	
	public Cooldown getGunCooldown() {
		return gunCooldown;
	}
	
	/**
	 * Dispara una bala. Colocará la bala en el mapa con respecto al angulo formado por X e Y.
	 * 
	 * @param x un float, define el desplazamiento y posicionamiento inicial horizontal de la bala.
	 * @param y un float, define el desplazamiento y posicionamiento inicial horizontal de la bala.
	 */
	public void shoot(float x, float y){
		if(this.gunCooldown.cooldownReady() && bullet != null && bulletShot == false){
			bullet.setPosition(shooter.getX() + (float)Math.cos(x), shooter.getY() + (float)Math.cos(y));
			// 
			scene.attachChild(bullet);
			this.bulletShot = true;
		}
	}
}
