package org.escoladeltreball.shooter2d.entities;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * La clase Abstracta GameEntity gestiona la carga gr�fica de la entidad y las
 * acciones comunes de todas las entidades, el jugador y los distintos
 * monstruos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class GameEntity extends AnimatedSprite {

	public GameEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
}
