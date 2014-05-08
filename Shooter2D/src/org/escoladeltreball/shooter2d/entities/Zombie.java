package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.entities.interfaces.Attacking;
import org.escoladeltreball.shooter2d.entities.interfaces.Targeting;
import org.escoladeltreball.shooter2d.entities.interfaces.Walking;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Cooldown;

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

	private float speed = 0.75f;

	private static final float ZOMBIE_ATTACK_TIMER = 3f;
	private Cooldown attackCooldown;

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
		this.attackCooldown = new Cooldown(ZOMBIE_ATTACK_TIMER);
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
		System.out.println("Cooldown Timer: " + this.attackCooldown.toString());
		if (this.attackCooldown.cooldownReady()) {
			ActorEntity actorEntity = (ActorEntity) super.getTarget();
			actorEntity.hurt(HPConstants.ZOMBIE_STRENGH);
			super.getTarget().setColor(Color.RED);
		}
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
		this.attackCooldown.updateTimer(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}

	@Override
	public void beginsContactWith(Body otherBody) {
		
	}
	
	/**
	 * Realiza las acciones que el zombie realiza cuando es herido.
	 * 
	 * @param strengh un integer, la fuerza del ataque recibido enemigo.
	 */
	@Override
	public void hurt(int strengh){
		super.hurt(strengh);
		System.out.println("ZOMBIE HEALTH: " + getHealthpoints() + "/" + getMaxHealthPoints());
		if(getHealthpoints() <= 0){
			System.out.println("ZOMBIE MUERTO");
			this.detachSelf();
			this.setHealthpoints(getMaxHealthPoints());
		}
	}

	@Override
	public void endsContactWith(Body otherBody) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isContactingWith(Body otherBody) {
		Object userData = otherBody.getUserData();
		if (userData == super.getTarget()) {
			this.attack();
		}		
	}
}
