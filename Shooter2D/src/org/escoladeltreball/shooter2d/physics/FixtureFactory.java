package org.escoladeltreball.shooter2d.physics;

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

import org.andengine.extension.physics.box2d.PhysicsFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Contiene y sirve las {@link FixtureDef}s para los {@link Body}s.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class FixtureFactory {

	// flesh
	private static final float FLESH_DENSITY = 100;
	private static final float FLESH_ELASTICITY = 0.05f;
	private static final float FLESH_FRICTION = 0.00f;
	
	// wall
	private static final float WALL_DENSITY = 0;
	private static final float WALL_ELASTICITY = 0;
	private static final float WALL_FRICTION = 0;
	
	// lead (plomo xra la bala xD)
	private static final float LEAD_DENSITY = 0;
	private static final float LEAD_ELASTICITY = 0;
	private static final float LEAD_FRICTION = 0;
	
	private static FixtureDef fleshFixtureInstance;
	private static FixtureDef wallFixtureInstance;
	private static FixtureDef leadFixtureInstance;

	/**
	 * Devuelve la instancia unica de flesh FixtureDef.
	 * Si no existe la crea.
	 * 
	 * @return una {@link FixtureDef}
	 */
	public static FixtureDef getFleshFixture() {
		if (fleshFixtureInstance == null) {
			fleshFixtureInstance = PhysicsFactory.createFixtureDef(FLESH_DENSITY, FLESH_ELASTICITY, FLESH_FRICTION);
		}
		return fleshFixtureInstance;
	}
	
	/**
	 * Devuelve la instancia unica de lead FixtureDef.
	 * Si no existe la crea.
	 * 
	 * @return una {@link FixtureDef}
	 */
	public static FixtureDef getLeadFixture() {
		if (leadFixtureInstance == null) {
			leadFixtureInstance = PhysicsFactory.createFixtureDef(LEAD_DENSITY, LEAD_ELASTICITY, LEAD_FRICTION);
		}
		return fleshFixtureInstance;
	}

	/**
	 * Devuelve la instancia unica de wall FixtureDef.
	 * Si no existe la crea.
	 * 
	 * @return una {@link FixtureDef}
	 */
	public static FixtureDef getWallFixture() {
		if (wallFixtureInstance == null) {
			wallFixtureInstance = PhysicsFactory.createFixtureDef(WALL_DENSITY, WALL_ELASTICITY, WALL_FRICTION);
		}
		return wallFixtureInstance;
	}
}
