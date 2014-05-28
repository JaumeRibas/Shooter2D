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
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Gun;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Player es una {@link ActorEntity} que controlara el jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Player extends ActorEntity {

	private static final int SHOOTING_TILE = 6;
	private static final int WALKING_FRAMES_PERSISTENCE_TIME = 200;
	public static final float WALKING_SPEED = 6f;
	private static final float WALKING_AND_SHOOTING_SPEED = 3f;

	private volatile boolean isWalking = false;
	private volatile boolean isShooting = false;

	private Gun gun = null;
	

	/**
	 * Constructor del Player.
	 * 
	 * @param pX
	 *            an integer posicion horizontal del Player
	 * @param pY
	 *            an integer posicion vertical del Player
	 * @param pTiledTextureRegion
	 *            a ITiledTextureRegion
	 * @param pVertexBufferObjectManager
	 *            a VertexBufferObjectManager
	 * @param scene
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			Engine engine) {
		super(pX, pY, pTiledTextureRegion, engine);
		Body body = BodyFactory.createHumanBody(pX, pY);
		this.setBody(body);
		this.setMaxHealthPoints(HPConstants.HUMAN_HEALTH);
		this.setHealthpoints(HPConstants.HUMAN_HEALTH);
	}

	/**
	 * Realiza las acciones que el jugador realiza cuando es herido.
	 * 
	 * @param strengh
	 *            un integer, la fuerza del ataque recibido enemigo.
	 */
	@Override
	public void hurt(int strengh) {
		super.hurt(strengh);
		// TODO Animación y particulas de player herido
		ResourceManager.getInstance().soundPlayerDead.play();
		MainActivity.vibrator.vibrate(500);
	}

	/**
	 * Realiza las acciones que el jugador colisiona con otras entidades.
	 * 
	 * @param otherBody
	 *            un body de otra entidad.
	 */
	@Override
	public void beginsContactWith(Body otherBody) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endsContactWith(Body otherBody) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isContactingWith(Body otherBody) {
		// TODO Auto-generated method stub

	}

	public Gun getGun() {
		return gun;
	}

	public void setGun(Gun gun) {
		this.gun = gun;
		this.gun.setShooter(this);
	}

	public void startWalking(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		if(pValueX != 0 || pValueY != 0){
			float speed;
			if (this.isShooting) {
				this.isWalking = true;
				speed = WALKING_AND_SHOOTING_SPEED;
			} else {
				speed = WALKING_SPEED;
				if(!this.isWalking){
					this.isWalking = true;
					this.startWalkingAnimation();
				}
				//rotamos el player en la dirección del movimiento
				this.getBody().setTransform(this.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			}			
			this.getBody().setLinearVelocity(pValueX * speed, pValueY * speed);
		} else {
			if (this.isWalking) {
				this.isWalking = false;
				this.getBody().setLinearVelocity(0, 0);
				if(!this.isShooting){
					this.stopAnimation();
					long[] pFrameDurations = {100,200};
					int[] pFrames = {0,6};
					this.animate(pFrameDurations, pFrames, false);
				}
			}
		}		
	}
	
	private void startWalkingAnimation() {
		long[] pFrameDurations = {WALKING_FRAMES_PERSISTENCE_TIME,
				WALKING_FRAMES_PERSISTENCE_TIME,
				WALKING_FRAMES_PERSISTENCE_TIME,
				WALKING_FRAMES_PERSISTENCE_TIME};
		int[] pFrames = {0,2,0,5};
		this.animate(pFrameDurations, pFrames, true);
	}

	public void shoot(float pValueX, float pValueY) {
		//cambiar orientacion del personaje
		if(pValueX != 0 || pValueY != 0){
			this.isShooting = true;
			if (this.isWalking) {
				this.stopAnimation();
				this.setCurrentTileIndex(SHOOTING_TILE);
			}
			double angle = Math.atan2(-pValueX, pValueY);
			this.setRotation((float) Math.toDegrees(-angle));
			this.getBody().setTransform(this.getBody().getPosition(), (float)angle);
			if(this.getGun() != null){
				this.getGun().shoot();
			}
		} else {
			if(this.isShooting && this.isWalking){
				this.startWalkingAnimation();
			}
			this.isShooting = false;
		}
	}
}
