package org.escoladeltreball.shooter2d;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.res.AssetManager;

/**
 * La clase UI continene variables y metodos relacionados con la interfaz de usuario.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class UI {

	private ITexture analogControlBaseTexture;
	private ITextureRegion analogControlBaseTextureRegion;
	private ITexture analogControlKnobTexture;
	private ITextureRegion analogControlKnobTextureRegion;
	
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
	public AnalogOnScreenControl createAnalogContollers(Camera camera, VertexBufferObjectManager vertexBufferObjectManager, IAnalogOnScreenControlListener leftAnalogControlListener, IAnalogOnScreenControlListener rightAnalogControlListener) {
		/* Control analogico izquierda */
		final AnalogOnScreenControl leftOnScreenControl = new AnalogOnScreenControl(0, 0, camera, this.analogControlBaseTextureRegion, this.analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, leftAnalogControlListener);

		{
			final Sprite controlBase = leftOnScreenControl.getControlBase();
			controlBase.setAlpha(0.5f);
			controlBase.setOffsetCenter(0, 0);
		}


		/* Control analogico derecha. */
		final AnalogOnScreenControl rightOnScreenControl = new AnalogOnScreenControl(camera.getWidth(), 0, camera, this.analogControlBaseTextureRegion, this.analogControlKnobTextureRegion, 0.1f, vertexBufferObjectManager, rightAnalogControlListener);

		{
			final Sprite controlBase = rightOnScreenControl.getControlBase();
			controlBase.setOffsetCenter(1, 0);
			controlBase.setAlpha(0.5f);
	
			leftOnScreenControl.setChildScene(rightOnScreenControl);
		}
		
		return leftOnScreenControl;
	}

	/**
	 * Carga recursos relacionados con la interfaz de usuario.
	 * 
	 * @param textureManger
	 * @param assets
	 * @throws IOException
	 */
	public void loadResources(TextureManager textureManger, AssetManager assets) throws IOException {
		this.analogControlBaseTexture = new AssetBitmapTexture(textureManger, assets, "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.analogControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.analogControlBaseTexture);
		this.analogControlBaseTexture.load();

		this.analogControlKnobTexture = new AssetBitmapTexture(textureManger, assets, "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.analogControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.analogControlKnobTexture);
		this.analogControlKnobTexture.load();
		
	}
}
