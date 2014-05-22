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
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Cooldown;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Bullet es una {@link ColisionableEntity} que simula una bala.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Bullet extends ColisionableEntity {

	/** El tiempo de vida de la bala en segundos */
	private static final int BULLET_LIFE_TIME = 2;
	public static final float SPEED = 20f;
	private int strengh = 1;

	private Cooldown bulletTime;

//	private boolean attached = true;

	/**
	 * Constructor de la bala.
	 * 
	 * @Param target un {@link GameEntity} para targetear.
	 * @param pX
	 *            un entero posicion horizontal de la bala.
	 * @param pY
	 *            un entero posicion vertical de la bala.
	 * @param pTiledTextureRegion
	 *            una {@link ITiledTextureRegion}.
	 * @param pVertexBufferObjectManager
	 *            un {@link VertexBufferObjectManager}.
	 * @param angle
	 *            el angulo de la bala.
	 * @param strengh
	 *            la vida que quita la bala.
	 */
	public Bullet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			Engine engine, float angle, int strengh) {
		super(pX, pY, pTiledTextureRegion, engine);
		Body body = BodyFactory.createBulletBody(pX, pY);
		body.setTransform(body.getPosition(), angle);
		this.strengh = strengh;
		this.setCurrentTileIndex(0);
		this.setBody(body);
		this.setScale(1.0f);
		this.animate(100);
		this.bulletTime = new Cooldown(BULLET_LIFE_TIME);
	}

	/**
	 * Realiza las acciones que una bala realiza en una actualización.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (bulletTime.cooldownReady()) {
			this.remove();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	/**
	 * Realiza las acciones que la bala realiza al chocar contra otro cuerpo.
	 * 
	 * Si el body pertenece a una pared, desaparece. Si el body pertenece a una
	 * {@link ActorEntity}, daña a esa {@link ActorEntity} y luego desaparece.
	 * 
	 * @param otherBody
	 *            a {@link Body}.
	 */
	public void beginsContactWith(Body otherBody) {

		if (otherBody.getUserData().equals(BodyFactory.WALL_USER_DATA)) {
			this.remove();
			// Contra un ActorEntity hace daño a esa ActorEntity y sale de
			// escena.
		} else if (otherBody.getUserData() instanceof ActorEntity) {
			ActorEntity actor = (ActorEntity) otherBody.getUserData();
			actor.hurt(strengh);
			this.remove();
		}
	}
	
	@Override
	public void endsContactWith(Body otherBody) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isContactingWith(Body otherBody) {
		// TODO Auto-generated method stub

	}
}
