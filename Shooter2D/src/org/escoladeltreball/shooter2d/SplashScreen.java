package org.escoladeltreball.shooter2d;

import org.andengine.engine.Engine;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

/**
 * SplashScreenActivity
 * @author 
 *
 */
public class SplashScreen extends Scene {
	
	private static final int SPLASH_DURATION = 3;
	private static final float SPLASH_SCALE_FROM = 0.6f;

	public SplashScreen(Engine engine) {
		/* Crea y añaade la imagen a la scene. */
		final Sprite splashimage = new Sprite(MainActivity.CAMERA_WIDTH/2, MainActivity.CAMERA_HEIGHT/2, ResourceManager.getInstance().splashTextureRegion, engine.getVertexBufferObjectManager());
	
		SequenceEntityModifier animation = new SequenceEntityModifier(
				new DelayModifier(0.5f),
				new ParallelEntityModifier(
						new ScaleModifier(SPLASH_DURATION, SPLASH_SCALE_FROM,1),
						new AlphaModifier(SPLASH_DURATION, 0,1)
				),
				new DelayModifier(0.5f)			
		);
		animation.addModifierListener(new IModifierListener<IEntity>() {
			@Override
			public void onModifierStarted(final IModifier<IEntity> pModifier, final IEntity pItem) {
				MainActivity.getInstance().runOnUiThread(new Runnable() {
					@Override
					public void run() {}
				});
			}

			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				MainActivity.getInstance().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MainActivity.getInstance().openMainMenu();
					}
				});
			}
		});		
		splashimage.setAlpha(0);
		splashimage.setScale(SPLASH_SCALE_FROM);
		splashimage.registerEntityModifier(animation);		
		this.attachChild(splashimage);
	}
}
