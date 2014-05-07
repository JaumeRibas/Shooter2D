package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.entities.interfaces.Walking;
import org.escoladeltreball.shooter2d.physics.BodyFactory;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Bullet es una GameEntity que simula una bala.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Bullet extends ColisionableEntity implements Walking {

	private float speed = 1f;
	private int strengh = 1;
	private float shootAngle;

	/**
	 * Constructor de la bala.
	 * 
	 * @param pX
	 *            un entero posicion horizontal del Zombie
	 * @param pY
	 *            un entero posicion vertical del Zombie
	 * @param pTiledTextureRegion
	 *            una ITiledTextureRegion
	 * @param pVertexBufferObjectManager
	 *            una VertexBufferObjectManager
	 * @Param target un GameEntity para targetear.
	 */
	public Bullet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, float angle,
			int strengh) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		Body body = BodyFactory.createBulletBody(pX, pY);
		this.shootAngle = angle;
		this.strengh = strengh;
		this.setCurrentTileIndex(0);
		this.setBody(body);
		this.setScale(1.2f);
		this.setRotation(angle);
		this.animate(200);

	}

	/**
	 * Camina hacia el objetivo si lo tiene.
	 */
	public void walk() {
		// Calcular movimiento horizontal.
		float xStep = (float) Math.sin(Math.toRadians(shootAngle));
		// Calcular movimiento vertical.
		float yStep = (float) Math.cos(Math.toRadians(shootAngle));
		// Calculo de la velocidad
		this.getBody().setLinearVelocity(xStep * this.speed, yStep * this.speed);
	}

	/**
	 * El zombie realiza una acción.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		
		this.walk();
		super.onManagedUpdate(pSecondsElapsed);
	}

	@Override
	public void collidesWith(Body otherBody) {
		if (otherBody.getUserData() instanceof ActorEntity) {
			ActorEntity actor = (ActorEntity)otherBody.getUserData();
			actor.hurt(strengh);
			this.detachSelf();
		}		
	}

}
