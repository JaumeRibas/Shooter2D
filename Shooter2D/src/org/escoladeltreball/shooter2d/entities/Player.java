package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Player es una GameEntity que controlara el jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Player extends ActorEntity {

	/**
	 * Constructor del Player.
	 * 
	 * @param pX
	 *            an integer posicion horizontal del Player
	 * @param pY
	 *            an integer posicion vertical del Player
	 * @param pTiledTextureRegion
	 *            a ITiledTextureRegion
	 * @param pVertexBufferObjectManager
	 *            a VertexBufferObjectManager
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		Body body = null;
		this.setBody(body);
	}
}