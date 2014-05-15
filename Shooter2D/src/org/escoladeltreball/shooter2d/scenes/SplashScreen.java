package org.escoladeltreball.shooter2d.scenes;

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
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.ResourceManager;

/**
 * La splash screen del inicio
 *
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 *
 */
public class SplashScreen extends Scene {
	
	private static final int SPLASH_DURATION = 3;
	private static final float SPLASH_SCALE_FROM = 0.6f;
	private boolean animationFinished = false;

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
				if (MainActivity.getInstance().getPopulateFinished()) {
					MainActivity.getInstance().runOnUiThread(new Runnable() {
						@Override
						public void run() {
							MainActivity.getInstance().openMainMenu();
						}
					});
				}
				SplashScreen.this.animationFinished = true;
			}
		});		
		splashimage.setAlpha(0);
		splashimage.setScale(SPLASH_SCALE_FROM);
		splashimage.registerEntityModifier(animation);		
		this.attachChild(splashimage);
	}

	public boolean getAnimationFinished() {
		return this.animationFinished;
	}
}
