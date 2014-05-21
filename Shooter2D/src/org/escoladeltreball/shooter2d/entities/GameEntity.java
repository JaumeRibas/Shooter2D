package org.escoladeltreball.shooter2d.entities;

/*
 * This file is part of shooter2d, a cenital shooter 2D game.
 *
 * Copyright (C) 2014	
 * 						Elvis Puertas <epuertas@gmail.com>
 *						Jaume Ribas <r.ribas.jaume@gmail.com>
 *						Carlos Serrano <arquak@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ui.GameObserver;

/**
 * La clase abstracta GameEntity gestiona la carga gráfica de la entidad y las
 * acciones comunes de todas las entidades, el jugador y los distintos
 * monstruos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class GameEntity extends AnimatedSprite {
	
	private GameObserver[] observers = new GameObserver[2];

	public boolean addGameObserver(GameObserver observer) {
		int i = 0;
		while (i < observers.length && observers[i] != null) {
			i++;
		}
		if (i < observers.length) {
			observers[i] = observer;
			return true;
		}
		return false;
	}
	
	/**
	 * Nofica un evento a su {@link GameObserver}
	 * 
	 * @param data
	 */
	public void notify(Object data) {
		int i = 0;
		while (i < this.observers.length && this.observers[i] != null) {
			this.observers[i].notify(this, data);
			i++;
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
	 * @param another una {@link GameEntity}
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
	 * @param another a {@link GameEntity}
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
	 * @param another a {@link GameEntity}
	 * @return a float.
	 */
	public float getYDistance(GameEntity another){
		float entityY = this.getY();
		float playerY = another.getY();
		float yDistance = playerY - entityY;
		return yDistance;
	}
}
