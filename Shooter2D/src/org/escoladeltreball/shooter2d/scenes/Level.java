package org.escoladeltreball.shooter2d.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXTiledMap;

/**
 * Los niveles del juego
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public abstract class Level {
	
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
}
