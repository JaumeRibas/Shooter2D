package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;

import android.content.res.AssetManager;

public class PlayerLoader {

	private static Player player;
	
	private PlayerLoader(){};

	/**
	 * Devuelve la instancia del player si existe, si no existe, la crea y la devuelve.
	 * La bala del jugador será la que lleva por defecto en la clase {@link Player}
	 * 
	 * @param camera a {@link Camera}
	 * @param x a integer, la posición horizontal del player
	 * @param y a integer, la posición vertical del players
	 * @param textureManger a {@link TextureManager}
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @param scene a {@link Scene}, la escena donde disparará el player
	 * @return a {@link Player}
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
	
	/**
	 * Devuelve la instancia del player si existe, si no existe, la crea y la devuelve.
	 * 
	 * @param camera a {@link Camera}
	 * @param x a integer, la posición horizontal del player
	 * @param y a integer, la posición vertical del players
	 * @param textureManger a {@link TextureManager}
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @param scene a {@link Scene}, la escena donde disparará el player
	 * @param playerbullet a {@link Bullet}, la bala que tendrá el jugador al iniciar la partida.
	 * @return a {@link Player}
	 */
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
	 * @return a {@link Player}
	 */
	public static Player getPlayer() {
		return player;
	}

}
