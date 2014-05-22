package org.escoladeltreball.shooter2d.ui;

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

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;

/**
 * Define un listener para un {@link AnalogOnScreenControl}
 * con dos referencias a dos commandos que se ejecutan al 
 * darse los eventos del analog control. Además proporciona
 * seters para cambiar estos comandos de forma que se puedan
 * cambiar las acciones realizadas por el analog en tiempo de
 * ejecución.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class ConfigurableAnalogControlListener implements IAnalogOnScreenControlListener {
	/** el comando a ejecutar al mover el analog*/
	private AnalogChangeCommand analogChangeCommand;
	
	/** el comando a ejecutar al clicar el analog*/
	private Command analogClickCommand;

	public AnalogChangeCommand getAnalogChangeCommand() {
		return analogChangeCommand;
	}

	public void setAnalogChangeCommand(AnalogChangeCommand analogChangeCommand) {
		this.analogChangeCommand = analogChangeCommand;
	}

	public Command getAnalogClickCommand() {
		return analogClickCommand;
	}

	public void setAnalogClickCommand(Command analogClickCommand) {
		this.analogClickCommand = analogClickCommand;
	}

	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl, float pValueX, float pValueY) {
		this.analogChangeCommand.execute(pValueX, pValueY);
	}

	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
		this.analogClickCommand.execute();
	}

}
