package org.escoladeltreball.shooter2d.ui;

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
