package org.escoladeltreball.shooter2d.entities;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.escoladeltreball.shooter2d.constants.NotificationConstants;

/**
 * La clase abstracta ActorEntity es una {@link ColisionableEntity} que designa a personajes
 * actores, como el jugador y los enemigos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class ActorEntity extends ColisionableEntity {
	
	private int healthpoints = 1;
	private int maxHealthPoints = 1;

	public ActorEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			Engine engine) {
		super(pX, pY, pTiledTextureRegion, engine);
	}

	/**
	 * Realiza las acciones que el actor realiza cuando es herido.
	 * 
	 * @param strengh a integer, la fuerza del ataque recibido.
	 */
	public void hurt(int strengh){
		this.setHealthpoints(this.getHealthpoints() - strengh);
		this.notify(NotificationConstants.CHANGE_HEALTH);
	}
	
	// GETTERS AND SETTERS
	
	public int getHealthpoints() {
		return healthpoints;
	}

	public void setHealthpoints(int healthpoints) {
		this.healthpoints = healthpoints;
	}

	public int getMaxHealthPoints() {
		return maxHealthPoints;
	}

	public void setMaxHealthPoints(int maxHealthPoints) {
		this.maxHealthPoints = maxHealthPoints;
	}

}
