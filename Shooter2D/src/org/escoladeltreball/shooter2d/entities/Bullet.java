package org.escoladeltreball.shooter2d.entities;

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

	private float speed = 4f;
	private int strengh = 1;
	private float shootAngle;

	private Cooldown bulletTime = new Cooldown(2);

	private boolean attached = true;

	/**
	 * Constructor de la bala.
	 * 
	 * @Param target un {@link GameEntity} para targetear.
	 * @param pX un entero posicion horizontal de la bala.
	 * @param pY un entero posicion vertical de la bala.
	 * @param pTiledTextureRegion una {@link ITiledTextureRegion}.
	 * @param pVertexBufferObjectManager un {@link VertexBufferObjectManager}.
	 * @param angle el angulo de la bala.
	 * @param strengh la vida que quita la bala.
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
		this.setScale(1.0f);
		this.animate(100);
	}
	/**
	 * Mueve la bala recto en el angulo determinado.
	 */
	public void walk() {

		// Calcular movimiento horizontal.
		float xStep = (float) Math.sin(Math.toRadians(shootAngle));

		// Calcular movimiento vertical.
		float yStep = (float) Math.cos(Math.toRadians(shootAngle));

		// Calculo de la velocidad
		this.getBody()
				.setLinearVelocity(xStep * this.speed, yStep * this.speed);
		// Calcular movimiento horizontal.
		float xStep90 = (float) Math.sin(Math.toRadians(shootAngle - 90));

		// Calcular movimiento vertical.
		float yStep90 = (float) Math.cos(Math.toRadians(shootAngle - 90));

		this.getBody().setTransform(this.getBody().getPosition(),
				(float) Math.atan2(-xStep90, yStep90));
	}

	/**
	 * Realiza las acciones que una bala realiza en una actualización.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		bulletTime.updateTimer(pSecondsElapsed);
		this.walk();
		if (bulletTime.cooldownReady()) {
			this.detachSelf();
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
	/**
	 * Realiza las acciones que la bala realiza al chocar contra otro cuerpo:
	 * 
	 * Si el body pertenece a una pared, desaparece.
	 * Si el body pertenece a una {@link ActorEntity}, daña a esa {@link ActorEntity} y luego desaparece.
	 * 
	 * @param otherBody a {@link Body}.
	 */
	public void beginsContactWith(Body otherBody) {

		if (otherBody.getUserData().equals(BodyFactory.WALL_USER_DATA)) {
			this.detachSelf();
		// Contra un ActorEntity hace daño a esa ActorEntity y sale de escena.
		} else if (otherBody.getUserData() instanceof ActorEntity) {
			ActorEntity actor = (ActorEntity) otherBody.getUserData();
			actor.hurt(strengh);
			this.detachSelf();
		}
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getShootAngle() {
		return shootAngle;
	}

	public void setShootAngle(float shootAngle) {
		this.shootAngle = shootAngle;
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
