package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;
import org.andengine.util.math.MathUtils;
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
public class Zombie extends ActorEntity implements Walking, Attacking,
		Targeting {

	private GameEntity target;

	private float speed = 0.5f;

	private float attackCooldown = 3;
	private float attackCooldownTimer;

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
		this.attackCooldownTimer = this.attackCooldown;
		Body body = BodyFactory.createHumanBody(pX, pY);
		this.setBody(body);
		this.setColor(Color.GREEN);
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
			float zombieX = this.getX();
			float zombieY = this.getY();

			float playerX = target.getX();
			float playerY = target.getY();

			// Distancias en referencia al Zombie, catetos
			float xDistance = playerX - zombieX;
			float yDistance = playerY - zombieY;
			// Distancia recta, hipotenusa
			float distance = (float) Math.sqrt(Math.pow(xDistance, 2)
					+ Math.pow(yDistance, 2));
			// Calculo de seno y coseno
			float xStep = xDistance / distance;
			float yStep = yDistance / distance;
			// Calculo de rotación
			float rotationAngle = (float) Math.sin(yStep);
			this.setRotation(MathUtils.radToDeg((float) Math
					.atan2(xStep, yStep)));
			// Calculo de posición
			this.setPosition(this.getX() + (xStep * this.speed), this.getY()
					+ (yStep * this.speed));
		}
	}

	/**
	 * Makes the zombie attack.
	 */
	public void attack() {
		this.isWalking = false;
		this.setCurrentTileIndex(0);
		if (this.attackCooldownTimer == 0) {
			// PLACEHOLDER ACTION TO ATTACK
			this.target.setRotation(this.target.getRotation() + 90);
			this.target.setColor(Color.RED);
			this.resetCooldown();
			System.out.println("ZOMBIE ATACANDO!");
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
		this.attackCooldownTimer = this.attackCooldown;
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.collidesWith(target)) {
			this.attack();
		} else {
			this.walk();
		}

		this.manageCooldown(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}

	public GameEntity getTarget() {
		return target;
	}

	public void setTarget(GameEntity target) {
		this.target = target;
	}
}
