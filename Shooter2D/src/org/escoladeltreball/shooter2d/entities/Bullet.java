package org.escoladeltreball.shooter2d.entities;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Cooldown;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Bullet es una {@link ColisionableEntity} que simula una bala.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Bullet extends ColisionableEntity {

	private float speed = 0.2f;
	private int strengh = 1;

	private Cooldown bulletTime = new Cooldown(2);

	private boolean attached = true;

	/**
	 * Constructor de la bala.
	 * 
	 * @Param target un {@link GameEntity} para targetear.
	 * @param pX
	 *            un entero posicion horizontal de la bala.
	 * @param pY
	 *            un entero posicion vertical de la bala.
	 * @param pTiledTextureRegion
	 *            una {@link ITiledTextureRegion}.
	 * @param pVertexBufferObjectManager
	 *            un {@link VertexBufferObjectManager}.
	 * @param angle
	 *            el angulo de la bala.
	 * @param strengh
	 *            la vida que quita la bala.
	 */
	public Bullet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			Engine engine, float angle, int strengh) {
		super(pX, pY, pTiledTextureRegion, engine);
		Body body = BodyFactory.createBulletBody(pX, pY);
		this.strengh = strengh;
		this.setCurrentTileIndex(0);
		this.setBody(body);
		this.setScale(1.0f);
		this.animate(100);
	}

	/**
	 * Realiza las acciones que una bala realiza en una actualización.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (bulletTime.cooldownReady()) {
			this.remove();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}

	/**
	 * Realiza las acciones que la bala realiza al chocar contra otro cuerpo.
	 * 
	 * Si el body pertenece a una pared, desaparece. Si el body pertenece a una
	 * {@link ActorEntity}, daña a esa {@link ActorEntity} y luego desaparece.
	 * 
	 * @param otherBody
	 *            a {@link Body}.
	 */
	public void beginsContactWith(Body otherBody) {

		if (otherBody.getUserData().equals(BodyFactory.WALL_USER_DATA)) {
			this.remove();
			// Contra un ActorEntity hace daño a esa ActorEntity y sale de
			// escena.
		} else if (otherBody.getUserData() instanceof ActorEntity) {
			ActorEntity actor = (ActorEntity) otherBody.getUserData();
			actor.hurt(strengh);
			this.remove();
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void endsContactWith(Body otherBody) {
		// TODO Auto-generated method stub

	}

	@Override
	public void isContactingWith(Body otherBody) {
		// TODO Auto-generated method stub

	}
}
