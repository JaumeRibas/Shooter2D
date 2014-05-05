package org.escoladeltreball.shooter2d.entities.interfaces;

/**
 * La interficie Attacking establece los metodos que se usarán para el control
 * del ataque de los enemigos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public interface Attacking {

	/**
	 * Realiza un ataque.
	 */
	public abstract void attack();

	/**
	 * Actualiza el timer de cooldown.
	 * 
	 * @param pSecondsElapsed
	 */
	public abstract void manageCooldown(float pSecondsElapsed);

	/**
	 * Vuelve a establecer un coodown.
	 */
	public abstract void resetCooldown();

}