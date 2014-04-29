package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.math.MathUtils;

/**
 * La clase Zombie es una GameEntity enemiga controlada por IA que persigue al
 * jugador cuando lo detecta.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Zombie extends GameEntity {

	private Player player;
	private float speed = 0.5f;

	private float attackCooldown = 3;
	private float attackCooldownTimer;

	/**
	 * Constructor del Zombie.
	 * 
	 * @param pX
	 *            an integer posici�n horizontal del Zombie
	 * @param pY
	 *            an integer posici�n vertical del Zombie
	 * @param pTiledTextureRegion
	 *            a ITiledTextureRegion
	 * @param pVertexBufferObjectManager
	 *            a VertexBufferObjectManager
	 * @Param player a Player to pursue
	 */
	public Zombie(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Player player) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.player = player;
		this.attackCooldownTimer = this.attackCooldown;
	}

	public void walk() {
		float zombieX = this.getX();
		float zombieY = this.getY();

		float playerX = player.getX();
		float playerY = player.getY();

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
		this.setRotation(MathUtils.radToDeg((float)Math.atan2(xStep, yStep)));
		// Calculo de posición
		this.setPosition(this.getX() + (xStep * this.speed), this.getY()
				+ (yStep * this.speed));
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.collidesWith(player)) {
			this.attack();
		} else {
			this.walk();
		}

		this.manageCooldown(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}

	private void attack() {
		if (this.attackCooldownTimer == 0) {
			this.player.setRotation(this.player.getRotation() + 90);
			this.resetCooldown();
			System.out.println("ZOMBIE ATACANDO!");
		}
	}

	private void manageCooldown(float pSecondsElapsed) {
		this.attackCooldownTimer -= pSecondsElapsed;
		if (this.attackCooldownTimer < 0) {
			this.attackCooldownTimer = 0;
		}
	}

	private void resetCooldown() {
		this.attackCooldownTimer = this.attackCooldown;
	}
}
