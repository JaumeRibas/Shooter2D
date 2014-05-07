package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;
import org.escoladeltreball.shooter2d.entities.Player;

import com.badlogic.gdx.physics.box2d.Body;


/**
 * Esta clase contiene y sirve los comandos del juego.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class CommandFactory {
	
	private static DoNothing doNothingInstance;
	private static SetPlayerVelocityAndOrientation setPlayerVelocityInstance;
	private static SetPlayerOrientationAndShoot setPlayerOrientationAndShoot;

	public static AnalogChangeCommand getSetPlayerVelocity(Player player) {
		if (setPlayerVelocityInstance == null) {
			setPlayerVelocityInstance = new SetPlayerVelocityAndOrientation(player);
		}
		return setPlayerVelocityInstance;
	}
	
	public static AnalogChangeCommand getSetPlayerOrientationAndShoot(Player player) {
		if (setPlayerOrientationAndShoot == null) {
			setPlayerOrientationAndShoot = new SetPlayerOrientationAndShoot(player);
		}
		return setPlayerOrientationAndShoot;
	}

	public static AnalogChangeCommand getDoNothingAnalogCommand() {
		if (doNothingInstance == null) {
			doNothingInstance = new DoNothing();
		}
		return doNothingInstance;
	}
	
	public static Command getDoNothingCommand() {
		if (doNothingInstance == null) {
			doNothingInstance = new DoNothing();
		}
		return doNothingInstance;
	}

}
