package org.escoladeltreball.shooter2d.scenes;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.MapCreator;
import org.escoladeltreball.shooter2d.constants.NotificationConstants;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.loader.PlayerLoader;
import org.escoladeltreball.shooter2d.ui.GameObserver;
import org.escoladeltreball.shooter2d.ui.UI;
import org.escoladeltreball.shooter2d.weapons.WeaponFactory;

public class FirstLevel extends Scene implements GameScene, GameObserver {
	
	private Player player;

	public FirstLevel() {
		setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	}

	@Override
	public void populate() {
		registerUpdateHandler(MainActivity.getInstance().mPhysicsWorld);
		// Muestra el mapa en la pantalla
		attachChild(MapCreator.getCurrentMap());
		// crea el player
		this.player = PlayerLoader.loadPlayer(MainActivity.CAMERA_WIDTH / 2, MainActivity.CAMERA_HEIGHT / 2, MainActivity.getInstance().getEngine(), this);
		this.player.setGun(WeaponFactory.getGun(this, MainActivity.getInstance().getEngine()));
		//crea los objetos del mapa
		MapCreator.createMapObjects(this, MainActivity.getInstance().getEngine(), MapCreator.getCurrentMap(), MainActivity.getInstance().getVertexBufferObjectManager(), player);
		// La camara sigue al jugador
		MainActivity.getInstance().camera.setChaseEntity(player);
		attachChild(player);
		// Añade la UI
		UI.getInstance().createUI(MainActivity.getInstance().camera, MainActivity.getInstance().getVertexBufferObjectManager());
		// Se pone a la UI como observador del player 
		this.player.addGameObserver(UI.getInstance());
		// Se pone al MainActivity como observador del player 
		this.player.addGameObserver(this);
	}

	@Override
	public void notify(Object notifier, Object data) {
		if (data instanceof Short) {
			short notification = ((Short)data).shortValue();
			if(notifier == PlayerLoader.getPlayer()) {
				switch (notification) {
				case NotificationConstants.CHANGE_HEALTH:
					if (this.player.getHealthpoints() <= 0)  {
						//cuando muere el player
					}
					break;
				}
			}
		}
	}	

}
