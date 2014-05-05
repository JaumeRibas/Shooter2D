package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.entities.interfaces.Targeting;

public abstract class IAEntity extends ActorEntity implements Targeting{

	private GameEntity target;
	
	public IAEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	public GameEntity getTarget() {
		return target;
	}

	public void setTarget(GameEntity target) {
		this.target = target;
	}

}
