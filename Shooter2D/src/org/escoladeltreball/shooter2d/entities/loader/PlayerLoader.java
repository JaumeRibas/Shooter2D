package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;

public class PlayerLoader {

	private static Player player;
	
	private PlayerLoader(){};

	/**
	 * La clase PlayerLoader se encarga cargar los recursos del jugador y del propio jugador.
	 * 
	 * @author Carlos Serrano
	 * @author Elvis Puertas
	 * @author Jaume Ribas
	 * @param scene 
	 */
	public static Player loadPlayer(float x, float y, VertexBufferObjectManager vertexBufferObjectManager, Scene scene) {
		if (player == null) {
			TiledTextureRegion pTiledTextureRegion;
			pTiledTextureRegion = ResourceManager.getInstance().playerTextureRegion;

			player = new Player(x, y, pTiledTextureRegion,
					vertexBufferObjectManager, scene);
		}
		return player;

	}
	
	public static Player loadPlayer(float x, float y, VertexBufferObjectManager vertexBufferObjectManager, Scene scene, Bullet playerbullet) {
		if (player == null) {
			TiledTextureRegion pTiledTextureRegion;
			pTiledTextureRegion = ResourceManager.getInstance().playerTextureRegion;

			player = new Player(x, y, pTiledTextureRegion,
					vertexBufferObjectManager, scene, playerbullet);
		}
		return player;
	}
	
	/**
	 * Obtiene la instancia de player.
	 * Puede ser null si todavia no se ha creado.
	 * 
	 * @return
	 */
	public static Player getPlayer() {
		return player;
	}

}
