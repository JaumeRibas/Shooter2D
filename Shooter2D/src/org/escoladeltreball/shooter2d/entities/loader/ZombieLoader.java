package org.escoladeltreball.shooter2d.entities.loader;

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
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.ZombieSprinter;
import org.escoladeltreball.shooter2d.entities.Zombie;

/**
 * La clase ZombieLoader se encarga cargar los recursos del jugador y del propio jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class ZombieLoader {
	
	private ZombieLoader(){};
	
	/**
	 * Devuelve un zombie nuevo.
	 * 
	 * @param x a integer, la posición horizontal del zombie
	 * @param y a integer, la posición vertical del zombie
	 * @param engine a {@link Engine}
	 * @param actorEntity a {@link ActorEntity} a perseguir
	 * @return a {@link Zombie}
	 */

	public static Zombie loadZombie(float x, float y, Engine engine, ActorEntity actorEntity) {
		Zombie zombie = null;
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().zombieTextureRegion;
		zombie = new Zombie(x, y, pTiledTextureRegion, engine, actorEntity);
		return zombie;
	}
	
	/**
	 * Devuelve un charger nuevo.
	 * 
	 * @param x a integer, la posición horizontal del zombie
	 * @param y a integer, la posición vertical del zombie
	 * @param engine a {@link Engine}
	 * @param actorEntity a {@link ActorEntity} a perseguir
	 * @return a {@link Zombie}
	 */
	public static ZombieSprinter loadZombieSprinter(float x, float y, Engine engine, ActorEntity actorEntity) {
		ZombieSprinter zombie = null;
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().zombieTextureRegion;
		zombie = new ZombieSprinter(x, y, pTiledTextureRegion, engine, actorEntity);
		return zombie;
	}
}
