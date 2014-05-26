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

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.escoladeltreball.shooter2d.constants.NotificationConstants;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.scenes.Level;
import org.escoladeltreball.shooter2d.ui.GameObserver;
import org.escoladeltreball.shooter2d.ui.UI;

/**
 * Controla el flujo del juego
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class GameManager implements GameObserver, IOnMenuItemClickListener {

	public static final int MENU_START = 0;
	public static final int MENU_EXIT = 1;
	public static final int MENU_RESUME = 2;

	public static final int MENU_RETRY = 3;
	
	/** instancia unica */
	private static GameManager instance;

	private boolean gameStarted = false;

	private boolean gameLost = false;
	
	private boolean gameWon = false;
	
	public void setStarted(boolean started) {
		this.gameStarted = started;
	}

	public boolean isStarted() {
		return gameStarted;
	}

	private GameManager() {
	}

	/**
	 * Este metodo devuelve la instancia unica de GameManager. Si no existe, la
	 * crea.
	 * 
	 * @return la instancia unica de GameManager
	 */
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	@Override
	public void notify(Object notifier, Object data) {
		if (data instanceof Short) {
			short notification = ((Short) data).shortValue();
			if (notifier == PlayerLoader.getPlayer()) {
				switch (notification) {
				case NotificationConstants.CHANGE_HEALTH:
					if (PlayerLoader.getPlayer().getHealthpoints() <= 0)  {
						this.gameLost  = true;
						showGameOver();
					}
					break;
				}
			} else if (notifier instanceof Level) {
				switch (notification) {
				case NotificationConstants.LEVEL_WON:
					this.gameWon = true;
					MainActivity.getInstance().openMenu();
					break;
				}
			}
		}
	}

	/**
	 * Muestra la pantalla de GameOver al llegar a 0 la vida del jugador
	 */
	private void showGameOver() {
		MainActivity.getInstance().getCurrentLevel().getScene().setIgnoreUpdate(true);
		MainActivity.getInstance().setCurrentHUD(UI.getGameOverHUD());		
	}

	@Override
	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START:
			this.setStarted(true);
			MainActivity.getInstance().setCurrentHUD(UI.getUIHUD());
		case MENU_RESUME:
			MainActivity.getInstance().openGame();
			return true;
		case MENU_EXIT:
			MainActivity.getInstance().closeActivity();
			return true;
		case MENU_RETRY:
			MainActivity.getInstance().getCurrentLevel().restart();
			MainActivity.getInstance().openGame();
			return true;
		default:
			break;
		}
		return false;
	}

	// Getters y setters
	public boolean isGameLost() {
		return this.gameLost;
	}

	public boolean isGameWon() {
		return this.gameWon;
	}
}
