package org.escoladeltreball.shooter2d.scenes;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.R;
import org.escoladeltreball.shooter2d.ResourceManager;

/**
 * El menu principal
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class MainMenuScene extends MenuScene implements
		IOnMenuItemClickListener {

	private static final int MENU_START = 0;
	private static final int MENU_EXIT = 1;

	public MainMenuScene(Camera camera, Engine engine, BaseActivity activity) {
		super(camera);
		setBackground(new Background(Color.BLACK));
		float centerY = MainActivity.CAMERA_HEIGHT / 2;
		float widthThird = MainActivity.CAMERA_WIDTH / 3;
		IMenuItem startItem = new TextMenuItem(MENU_START, ResourceManager.getInstance().hudFont, activity.getString(R.string.start),
				engine.getVertexBufferObjectManager());
		startItem.setOffsetCenter(0.5f, 0.5f);
		IMenuItem exitItem = new TextMenuItem(MENU_EXIT, ResourceManager.getInstance().hudFont, activity.getString(R.string.exit),
				engine.getVertexBufferObjectManager());
		exitItem.setOffsetCenter(0.5f, 0.5f);
		exitItem.setPosition(widthThird, centerY);
		startItem.setPosition(widthThird * 2, centerY);
		addMenuItem(startItem);
		addMenuItem(exitItem);
		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START:
			MainActivity.getInstance().startGame();
			return true;
		case MENU_EXIT:
			MainActivity.getInstance().finish();
			return true;
		default:
			break;
		}
		return false;
	}
}
