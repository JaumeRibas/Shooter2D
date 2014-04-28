package org.escoladeltreball.shooter2d.ui;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.commands.CommandManager;
import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.commands.interfaces.Command;

/**
 * La clase UI continene variables y metodos relacionados con la interfaz de usuario.
 * Utiliza el patron singleton.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class UI {
	
	/** instancia unica */
	private static UI instance;

	private AnalogOnScreenControl leftAnalogControl;
	private AnalogOnScreenControl rightAnalogControl;
	
	private UI(){}
	
	/**
	 * Devuelve la unica instancia.
	 * Si no existe la crea.
	 * 
	 * @return la unica instancia de UI
	 */
	public static UI getInstance() {
		if (instance == null) {
			instance = new UI();
		}
		return instance;
	}
	
	/**
	 * Este metodo crea dos {@link AnalogOnScreenControl} y los coloca a cada
	 * lado de la camara en la parte inferior. Devuelve un {@link AnalogOnScreenControl}
	 * con el otro {@link AnalogOnScreenControl} como su {@link Scene} hija.
	 * 
	 * @param camera
	 * @param vertexBufferObjectManager
	 * @param leftAnalogChangeCommand el comando a ejecutar cuando se mueva el analog izquierdo
	 * @param leftAnalogClickCommand el comando a ejecutar cuando se clique el analog izquierdo
	 * @param rightAnalogChangeCommand el comando a ejecutar cuando se mueva el analog derecho
	 * @param rightAnalogClickCommand el comando a ejecutar cuando se clique el analog derecho
	 * @return un {@link AnalogOnScreenControl} con el otro {@link AnalogOnScreenControl} como su {@link Scene} hija
	 */
	public AnalogOnScreenControl createAnalogControls(Camera camera, VertexBufferObjectManager vertexBufferObjectManager, AnalogChangeCommand leftAnalogChangeCommand, Command leftAnalogClickCommand, AnalogChangeCommand rightAnalogChangeCommand, Command rightAnalogClickCommand) {
		
		ConfigurableAnalogControlListener leftAnalogListener = new ConfigurableAnalogControlListener();
		leftAnalogListener.setAnalogChangeCommand(leftAnalogChangeCommand == null? CommandManager.getDoNothingAnalogCommand(): leftAnalogChangeCommand);
		leftAnalogListener.setAnalogClickCommand(leftAnalogClickCommand == null? CommandManager.getDoNothingCommand() : leftAnalogClickCommand);
		ConfigurableAnalogControlListener rightAnalogListener = new ConfigurableAnalogControlListener();
		rightAnalogListener.setAnalogChangeCommand(rightAnalogChangeCommand == null? CommandManager.getDoNothingAnalogCommand() : rightAnalogChangeCommand);
		rightAnalogListener.setAnalogClickCommand(rightAnalogClickCommand == null? CommandManager.getDoNothingCommand() : rightAnalogClickCommand);
		
		/* Control analogico izquierda */
		leftAnalogControl = new AnalogOnScreenControl(0, 0, camera, ResourceManager.getInstance().analogControlBaseTextureRegion, ResourceManager.getInstance().analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, leftAnalogListener);

		{
			final Sprite controlBase = leftAnalogControl.getControlBase();
			controlBase.setAlpha(0.5f);
			controlBase.setOffsetCenter(0, 0);
		}


		/* Control analogico derecha. */
		rightAnalogControl = new AnalogOnScreenControl(camera.getWidth(), 0, camera, ResourceManager.getInstance().analogControlBaseTextureRegion, ResourceManager.getInstance().analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, rightAnalogListener);

		{
			final Sprite controlBase = rightAnalogControl.getControlBase();
			controlBase.setOffsetCenter(1, 0);
			controlBase.setAlpha(0.5f);
	
			leftAnalogControl.setChildScene(rightAnalogControl);
		}
		
		return leftAnalogControl;
	}
	
	/**
	 * Este metodo crea dos {@link AnalogOnScreenControl} y los coloca a cada
	 * lado de la camara en la parte inferior. Devuelve un {@link AnalogOnScreenControl}
	 * con el otro {@link AnalogOnScreenControl} como su {@link Scene} hija.
	 * 
	 * @param camera
	 * @param vertexBufferObjectManager
	 * @return
	 */
	public AnalogOnScreenControl createAnalogControls(Camera camera, VertexBufferObjectManager vertexBufferObjectManager) {
		return createAnalogControls(camera, vertexBufferObjectManager, null, null, null, null);
	}
	
	/**
	 * Devuelve el comando que se ejecuta al mover
	 * el analog izquierdo
	 * 
	 * @return un objeto {@link AnalogChangeCommand}
	 */
	public AnalogChangeCommand getLeftAnalogChangeCommand(){
		return ((ConfigurableAnalogControlListener)this.leftAnalogControl.getOnScreenControlListener()).getAnalogChangeCommand();
	}
	
	/**
	 * Setea un comando que se ejecutará al mover
	 * el analog izquierdo
	 * 
	 * @param analogChangeCommand un objeto {@link AnalogChangeCommand}
	 */
	public void setLeftAnalogChangeCommand(AnalogChangeCommand analogChangeCommand) {
		((ConfigurableAnalogControlListener)this.leftAnalogControl.getOnScreenControlListener()).setAnalogChangeCommand(analogChangeCommand);
	}
	
	/**
	 * Devuelve el comando que se ejecuta al mover
	 * el analog derecho
	 * 
	 * @return un objeto {@link AnalogChangeCommand}
	 */
	public AnalogChangeCommand getRightAnalogChangeCommand(){
		return ((ConfigurableAnalogControlListener)this.rightAnalogControl.getOnScreenControlListener()).getAnalogChangeCommand();
	}
	
	/**
	 * Setea un comando que se ejecutará al mover
	 * el analog derecho
	 * 
	 * @param analogChangeCommand un objeto {@link AnalogChangeCommand}
	 */
	public void setRightAnalogChangeCommand() {
		((ConfigurableAnalogControlListener)this.rightAnalogControl.getOnScreenControlListener()).getAnalogChangeCommand();
	}
	
	/**
	 * Devuelve el comando que se ejecuta al clicar
	 * el analog izquierdo
	 * 
	 * @return un objeto {@link Command}
	 */
	public Command getLeftAnalogClickCommand(){
		return ((ConfigurableAnalogControlListener)this.leftAnalogControl.getOnScreenControlListener()).getAnalogClickCommand();
	}
	
	/**
	 * Setea un comando que se ejecutará al clicar
	 * el analog izquierdo
	 * 
	 * @param analogClickCommand un objeto {@link Command}
	 */
	public void setLeftAnalogClickCommand(Command analogClickCommand) {
		((ConfigurableAnalogControlListener)this.leftAnalogControl.getOnScreenControlListener()).setAnalogClickCommand(analogClickCommand);
	}
	
	/**
	 * Devuelve el comando que se ejecuta al clicar
	 * el analog derecho
	 * 
	 * @return un objeto {@link Command}
	 */
	public Command getRightAnalogClickCommand(){
		return ((ConfigurableAnalogControlListener)this.rightAnalogControl.getOnScreenControlListener()).getAnalogClickCommand();
	}
	
	/**
	 * Setea un comando que se ejecutará al clicar
	 * el analog derecho
	 * 
	 * @param analogClickCommand un objeto {@link Command}
	 */
	public void setRightAnalogClickCommand() {
		((ConfigurableAnalogControlListener)this.rightAnalogControl.getOnScreenControlListener()).getAnalogClickCommand();
	}
}
