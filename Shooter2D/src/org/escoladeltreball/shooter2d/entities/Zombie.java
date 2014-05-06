package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.math.MathUtils;
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

	private ActorEntity target;
	
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
		this.target = player;
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
			float xDistance = this.getXDistance(target);
			float yDistance = this.getYDistance(target);
			// Distancia recta, hipotenusa
			float distance = this.getDistance(target);
			// Calculo de seno y coseno
			float xStep = xDistance / distance;
			float yStep = yDistance / distance;
			// Rotación
			this.getBody().setTransform(this.getBody().getPosition(), (float) Math.atan2(-xStep, yStep));
			// Calculo de la velocidad
			this.getBody().setLinearVelocity(xStep * this.speed, yStep * this.speed);
		}
	}

	/**
	 * Makes the zombie attack.
	 */
	public void attack() {
		this.isWalking = false;
		this.setCurrentTileIndex(0);
		if (this.attackCooldownTimer == 0) {
			this.target.hurt(HPConstants.ZOMBIE_STRENGH);
			this.target.setColor(Color.RED);
			this.resetCooldown();
		}
	}

	@Override
	public void manageCooldown(float pSecondsElapsed) {
		this.attackCooldownTimer -= pSecondsElapsed;
		if (this.attackCooldownTimer < 0) {
			this.attackCooldownTimer = 0;
		}
	}

	@Override
	public void resetCooldown() {
		this.attackCooldownTimer = Zombie.ZOMBIE_ATTACK_COOLDOWN;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		this.walk();
		this.manageCooldown(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}

	public ActorEntity getTarget() {
		return target;
	}

	public void setTarget(ActorEntity target) {
		this.target = target;
	}

	@Override
	public void collidesWith(Body otherBody) {
		Object userData = otherBody.getUserData();
		if (userData == this.target) {
			this.attack();
		}		
	}
}
