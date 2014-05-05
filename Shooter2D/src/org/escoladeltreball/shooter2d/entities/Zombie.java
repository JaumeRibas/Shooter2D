package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.entities.interfaces.Attacking;
import org.escoladeltreball.shooter2d.entities.interfaces.Targeting;
import org.escoladeltreball.shooter2d.entities.interfaces.Walking;
import org.escoladeltreball.shooter2d.physics.BodyFactory;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Zombie es una GameEntity enemiga controlada por IA que persigue al
 * jugador cuando lo detecta.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Zombie extends IAEntity implements Walking, Attacking, Targeting {

	private float speed = 0.5f;

	private static final float ZOMBIE_ATTACK_COOLDOWN = 3;
	private float attackCooldownTimer = 0;

	private boolean isWalking = false;

	/**
	 * Constructor del Zombie.
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
	public Zombie(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Player player) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

		super.setTarget(player);
		this.attackCooldownTimer = Zombie.ZOMBIE_ATTACK_COOLDOWN;
		Body body = BodyFactory.createHumanBody(pX, pY);
		this.setBody(body);
		this.setColor(Color.GREEN);

		this.setMaxHealthPoints(HPConstants.ZOMBIE_HEALTH);
		this.setHealthpoints(HPConstants.ZOMBIE_HEALTH);
	}

	/**
	 * Camina hacia el objetivo si lo tiene.
	 */
	public void walk() {
		if (this.getTarget() != null) {

			// Inicia la animación si no ha empezado.
			if (!this.isWalking) {
				this.isWalking = true;
				this.animate(400);
			}
			// Distancias en referencia al Zombie, catetos
			float xDistance = this.getXDistance(super.getTarget());
			float yDistance = this.getYDistance(super.getTarget());
			// Distancia recta, hipotenusa
			float distance = this.getDistance(super.getTarget());
			// Calculo de seno y coseno
			float xStep = xDistance / distance;
			float yStep = yDistance / distance;
			// Rotación
			this.getBody().setTransform(this.getBody().getPosition(),
					(float) Math.atan2(-xStep, yStep));
			// Calculo de la velocidad
			this.getBody().setLinearVelocity(xStep * this.speed,
					yStep * this.speed);
		}
	}

	/**
	 * Hace atacar al zombie.
	 */
	public void attack() {
		this.isWalking = false;
		this.setCurrentTileIndex(0);
		if (this.attackCooldownTimer == 0
				&& super.getTarget() instanceof ActorEntity) {
			ActorEntity actorEntity = (ActorEntity) super.getTarget();
			
			actorEntity.hurt(HPConstants.ZOMBIE_STRENGH);
			super.getTarget().setColor(Color.RED);
			this.resetCooldown();
		}
	}

	/**
	 * Actualiza el timer de cooldown.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	public void manageCooldown(float pSecondsElapsed) {
		this.attackCooldownTimer -= pSecondsElapsed;
		if (this.attackCooldownTimer < 0) {
			this.attackCooldownTimer = 0;
		}
	}

	/**
	 * Reestablece el cooldown.
	 */
	@Override
	public void resetCooldown() {
		this.attackCooldownTimer = Zombie.ZOMBIE_ATTACK_COOLDOWN;
	}

	/**
	 * El zombie realiza una acción.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {

		if (this.collidesWith(super.getTarget())) {
			this.attack();
		} else {
			this.walk();
		}

		this.manageCooldown(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}
}