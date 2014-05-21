package org.escoladeltreball.shooter2d.commands;

/*
 * This file is part of shooter2d, a cenital shooter 2D game.
 *
 * Copyright (C) 2014	
 * 						Elvis Puertas <epuertas@gmail.com>
 *						Jaume Ribas <r.ribas.jaume@gmail.com>
 *						Carlos Serrano <arquak@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;


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

	public static AnalogChangeCommand getSetPlayerVelocityAndOrientation() {
		if (setPlayerVelocityInstance == null) {
			setPlayerVelocityInstance = new SetPlayerVelocityAndOrientation();
		}
		return setPlayerVelocityInstance;
	}
	
	public static AnalogChangeCommand getSetPlayerOrientationAndShoot() {
		if (setPlayerOrientationAndShoot == null) {
			setPlayerOrientationAndShoot = new SetPlayerOrientationAndShoot();
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
