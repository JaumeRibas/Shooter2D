package org.escoladeltreball.shooter2d.weapons;

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
import org.andengine.entity.scene.Scene;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.loader.BulletLoader;

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
			ResourceManager.getInstance().soundShootGun.play();
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
