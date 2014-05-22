package org.escoladeltreball.shooter2d.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.text.Text;
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
public class WinnerMenuScene extends MenuScene implements GameScene {
	
	private static final int WINNER_TEXT_MAX_CHARACTER_COUNT = 20;
	public static final float WINNER_TEXT_X = (float)(MainActivity.CAMERA_WIDTH / 2.0);
	public static final float WINNER_TEXT_Y = (float)(MainActivity.CAMERA_HEIGHT / 2.0 + 50);
	
	private static final float EXIT_ITEM_Y = (float)(MainActivity.CAMERA_HEIGHT / 2.0 - 50);
	private static final float RESUME_ITEM_Y = EXIT_ITEM_Y;
	private Engine engine;
	private Context context;

	public WinnerMenuScene(Camera camera, Engine engine, Context context, IOnMenuItemClickListener listener) {
		super(camera);
		setBackground(new Background(Color.BLACK));
		this.engine = engine;
		this.context = context;
		setOnMenuItemClickListener(listener);
	}

	@Override
	public void populate() {
		//titulo game over
		Text gameOverText = new Text(WINNER_TEXT_X, WINNER_TEXT_Y, ResourceManager.getInstance().gameOverFont, context.getString(R.string.you_sourvived), WINNER_TEXT_MAX_CHARACTER_COUNT, engine.getVertexBufferObjectManager());
		float widthThird = MainActivity.CAMERA_WIDTH / 3;
		TextMenuItem retryItem = new TextMenuItem(GameManager.MENU_RETRY,
				ResourceManager.getInstance().menuFont,
				context.getString(R.string.restart),
				engine.getVertexBufferObjectManager());
		retryItem.setOffsetCenter(0.5f, 0.5f);
		TextMenuItem exitItem = new TextMenuItem(GameManager.MENU_EXIT,
				ResourceManager.getInstance().menuFont,
				context.getString(R.string.exit),
				engine.getVertexBufferObjectManager());
		exitItem.setOffsetCenter(0.5f, 0.5f);
		exitItem.setPosition(widthThird, EXIT_ITEM_Y);
		retryItem.setPosition(widthThird * 2, RESUME_ITEM_Y);
		attachChild(gameOverText);
		addMenuItem(retryItem);
		addMenuItem(exitItem);
		setOnMenuItemClickListener(GameManager.getInstance());
	}
}
