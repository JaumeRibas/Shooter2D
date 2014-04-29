package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * La clase Player es una GameEntity que controlar� el jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Player extends GameEntity {

	/**
	 * Constructor del Player.
	 * 
	 * @param pX an integer posici�n horizontal del Player
	 * @param pY an integer posici�n vertical del Player
	 * @param pTiledTextureRegion a ITiledTextureRegion
	 * @param pVertexBufferObjectManager a VertexBufferObjectManager
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
}

