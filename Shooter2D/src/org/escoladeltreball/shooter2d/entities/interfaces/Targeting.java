package org.escoladeltreball.shooter2d.entities.interfaces;

import org.escoladeltreball.shooter2d.entities.GameEntity;

/**
 * La interficie Targeting establece los metodos que se usarán para el control
 * del target de la entidad.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public interface Targeting {
	
	public abstract GameEntity getTarget();
	
	public abstract void setTarget(GameEntity gameEntity);

}