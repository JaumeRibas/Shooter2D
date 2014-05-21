package org.escoladeltreball.shooter2d.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.GameManager;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.R;
import org.escoladeltreball.shooter2d.ResourceManager;

import android.content.Context;

/**
 * El menu principal
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class StartMenuScene extends MenuScene implements GameScene {

	private Engine engine;
	private Context context;

	public StartMenuScene(Camera camera, Engine engine, Context context, IOnMenuItemClickListener listener) {
		super(camera);
		setBackground(new Background(Color.BLACK));
		this.engine = engine;
		this.context = context;
		setOnMenuItemClickListener(listener);
	}

	@Override
	public void populate() {
		float centerY = MainActivity.CAMERA_HEIGHT / 2;
		float widthThird = MainActivity.CAMERA_WIDTH / 3;
		TextMenuItem startItem = new TextMenuItem(GameManager.MENU_START,
				ResourceManager.getInstance().menuFont,
				context.getString(R.string.start),
				engine.getVertexBufferObjectManager());
		startItem.setOffsetCenter(0.5f, 0.5f);
		TextMenuItem exitItem = new TextMenuItem(GameManager.MENU_EXIT,
				ResourceManager.getInstance().menuFont,
				context.getString(R.string.exit),
				engine.getVertexBufferObjectManager());
		exitItem.setOffsetCenter(0.5f, 0.5f);
		exitItem.setPosition(widthThird, centerY);
		startItem.setPosition(widthThird * 2, centerY);
		addMenuItem(startItem);
		addMenuItem(exitItem);
	}
}
