package org.escoladeltreball.shooter2d.commands;

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;

/**
 * No hace nada
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class DoNothing implements AnalogChangeCommand, Command {

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		// TODO Auto-generated method stub

	}

}
