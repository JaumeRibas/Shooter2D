package org.escoladeltreball.shooter2d;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.controller.MultiTouch;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.math.MathUtils;

import android.widget.Toast;

public class MainActivity extends SimpleBaseGameActivity
{
	private Camera camera;
	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;
	
	private ITexture mOnScreenControlBaseTexture;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITexture mOnScreenControlKnobTexture;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	@Override
	public EngineOptions onCreateEngineOptions()
	{
		checkCompatibilityMultiTouch();
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new FillResolutionPolicy(), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() throws IOException
	{
		this.mOnScreenControlBaseTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_base.png", TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlBaseTexture);
		this.mOnScreenControlBaseTexture.load();

		this.mOnScreenControlKnobTexture = new AssetBitmapTexture(this.getTextureManager(), this.getAssets(), "gfx/onscreen_control_knob.png", TextureOptions.BILINEAR);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory.extractFromTexture(this.mOnScreenControlKnobTexture);
		this.mOnScreenControlKnobTexture.load();
	}

	@Override
	protected Scene onCreateScene()
	{
		Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		
		/* Control analogico izquierda */
		final AnalogOnScreenControl leftOnScreenControl = new AnalogOnScreenControl(0, 0, this.camera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				//accion al mover el analog
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				/* Nothing. */
			}
		});

		{
			final Sprite controlBase = leftOnScreenControl.getControlBase();
			controlBase.setAlpha(0.5f);
			controlBase.setOffsetCenter(0, 0);
	
			scene.setChildScene(leftOnScreenControl);
		}


		/* Control analogico derecha. */
		final AnalogOnScreenControl rightOnScreenControl = new AnalogOnScreenControl(CAMERA_WIDTH, 0, this.camera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f, this.getVertexBufferObjectManager(), new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				//accion al mover el analog
			}

			@Override
			public void onControlClick(final AnalogOnScreenControl pAnalogOnScreenControl) {
				/* Nothing. */
			}
		});

		{
			final Sprite controlBase = rightOnScreenControl.getControlBase();
			controlBase.setOffsetCenter(1, 1);
			controlBase.setAlpha(0.5f);
	
			leftOnScreenControl.setChildScene(rightOnScreenControl);
		}
		
		return scene;
	}

	/**
	 * Comprueba si existe compatibilidad con multitouch
	 */
	public void checkCompatibilityMultiTouch(){
		if(MultiTouch.isSupported(this)) {  
			if(!MultiTouch.isSupportedDistinct(this)) { 
				Toast.makeText(this, "MultiTouch detected, but your device has problems distinguishing between fingers.", Toast.LENGTH_LONG).show();  
			}  
		} else {  
			Toast.makeText(this, "Sorry your device does NOT support MultiTouch!", Toast.LENGTH_LONG).show();  
		}  
	}
}