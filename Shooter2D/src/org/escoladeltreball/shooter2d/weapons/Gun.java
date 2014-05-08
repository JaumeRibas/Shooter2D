package org.escoladeltreball.shooter2d.weapons;

import org.andengine.entity.scene.Scene;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;



public class Gun {
	
	private Cooldown gunCooldown;
	private Bullet bullet;
	private Scene scene;
	private Player player;
	private boolean bulletShot;
	
	public Cooldown getGunCooldown() {
		return gunCooldown;
	}

	public Gun(float gunCooldownTime, Scene scene, Player player, Bullet playerbullet){
		this.gunCooldown = new Cooldown(gunCooldownTime);
		this.player = player;
		this.bullet = playerbullet;
		this.scene = scene;
	
	}
	
	public void shoot(){
		if(this.gunCooldown.cooldownReady() && bullet != null && bulletShot == false){	
			scene.attachChild(bullet);
			this.bulletShot = true;
		}
	}
}
