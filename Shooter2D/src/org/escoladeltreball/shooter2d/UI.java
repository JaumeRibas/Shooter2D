package org.escoladeltreball.shooter2d;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * La clase UI continene variables y metodos relacionados con la interfaz de usuario.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class UI {
	
	/**
	 * Este metodo crea dos {@link AnalogOnScreenControl} y los coloca a cada
	 * lado de la camara en la parte inferior. Devuelve un {@link AnalogOnScreenControl}
	 * con el otro {@link AnalogOnScreenControl} como su {@link Scene} hija.
	 * 
	 * @param camera
	 * @param vertexBufferObjectManager
	 * @param leftAnalogControlListener un {@link IAnalogOnScreenControlListener} para el {@link AnalogOnScreenControl} izquierdo
	 * @param rightAnalogControlListener un {@link IAnalogOnScreenControlListener} para el {@link AnalogOnScreenControl} derecho
	 * @return un {@link AnalogOnScreenControl} con el otro {@link AnalogOnScreenControl} como su {@link Scene} hija
	 */
	public AnalogOnScreenControl createAnalogControls(Camera camera, VertexBufferObjectManager vertexBufferObjectManager, IAnalogOnScreenControlListener leftAnalogControlListener, IAnalogOnScreenControlListener rightAnalogControlListener) {
		/* Control analogico izquierda */
		final AnalogOnScreenControl leftOnScreenControl = new AnalogOnScreenControl(0, 0, camera, ResourceManager.getInstance().analogControlBaseTextureRegion, ResourceManager.getInstance().analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, leftAnalogControlListener);

		{
			final Sprite controlBase = leftOnScreenControl.getControlBase();
			controlBase.setAlpha(0.5f);
			controlBase.setOffsetCenter(0, 0);
		}


		/* Control analogico derecha. */
		final AnalogOnScreenControl rightOnScreenControl = new AnalogOnScreenControl(camera.getWidth(), 0, camera, ResourceManager.getInstance().analogControlBaseTextureRegion, ResourceManager.getInstance().analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, rightAnalogControlListener);

		{
			final Sprite controlBase = rightOnScreenControl.getControlBase();
			controlBase.setOffsetCenter(1, 0);
			controlBase.setAlpha(0.5f);
	
			leftOnScreenControl.setChildScene(rightOnScreenControl);
		}
		
		return leftOnScreenControl;
	}
}
