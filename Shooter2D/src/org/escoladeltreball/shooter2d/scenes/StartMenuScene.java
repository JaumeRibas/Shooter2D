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
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.ui.activity.BaseActivity;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.GameManager;
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
public class StartMenuScene extends MenuScene implements GameScene {

	private Engine engine;
	private BaseActivity activity;

	public StartMenuScene(Camera camera, Engine engine, BaseActivity activity, IOnMenuItemClickListener listener) {
		super(camera);
		setBackground(new Background(Color.BLACK));
		this.engine = engine;
		this.activity = activity;
		setOnMenuItemClickListener(listener);
	}

	@Override
	public void populate() {
		float centerY = MainActivity.CAMERA_HEIGHT / 2;
		float widthThird = MainActivity.CAMERA_WIDTH / 3;
		TextMenuItem startItem = new TextMenuItem(GameManager.MENU_START,
				ResourceManager.getInstance().menuFont,
				activity.getString(R.string.start),
				engine.getVertexBufferObjectManager());
		startItem.setOffsetCenter(0.5f, 0.5f);
		TextMenuItem exitItem = new TextMenuItem(GameManager.MENU_EXIT,
				ResourceManager.getInstance().menuFont,
				activity.getString(R.string.exit),
				engine.getVertexBufferObjectManager());
		exitItem.setOffsetCenter(0.5f, 0.5f);
		exitItem.setPosition(widthThird, centerY);
		startItem.setPosition(widthThird * 2, centerY);
		addMenuItem(startItem);
		addMenuItem(exitItem);
	}
}
