package org.escoladeltreball.shooter2d.entities;

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
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.entities.interfaces.Attacking;
import org.escoladeltreball.shooter2d.entities.interfaces.Targeting;
import org.escoladeltreball.shooter2d.entities.interfaces.Walking;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Cooldown;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Zombie es una {@link IAEntity} enemiga controlada por IA que
 * persigue al jugador cuando lo detecta.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Zombie extends IAEntity implements Walking, Attacking, Targeting {

	protected float speed = HPConstants.ZOMBIE_WALK_SPEED;
	private static final float zombie_attack_timer = HPConstants.ZOMBIE_ATTACK_TIMER;
	private Cooldown attackCooldown;
	
	protected int vision_radius = HPConstants.ZOMBIE_SIGHT_RADIUS;

	private boolean isWalking = false;

	// CONSTANTS
	
	public static final String RESPAWN_NAME = "zombie";
	/**
	 * Constructor del Zombie.
	 * 
	 * @param pX un entero posicion horizontal del Zombie
	 * @param pY un entero posicion vertical del Zombie
	 * @param pTiledTextureRegion un {@link ITiledTextureRegion}
	 * @param pVertexBufferObjectManager un {@link VertexBufferObjectManager}.
	 * @Param target un {@link ActorEntity} para targetear.
	 */
	public Zombie(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			Engine engine, ActorEntity actorEntity) {
		super(pX, pY, pTiledTextureRegion, engine);
		super.setTarget(actorEntity);
		this.attackCooldown = new Cooldown(zombie_attack_timer);
		Body body = BodyFactory.createZombieBody(pX, pY);
		this.setBody(body);
		this.setColor(Color.GREEN);
		this.setMaxHealthPoints(HPConstants.ZOMBIE_HEALTH);
		this.setHealthpoints(HPConstants.ZOMBIE_HEALTH);
	}

	/**
	 * Camina hacia el objetivo si lo tiene. Si no tiene objetivo, se queda quieto.
	 */
	public void walk() {
		// Comprueba si puede perseguir al jugador.
		boolean canStartChase = this.getTarget() != null && this.getTarget().getDistance(this) < vision_radius;
		boolean isChasing = this.isWalking && this.getTarget().getDistance(this) < vision_radius * (6 / 5);
		// Persigue al jugador si esta a las distancias
		if (canStartChase || isChasing) {
			// Inicia la animación si no ha empezado.
			if (!this.isWalking) {
				this.isWalking = true;
				this.animate(400);
			}
			// Distancias en referencia al Zombie, catetos
			float xDistance = this.getXDistance(super.getTarget());
			float yDistance = this.getYDistance(super.getTarget());
			// Distancia recta, hipotenusa
			float distance = this.getDistance(super.getTarget());
			// Calculo de seno y coseno
			float xStep = xDistance / distance;
			float yStep = yDistance / distance;
			// Rotación
			this.getBody().setTransform(this.getBody().getPosition(),
					(float) Math.atan2(-xStep, yStep));
			// Calculo de la velocidad
			this.getBody().setLinearVelocity(xStep * this.speed,
					yStep * this.speed);
	
		} else {
			if (this.isWalking) {
				this.isWalking = false;
				this.stopAnimation(0);
			}
		}
	}

	/**
	 * Hace atacar al zombie.
	 */
	public void attack() {
		this.isWalking = false;
		this.setCurrentTileIndex(0);
		System.out.println("Cooldown Timer: " + this.attackCooldown.toString());
		if (this.attackCooldown.cooldownReady()) {
			ActorEntity actorEntity = (ActorEntity) super.getTarget();
			actorEntity.hurt(HPConstants.ZOMBIE_STRENGH);
			ResourceManager.getInstance().soundZombie.play();
		}
	}

	/**
	 * Realiza las acciones que un zombie realiza en una actualización.
	 * 
	 * @param pSecondsElapsed el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		this.walk();
		super.onManagedUpdate(pSecondsElapsed);
	}

	/**
	 * Realiza las acciones que el zombie realiza al colisionar con otra entidad.
	 * 
	 * @param otherBody a {@link Body}
	 */

	@Override
	public void beginsContactWith(Body otherBody) {
		
	}

	/**
	 * Realiza las acciones que el zombie realiza cuando es herido. Si su vida
	 * llega a 0, el zombie muere.
	 * 
	 * @param strengh un integer, la fuerza del ataque recibido enemigo.
	 */
	@Override
	public void hurt(int strengh) {
		super.hurt(strengh);
		// TODO Efectos de sonido del zombie herido
		// TODO Animación y particulas de zombie herido
		System.out.println("ZOMBIE HEALTH: " + getHealthpoints() + "/"
				+ getMaxHealthPoints());
		if (getHealthpoints() <= 0) {
			die();
		}
	}

	/**
	 * Mata al zombie, sacandolo de escena y restableciendo sus puntos de vida
	 * para reaprovecharlo nuevamente.
	 */
	public void die() {
		System.out.println("ZOMBIE MUERTO");
		this.remove();
		this.setHealthpoints(getMaxHealthPoints());
	}

	@Override
	public void endsContactWith(Body otherBody) {
		this.speed = HPConstants.ZOMBIE_WALK_SPEED;
	}

	@Override
	public void isContactingWith(Body otherBody) {
		Object userData = otherBody.getUserData();
		if (userData == super.getTarget()) {
			this.speed = HPConstants.ZOMBIE_WALK_SPEED / 6;
			this.attack();
		}
	}
}
