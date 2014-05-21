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
import org.escoladeltreball.shooter2d.constants.NotificationConstants;

/**
 * La clase abstracta ActorEntity es una {@link ColisionableEntity} que designa a personajes
 * actores, como el jugador y los enemigos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class ActorEntity extends ColisionableEntity {
	
	private int healthpoints = 1;
	private int maxHealthPoints = 1;

	public ActorEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			Engine engine) {
		super(pX, pY, pTiledTextureRegion, engine);
	}

	/**
	 * Realiza las acciones que el actor realiza cuando es herido.
	 * 
	 * @param strengh a integer, la fuerza del ataque recibido.
	 */
	public void hurt(int strengh){
		this.setHealthpoints(this.getHealthpoints() - strengh);
		this.notify(NotificationConstants.CHANGE_HEALTH);
	}
	
	// GETTERS AND SETTERS
	
	public int getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(int maxHealthPoints) {
		this.maxHealthPoints = maxHealthPoints;
	}

}
