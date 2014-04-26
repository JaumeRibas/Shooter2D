package org.escoladeltreball.shooter2d.entities.loader;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.constants.SpriteConstants;
import org.escoladeltreball.shooter2d.entities.Player;

import android.content.res.AssetManager;

public class PlayerLoader extends EntityLoader {

	private Player player;

	/**
	 * La clase PlayerLoader se encarga cargar los recursos del jugador y del propio jugador.
	 * 
	 * @author Carlos Serrano
	 * @author Elvis Puertas
	 * @author Jaume Ribas
	 */
	public Player loadPlayer(Camera camera, TextureManager textureManger,
			AssetManager assets,
			VertexBufferObjectManager vertexBufferObjectManager) {
		if (this.player == null) {
			try {
				TiledTextureRegion pTiledTextureRegion;
				pTiledTextureRegion = this.loadResources(textureManger, assets);

				this.player = new Player(camera.getCenterX(),
						camera.getCenterY(), pTiledTextureRegion,
						vertexBufferObjectManager);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.player;

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
