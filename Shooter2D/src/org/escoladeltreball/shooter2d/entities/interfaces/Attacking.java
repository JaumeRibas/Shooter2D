package org.escoladeltreball.shooter2d.entities.interfaces;


public interface Attacking {

	public abstract void attack();

	public abstract void manageCooldown(float pSecondsElapsed);

	public abstract void resetCooldown();

}
