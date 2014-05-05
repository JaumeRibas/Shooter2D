package org.escoladeltreball.shooter2d;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;

import android.content.Intent;

/**
 * SplashScreenActivity
 * @author 
 *
 */
public class SplashScreenActivity extends SimpleBaseGameActivity {

	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = MainActivity.CAMERA_WIDTH;
	private static final int CAMERA_HEIGHT = MainActivity.CAMERA_HEIGHT;

	// ===========================================================
	// Fields
	// ===========================================================

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion mFaceTextureRegion;
	
	private static final int SPLASH_DURATION = 3;
	private static final float SPLASH_SCALE_FROM = 0.6f;
	private static final String IMAGE = "splash.png";
	private static final Class<MenuActivity> FOLLOWING_ACTIVITY = MenuActivity.class;	

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		this.mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmapTextureAtlas, this, IMAGE, 0, 0);
		this.mBitmapTextureAtlas.load();
	}

	@Override
	public Scene onCreateScene() {
		final Scene scene = new Scene();
		scene.setBackground(new Background(0,0,0));

		/* Añaade la imagen en el centro de la pantalla. */
		final int centerX = (int) (CAMERA_WIDTH / 2);
		final int centerY = (int) (CAMERA_HEIGHT / 2);

		/* Crea y añaade la imagen a la scene. */
		final Sprite splashimage = new Sprite(centerX, centerY, this.mFaceTextureRegion, this.mEngine.getVertexBufferObjectManager());
	
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
				SplashScreenActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {}
				});
			}

			@Override
			public void onModifierFinished(final IModifier<IEntity> pEntityModifier, final IEntity pEntity) {
				SplashScreenActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(SplashScreenActivity.this, FOLLOWING_ACTIVITY);
            			startActivity(intent);
            			finish();
					}
				});
			}
		});		
		splashimage.setAlpha(0);
		splashimage.setScale(SPLASH_SCALE_FROM);
		splashimage.registerEntityModifier(animation);		
		scene.attachChild(splashimage);

		return scene;
	}

}
