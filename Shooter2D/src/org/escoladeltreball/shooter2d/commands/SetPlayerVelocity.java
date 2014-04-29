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
public class SetPlayerVelocity implements AnalogChangeCommand {

	private Player player;
	
	public SetPlayerVelocity(Player player) {
		this.player = player;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		//cambiar este codigo
		this.player.setPosition(this.player.getX() + pValueX, this.player.getY() + pValueY);
	}

}
