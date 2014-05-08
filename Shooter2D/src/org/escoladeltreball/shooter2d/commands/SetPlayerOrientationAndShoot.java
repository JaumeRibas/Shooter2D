package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerOrientationAndShoot implements AnalogChangeCommand {

	@Override
	public void execute(float pValueX, float pValueY) {
		Player player = PlayerLoader.getPlayer();
		//cambiar orientacion del personaje
		if(pValueX != 0 && pValueY != 0){
			player.getBody().setTransform(player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			if(player.getGun() != null){
				player.getGun().shoot();
			}
		}
	}

}
