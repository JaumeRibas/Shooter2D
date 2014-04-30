package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * La clase abstracta ActorEntity es una ColisionableEntity que designa a personajes
 * actores, como el jugador y los enemigos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public abstract class ActorEntity extends ColisionableEntity {

	public ActorEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

}
