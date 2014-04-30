package org.escoladeltreball.shooter2d.entities.interfaces;

import org.escoladeltreball.shooter2d.entities.GameEntity;

public interface Targeting {
	
	public abstract GameEntity getTarget();
	
	public abstract void setTarget(GameEntity gameEntity);

}
