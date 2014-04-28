package org.escoladeltreball.shooter2d.commands.interfaces;

import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;

/**
 * Esta interfaz define un commando
 * a ejecutar cuando se mueve un {@link AnalogOnScreenControl}
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public interface AnalogChangeCommand {
	/** ejecuta el comando cuando se mueve el analog */
	void execute(float pValueX, float pValueY);

}
