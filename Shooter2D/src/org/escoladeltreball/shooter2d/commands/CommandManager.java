package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;
import org.escoladeltreball.shooter2d.entities.Player;


/**
 * Esta clase contiene y sirve los comandos del juego.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class CommandManager {
	
	private static DoNothing doNothingInstance;
	
	private static SetPlayerVelocity setPlayerVelocity;

	public static AnalogChangeCommand getSetPlayerVelocity(Player player) {
		if (setPlayerVelocity == null) {
			setPlayerVelocity = new SetPlayerVelocity(player);
		}
		return setPlayerVelocity;
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
