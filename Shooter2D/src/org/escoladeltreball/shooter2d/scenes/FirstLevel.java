package org.escoladeltreball.shooter2d.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.MapCreator;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.weapons.WeaponFactory;

/**
 * El primer nivel
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class FirstLevel extends Level implements GameScene {
	
	private Player player;
	
	private static final float START_PLAYER_X = (float)(MainActivity.CAMERA_WIDTH / 2.0);
	private static final float START_PLAYER_Y = (float)(MainActivity.CAMERA_HEIGHT / 2.0);
	private static final float START_PLAYER_ANGLE = 0;
	
	public static final String MAP_PATH = "tmx/base.tmx";

	public FirstLevel() {
		
	}
	
	@Override
	public void createScene() {
		Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
		setScene(scene);
	}	
	
	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public void populate() {
		getScene().registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
		// Muestra el mapa en la pantalla
		getScene().attachChild(getMap());
		// La camara sigue al jugador
		MainActivity.getInstance().camera.setChaseEntity(player);
		//ponemos al jugador en su posicion inicial
//		this.player.getBody().setTransform(START_PLAYER_X, START_PLAYER_Y, START_PLAYER_ANGLE);
		//el jugador tendra una pistola
		this.player.setGun(WeaponFactory.getGun(getScene(), MainActivity.getInstance().getEngine()));
		//metemos al player
		getScene().attachChild(player);
		//crea los objetos del mapa
		MapCreator.createMapObjects(getMap(), getScene(), MainActivity.getInstance().getEngine(), MainActivity.getInstance().getVertexBufferObjectManager(), player);
		
//		this.player.getBody().setTransform(MainActivity.CAMERA_WIDTH / 2.0f, MainActivity.CAMERA_HEIGHT / 2.0f, START_PLAYER_ANGLE);		
	}

	@Override
	public void restart() {
		getScene().reset();
		populate();
	}
}
