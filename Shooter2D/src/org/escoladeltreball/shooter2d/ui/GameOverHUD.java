package org.escoladeltreball.shooter2d.ui;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.IModifier.IModifierListener;
import org.escoladeltreball.shooter2d.MainActivity;

import android.content.Context;
import android.graphics.Color;

/**
 * El título de game over antes de tener opciones
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class GameOverHUD extends HUD {
	
	private static final int FADE_LAYER_COLOR = Color.BLACK;
	private static final float FADE_LAYER_ALPHA = 0.8f;
	
	private static final float FADING_DURATION = 2;
	
	public GameOverHUD(VertexBufferObjectManager vertexBufferObjectManager, Context context) {
		// game over hud
		//capa oscurecedora
		Rectangle fadeLayer = new Rectangle(0, 0, MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		fadeLayer.setOffsetCenter(0, 0);
		fadeLayer.setColor(FADE_LAYER_COLOR);
		fadeLayer.setAlpha(FADE_LAYER_ALPHA);
		SequenceEntityModifier animation = new SequenceEntityModifier(
				new ParallelEntityModifier(
						new AlphaModifier(FADING_DURATION, 0,1)
				)
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
				System.out.println("animación finished");
				MainActivity.getInstance().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						MainActivity.getInstance().openMenu();
					}
				});
			}
		});
		fadeLayer.registerEntityModifier(animation);
		attachChild(fadeLayer);
	}
}
