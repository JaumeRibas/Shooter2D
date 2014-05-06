package org.escoladeltreball.shooter2d.entities;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.MainActivity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase ColisionableEntity es una GameEntity hecha para objetos que pueden
 * colisionar, como el jugador, los enemigos y posibles obstaculos del juego.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public abstract class ColisionableEntity extends GameEntity {

	private Body body;

	public ColisionableEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	// GETTERS AND SETTERS
	
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
		MainActivity.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this, this.body));
		this.body.setUserData(this);
	}
	
	/**
	 * Se llama cuando esta {@link ColisionableEntity} colisiona con un {@link Body}.
	 * 
	 * @param otherBody el {@link Body} con el que ha chocado
	 */
	public abstract void collidesWith(Body otherBody);

}
