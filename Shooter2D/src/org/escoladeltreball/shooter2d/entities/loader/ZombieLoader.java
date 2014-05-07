package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;

import android.content.res.AssetManager;

public class ZombieLoader {
	
	private ZombieLoader(){};

	/**
	 * La clase PlayerLoader se encarga cargar los recursos del jugador y del propio jugador.
	 * 
	 * @author Carlos Serrano
	 * @author Elvis Puertas
	 * @author Jaume Ribas
	 */
	public static Zombie loadZombie(Camera camera, int x, int y, TextureManager textureManger,
			AssetManager assets,
			VertexBufferObjectManager vertexBufferObjectManager, Player player) {
		
		Zombie zombie = null;
	
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().zombieTextureRegion;

		zombie = new Zombie(x,
				y, pTiledTextureRegion,
				vertexBufferObjectManager, player);
		return zombie;

	}
}
