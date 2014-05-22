package org.escoladeltreball.shooter2d;

/*
 * This file is part of shooter2d, a cenital shooter 2D game.
 *
 * Copyright (C) 2014	
 * 						Elvis Puertas <epuertas@gmail.com>
 *						Jaume Ribas <r.ribas.jaume@gmail.com>
 *						Carlos Serrano <arquak@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
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
	private BitmapTextureAtlas mButtonSoundtexture;
	private TextureRegion mSoundOffButtonTextureRegion;
	private Sprite mSoundOffButton;
	private Sprite mSongOnButton;
	private TextureRegion mSongOffButtonTextureRegion;
	private TextureRegion mSongOnButtonTextureRegion;
	private BitmapTextureAtlas mButtonSongtexture;

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		mBackground = new BitmapTextureAtlas(this.getTextureManager(), 5000, 5000,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtontexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtonSoundtexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mButtonSongtexture = new BitmapTextureAtlas(this.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		mStartButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtontexture, this, "button_start.png", 0, 0);
		mScoresButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtontexture, this, "button_options.png", 0, 70);
		mSoundOnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonSoundtexture, this, "button_sound_on.png", 0, 70);
		mSoundOffButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonSoundtexture, this, "button_sound_off.png", 0, 70);
		
		mSongOnButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonSongtexture, this, "button_music_on.png", 0, 70);
		mSongOffButtonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonSongtexture, this, "button_music_off.png", 0, 70);
		
		this.mEngine.getTextureManager().loadTexture(mButtontexture);
		this.mEngine.getTextureManager().loadTexture(mButtonSoundtexture);
		this.mEngine.getTextureManager().loadTexture(mButtonSongtexture);
		this.mEngine.getTextureManager().loadTexture(mBackground);
	}

	@Override
	public Scene onCreateScene() {
		Scene scene = new Scene();

		createStartButton();
		createOptionsButton();
		createSongButton();
		createSoundButton();

		scene.attachChild(mStartButton);
		scene.attachChild(mScoresButton);
		scene.attachChild(mSoundOnButton);
		scene.attachChild(mSongOnButton);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.registerTouchArea(mStartButton);
		scene.registerTouchArea(mSoundOnButton);
		scene.registerTouchArea(mSongOnButton);
		scene.registerTouchArea(mScoresButton);

		scene.setBackground(new Background(Color.BLACK)); 

		return scene;
	}

	public void createStartButton(){
		this.mStartButton = new Sprite((float) (CAMERA_WIDTH/2-70), (float) (CAMERA_HEIGHT/4),
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
	}

	public void createOptionsButton(){
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
//					Intent start = new Intent(MenuActivity.this,
//							SplashScreenActivity.class);
//					startActivity(start);
				}
				return true;
			}
		};
	}

	public void createSongButton(){
		mSongOnButton = new Sprite((float) (CAMERA_WIDTH-50), (float) (CAMERA_HEIGHT-50),
				mSongOnButtonTextureRegion, this.mEngine.getVertexBufferObjectManager()) {
			private Sprite mSongOffButton;

			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				super.onManagedUpdate(pSecondsElapsed);
			}

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float
					pTouchAreaLocalY) {

				if (pSceneTouchEvent.isActionDown()) {
					mSongOnButton = mSongOffButton;
				}
				return true;
			}
		};
	}
	
	public void createSoundButton(){
		mSoundOnButton = new Sprite((float) (CAMERA_WIDTH-115), (float) (CAMERA_HEIGHT-50),
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
					mSoundOnButton = mSoundOffButton;
				}
				return true;
			}
		};
	}

}
