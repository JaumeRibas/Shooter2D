package org.escoladeltreball.shooter2d.scenes;

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

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.MapCreator;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.weapons.WeaponFactory;

public class FirstLevel extends Scene implements GameScene {
	
	private Player player;

	public FirstLevel() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}



	@Override
	public void populate() {
		registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
		// Muestra el mapa en la pantalla
		attachChild(MapCreator.getCurrentMap());
		//el jugador tendra una pistola
		this.player.setGun(WeaponFactory.getGun(this, MainActivity.getInstance().getEngine()));
		//crea los objetos del mapa
		MapCreator.createMapObjects(this, MainActivity.getInstance().getEngine(), MapCreator.getCurrentMap(), MainActivity.getInstance().getVertexBufferObjectManager(), player);
		// La camara sigue al jugador
		MainActivity.getInstance().camera.setChaseEntity(player);
		attachChild(player);
	}

}
