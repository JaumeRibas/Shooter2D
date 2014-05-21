package org.escoladeltreball.shooter2d.scenes;

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
public class SplashScreen extends Scene implements GameScene {
	
	private static final int SPLASH_DURATION = 3;
	private static final float SPLASH_SCALE_FROM = 0.6f;
	private boolean animationFinished = false;
	private Engine engine;

	public SplashScreen(Engine engine) {
		this.engine = engine;
	}

	public boolean getAnimationFinished() {
		return this.animationFinished;
	}

	@Override
	public void populate() {
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
							MainActivity.getInstance().openMenu();
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
}
