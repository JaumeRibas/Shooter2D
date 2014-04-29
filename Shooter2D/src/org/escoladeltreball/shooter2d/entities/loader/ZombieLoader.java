package org.escoladeltreball.shooter2d.entities.loader;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.constants.SpriteConstants;
import org.escoladeltreball.shooter2d.entities.Player;
import org.escoladeltreball.shooter2d.entities.Zombie;

import android.content.res.AssetManager;

public class ZombieLoader extends EntityLoader {

	/**
	 * La clase PlayerLoader se encarga cargar los recursos del jugador y del propio jugador.
	 * 
	 * @author Carlos Serrano
	 * @author Elvis Puertas
	 * @author Jaume Ribas
	 */
	public Zombie loadZombie(Camera camera, int x, int y, TextureManager textureManger,
			AssetManager assets,
			VertexBufferObjectManager vertexBufferObjectManager, Player player) {
		
			Zombie zombie = null;
		
			try {
				TiledTextureRegion pTiledTextureRegion;
				pTiledTextureRegion = this.loadResources(textureManger, assets);

				zombie = new Zombie(x,
						y, pTiledTextureRegion,
						vertexBufferObjectManager, player);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return zombie;

	}
	/**
	 * Carga recursos relacionados con el jugador.
	 * 
	 * @param textureManger un TextureManager
	 * @param assets un AssetManager
	 * @throws IOException
	 */
	public TiledTextureRegion loadResources(TextureManager textureManger,
			AssetManager assets) throws IOException {
		return super.loadResources(textureManger, assets,
				SpriteConstants.PLAYER_SPRITE,
				SpriteConstants.PLAYER_SPRITE_COLUMNS,
				SpriteConstants.PLAYER_SPRITE_ROWS);
	}
}
