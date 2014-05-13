package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;

import android.content.res.AssetManager;

/**
 * La clase ZombieLoader se encarga cargar los recursos del jugador y del propio jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class ZombieLoader {
	
	private ZombieLoader(){};
	/**
	 * Devuelve un zombie nuevo.
	 * 
	 * @param camera a {@link Camera}
	 * @param x a integer, la posición horizontal del zombie
	 * @param y a integer, la posición vertical del zombie
	 * @param textureManger a {@link TextureManager}
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @param player a {@link Player} a perseguir
	 * @return a {@link Zombie}
	 */
	public static Zombie loadZombie(Camera camera, int x, int y, Engine engine, Player player) {
		
		Zombie zombie = null;
	
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().zombieTextureRegion;

		zombie = new Zombie(x,
				y, pTiledTextureRegion,
				engine, player);
		return zombie;
	}
}
