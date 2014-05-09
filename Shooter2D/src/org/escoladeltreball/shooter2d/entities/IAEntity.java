package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.entities.interfaces.Targeting;

/**
 * La clase abstracta IAEntity es una {@link ActorEntity} que tiene una inteligencia y reacciona a
 * las acciones del jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class IAEntity extends ActorEntity implements Targeting {

	private GameEntity target;

	public IAEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	// GETTERS AND SETTERS

	public GameEntity getTarget() {
		return target;
	}

	public void setTarget(GameEntity target) {
		this.target = target;
	}

}
