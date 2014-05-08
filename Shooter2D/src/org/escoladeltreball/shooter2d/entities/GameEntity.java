package org.escoladeltreball.shooter2d.entities;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ui.GameObserver;

/**
 * La clase Abstracta GameEntity gestiona la carga gráfica de la entidad y las
 * acciones comunes de todas las entidades, el jugador y los distintos
 * monstruos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class GameEntity extends AnimatedSprite {
	
	private GameObserver observer;

	public void setGameObserver(GameObserver observer) {
		this.observer = observer;
	}
	
	/**
	 * Nofica un evento a su {@link GameObserver}
	 * 
	 * @param data
	 */
	public void notify(Object data) {
		if (this.observer != null) {
			this.observer.notify(this, data);
		}
	}
	
	public GameEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	/**
	 * Calcula la distancia entre esta entidad y otra.
	 * 
	 * @param another
	 * @return a float.
	 */
	public float getDistance(GameEntity another){
		// Distancia recta, hipotenusa
		float distance = (float) Math.sqrt(Math.pow(getXDistance(another), 2)
				+ Math.pow(getYDistance(another), 2));	
		return distance;
	}
	
	/**
	 * Calcula la distancia horizontal entre esta entidad y otra.
	 * 
	 * @param another a GameEntity
	 * @return a float.
	 */
	public float getXDistance(GameEntity another){
		float entityX = this.getX();
		float playerX = another.getX();
		float xDistance = playerX - entityX;
		return xDistance;
	}
	
	/**
	 * Calcula la distancia vertical entre esta entidad y otra.
	 * 
	 * @param another a GameEntity
	 * @return a float.
	 */
	public float getYDistance(GameEntity another){
		float entityY = this.getY();
		float playerY = another.getY();
		float yDistance = playerY - entityY;
		return yDistance;
	}
}
