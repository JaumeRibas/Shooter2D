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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;

import android.content.res.AssetManager;

/**
 * La clase Bullet se encarga cargar las balas.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class BulletLoader {
	
	/** El angulo de la sprite de la bala en radianes */
	public static final float BULLET_SPRITE_ANGLE = (float) (Math.PI / 2f);

	/**
	 * Crea una nueva bala.
	 * 
	 * @param x a integer, la posición horizontal de la bala
	 * @param y a integer, la posición vertical de la bala
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @param angle a float, angulo en grados de la trayectoria de la bala
	 * @param strengh a int, daño que realizará la bala
	 * @return a {@link Player}
	 */	
	public static Bullet loadBullet(float x, float y, float angle, int strengh,	Engine engine) {
		Bullet bullet = null;
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().bulletTextureRegion;
		bullet = new Bullet(x, y, pTiledTextureRegion, engine, angle + BULLET_SPRITE_ANGLE, strengh);
		return bullet;
	}
}