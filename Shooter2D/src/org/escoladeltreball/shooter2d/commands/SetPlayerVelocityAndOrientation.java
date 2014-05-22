package org.escoladeltreball.shooter2d.commands;

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

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocityAndOrientation implements AnalogChangeCommand {

	private boolean isWalking = false;
	
	@Override
	public void execute(float pValueX, float pValueY) {
		Player player = PlayerLoader.getPlayer();
		//cambiar la velocidad del jugador
		if(pValueX != 0 && pValueY != 0){
			if(!this.isWalking){
				long[] pFrameDurations = {200,200,200,200,200,200};
				int[] pFrames = {0,1,2,3,4,5};

				player.animate(pFrameDurations, pFrames, true);
				this.isWalking = true;
			}			
			player.getBody().setTransform(player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			player.getBody().setLinearVelocity(pValueX * HPConstants.HUMAN_WALK_SPEED, pValueY * HPConstants.HUMAN_WALK_SPEED);

		} else {
			if(this.isWalking){
				player.stopAnimation();
				long[] pFrameDurations = {100,200};
				int[] pFrames = {0,6};
				player.animate(pFrameDurations, pFrames, false);
			}
			player.getBody().setLinearVelocity(0, 0);
			this.isWalking = false;
		}
	}

}
