package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocityAndOrientation implements AnalogChangeCommand {

	public float SPEED = 2;
	private boolean isWalking = false;


	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		if(pValueX != 0 && pValueY != 0){
			if(!this.isWalking){
				PlayerLoader.getPlayer().animate(200);
				this.isWalking = true;
			}			
			PlayerLoader.getPlayer().getBody().setTransform(PlayerLoader.getPlayer().getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			PlayerLoader.getPlayer().getBody().setLinearVelocity(pValueX * SPEED, pValueY * SPEED);
			System.out.println("hola");
		} else {
			PlayerLoader.getPlayer().stopAnimation();
			PlayerLoader.getPlayer().setCurrentTileIndex(0);
			PlayerLoader.getPlayer().getBody().setLinearVelocity(0, 0);
			this.isWalking = false;
		}
	}

}
