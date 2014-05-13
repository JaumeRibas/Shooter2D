package org.escoladeltreball.shooter2d;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.color.Color;

import android.content.Intent;

public class MenuActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = MainActivity.CAMERA_WIDTH;
	private static final int CAMERA_HEIGHT = MainActivity.CAMERA_HEIGHT;

	private BitmapTextureAtlas mBackground;
	private BitmapTextureAtlas mButtontexture;
	private TextureRegion mStartButtonTextureRegion;
	private TextureRegion mScoresButtonTextureRegion;
	private TextureRegion mSoundOnButtonTextureRegion;
	private Sprite mStartButton;
	private Sprite mScoresButton;
	private Sprite mSoundOnButton;
	private BitmapTextureAtlas mButtontexture2;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		mBackground = new BitmapTextureAtlas(this.getTextureManager(), 512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtontexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtontexture2 = new BitmapTextureAtlas(this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		mStartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtontexture, this, "button_start.png", 0, 0);
		mScoresButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtontexture, this, "button_options.png", 0, 70);
		mSoundOnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtontexture2, this, "button_sound_on.png", 0, 70);
		this.mEngine.getTextureManager().loadTexture(mButtontexture);
		this.mEngine.getTextureManager().loadTexture(mButtontexture2);
		this.mEngine.getTextureManager().loadTexture(mBackground);
	}

	@Override
	public Scene onCreateScene() {
		Scene scene = new Scene();

		mStartButton = new Sprite((float) (CAMERA_WIDTH/2-70), (float) (CAMERA_HEIGHT/4),
				mStartButtonTextureRegion, this.mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
			}

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					Intent start = new Intent(MenuActivity.this, MainActivity.class);
					startActivity(start);
				}
				return true;
			}
		};

		mScoresButton = new Sprite((float) (CAMERA_WIDTH/2+70), (float) (CAMERA_HEIGHT/4),
				mScoresButtonTextureRegion, this.mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {

				super.onManagedUpdate(pSecondsElapsed);
			}

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					Intent start = new Intent(MenuActivity.this,
							SplashScreenActivity.class);
					startActivity(start);
				}
				return true;
			}
		};
		
		mSoundOnButton = new Sprite((float) (CAMERA_WIDTH-50), (float) (CAMERA_HEIGHT-50),
				mSoundOnButtonTextureRegion, this.mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
			}

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float
					pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					Intent start = new Intent(MenuActivity.this, MainActivity.class);
					startActivity(start);
				}
				return true;
			}
		};

		scene.attachChild(mStartButton);
		scene.attachChild(mScoresButton);
		scene.attachChild(mSoundOnButton);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.registerTouchArea(mStartButton);
		scene.registerTouchArea(mSoundOnButton);
		scene.registerTouchArea(mScoresButton);
		
		scene.setBackground(new Background(Color.BLACK));
        
		return scene;
	}

}
