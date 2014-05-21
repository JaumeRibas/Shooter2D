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
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Player;

import android.content.res.AssetManager;

public class PlayerLoader {

	private static Player player;
	
	private PlayerLoader(){};

	/**
	 * Devuelve la instancia del player si existe, si no existe, la crea y la devuelve.
	 * La bala del jugador será la que lleva por defecto en la clase {@link Player}
	 * 
	 * @param camera a {@link Camera}
	 * @param x a integer, la posición horizontal del player
	 * @param y a integer, la posición vertical del players
	 * @param textureManger a {@link TextureManager}
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @return a {@link Player}
	 */
	public static Player loadPlayer(float x, float y, Engine engine) {
		if (player == null) {
			TiledTextureRegion pTiledTextureRegion;
			pTiledTextureRegion = ResourceManager.getInstance().playerTextureRegion;
			player = new Player(x, y, pTiledTextureRegion, engine);		
		}
		return player;

	}
	
	/**
	 * Obtiene la instancia de player.
	 * Puede ser null si todavia no se ha creado.
	 * 
	 * @return a {@link Player}
	 */
	public static Player getPlayer() {
		return player;
	}

}
