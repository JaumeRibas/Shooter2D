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
public class SetPlayerVelocityAndOrientation implements AnalogChangeCommand {

	private Player player;
	public float SPEED = 2;
	private boolean isWalking = false;
	
	public SetPlayerVelocityAndOrientation(Player player) {
		this.player = player;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		if(pValueX != 0 && pValueY != 0){
			if(!this.isWalking){
				this.player.animate(200);
				this.isWalking = true;
			}			
			this.player.getBody().setTransform(this.player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			this.player.getBody().setLinearVelocity(pValueX * SPEED, pValueY * SPEED);
			System.out.println("hola");
		} else {
			this.player.stopAnimation();
			this.player.setCurrentTileIndex(0);
			this.player.getBody().setLinearVelocity(0, 0);
			this.isWalking = false;
		}
	}

}
