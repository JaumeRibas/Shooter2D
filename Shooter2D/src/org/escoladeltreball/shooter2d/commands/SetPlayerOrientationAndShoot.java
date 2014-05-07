package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerOrientationAndShoot implements AnalogChangeCommand {

	private Player player;
	
	public SetPlayerOrientationAndShoot(Player player) {
		this.player = player;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar orientacion del personaje
		if(pValueX != 0 && pValueY != 0){
			this.player.getBody().setTransform(this.player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			
		}
	}

}
