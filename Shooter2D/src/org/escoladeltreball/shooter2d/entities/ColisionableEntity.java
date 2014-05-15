package org.escoladeltreball.shooter2d.entities;

import org.andengine.engine.Engine;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.MainActivity;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase abstracta ColisionableEntity es una {@link GameEntity} hecha para objetos que pueden
 * colisionar, como el jugador, los enemigos y posibles obstaculos del juego.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public abstract class ColisionableEntity extends GameEntity {

	private Body body;
	
	private volatile boolean removeSelf = false;

	private Engine engine;

	public ColisionableEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			Engine engine) {
		super(pX, pY, pTiledTextureRegion, engine.getVertexBufferObjectManager());
		this.engine = engine;
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
	 * Se llama cuando esta {@link ColisionableEntity} empieza a colisionar con un {@link Body}.
	 * 
	 * @param otherBody el {@link Body} con el que ha chocado
	 */
	public abstract void beginsContactWith(Body otherBody);
	
	/**
	 * Se llama cuando esta {@link ColisionableEntity} deja de colisionar con un {@link Body}.
	 * 
	 * @param otherBody el {@link Body} con el que ha chocado
	 */
	public abstract void endsContactWith(Body otherBody);
	
	
	/**
	 * Se llama cada step del {@link PhysicsWorld} en que esta 
	 * {@link ColisionableEntity} esta colisionando con un {@link Body} 
	 * (antes de que se calcule la colision).
	 * 
	 * @param otherBody el {@link Body} con el que esta chocando
	 */
	public abstract void isContactingWith(Body otherBody);
	
	
	/**
	 * Se borra a si mismo, incluyendo el Body.
	 * No llamar desde metodos de colision.
	 * http://www.iforce2d.net/b2dtut/removing-bodies
	 */
	private void removeSelf() {
		this.detachSelf();
		this.clearUpdateHandlers();
		MainActivity.mPhysicsWorld.unregisterPhysicsConnector(
				MainActivity.mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(this));
		MainActivity.mPhysicsWorld.destroyBody(this.getBody());
	}
	
	/**
	 * Borra esta {@link ColisionableEntity} incluyendo su {@link Body}.
	 */
	public void remove() {
		this.removeSelf = true;
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.removeSelf) {
			engine.runOnUpdateThread(new Runnable() {
		        @Override
		        public void run() {
		            ColisionableEntity.this.removeSelf();
		        }
		    });
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
}
