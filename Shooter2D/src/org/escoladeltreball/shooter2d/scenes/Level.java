package org.escoladeltreball.shooter2d.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXTiledMap;
import org.escoladeltreball.shooter2d.ui.GameObserver;

/**
 * Los niveles del juego
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public abstract class Level {
	
	private GameObserver observer;
	
	public Level(GameObserver observer) {
		this.observer = observer;
	}
	
	public GameObserver getGameObserver() {
		return observer;
	}

	public void setGameObserver(GameObserver observer) {
		this.observer = observer;
	}

	/** La escena del nivel */
	private Scene scene;
	
	/** El mapa del nivel */
	private TMXTiledMap map;
	
	/**
	 * Crea la escena del nivel
	 */
	public abstract void createScene();
	
	/**
	 * Reinicia el nivel
	 */
	public abstract void restart();

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public TMXTiledMap getMap() {
		return this.map;
	}
	
	public void setMap(TMXTiledMap map) {
		this.map = map;
	}
	
	/**
	 * Nofica un evento a su {@link GameObserver}
	 * 
	 * @param data
	 */
	public void notify(Object data) {
		int i = 0;
		this.observer.notify(this, data);
	}
}
