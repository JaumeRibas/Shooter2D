package org.escoladeltreball.shooter2d.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.MapCreator;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.weapons.WeaponFactory;

public class FirstLevel extends Scene implements GameScene {
	
	private Player player;

	public FirstLevel() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}



	@Override
	public void populate() {
		registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
		// Muestra el mapa en la pantalla
		attachChild(MapCreator.getCurrentMap());
		//el jugador tendra una pistola
		this.player.setGun(WeaponFactory.getGun(this, MainActivity.getInstance().getEngine()));
		//crea los objetos del mapa
		MapCreator.createMapObjects(this, MainActivity.getInstance().getEngine(), MapCreator.getCurrentMap(), MainActivity.getInstance().getVertexBufferObjectManager(), player);
		// La camara sigue al jugador
		MainActivity.getInstance().camera.setChaseEntity(player);
		attachChild(player);
	}

}
