package org.escoladeltreball.shooter2d.commands;

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
				player.animate(200);
				this.isWalking = true;
			}			
			player.getBody().setTransform(player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			player.getBody().setLinearVelocity(pValueX * HPConstants.HUMAN_WALK_SPEED, pValueY * HPConstants.HUMAN_WALK_SPEED);

		} else {
			player.stopAnimation();
			player.setCurrentTileIndex(0);
			player.getBody().setLinearVelocity(0, 0);
			this.isWalking = false;
		}
	}

}
