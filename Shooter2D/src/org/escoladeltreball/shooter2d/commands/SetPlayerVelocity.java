package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocity implements AnalogChangeCommand {

	private Body playerBody;
	
	public SetPlayerVelocity(Body playerBody) {
		this.playerBody = playerBody;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		System.out.println("setting velocity");
		this.playerBody.setLinearVelocity(pValueX * 3, pValueY * 3);
	}

}
