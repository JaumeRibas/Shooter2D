package org.escoladeltreball.shooter2d.ui;

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

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

public class HUDBar extends Entity {
	
	private static final int BACKGROUND_TAG = 0;
	private static final int BORDER_TAG = 1;
	private float value;
	private float maxValue;
	private float valueToPixelRatio;
	private Rectangle valueRectangle;

	public HUDBar(float pX, float pY, float pWidth, float pHeight, float maxValue, float currentValue, float borderWidth, int barColor, int borderColor, int backgroundColor, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight);
		this.setMaxValue(maxValue);
		this.valueToPixelRatio = pWidth / this.getMaxValue();
		this.value = currentValue;
		Rectangle border = new Rectangle(-borderWidth, -borderWidth, pWidth + borderWidth * 2, pHeight + borderWidth * 2, vertexBufferObjectManager);
		border.setOffsetCenter(0, 0);
		border.setColor(borderColor);
		border.setTag(BORDER_TAG);
		this.attachChild(border);
		Rectangle background = new Rectangle(0, 0, pWidth, pHeight, vertexBufferObjectManager);
		background.setOffsetCenter(0, 0);
		background.setColor(backgroundColor);
		background.setTag(BACKGROUND_TAG);
		this.attachChild(background);
		this.valueRectangle = new Rectangle(0, 0, pWidth, pHeight, vertexBufferObjectManager);
		this.valueRectangle.setOffsetCenter(0, 0); 
		this.valueRectangle.setColor(barColor);
		this.attachChild(this.valueRectangle);
	}

	/**
	 * Cambia el valor de la barra de forma relativa.
	 * 
	 * @param value un float
	 */
	public void change(float value) {
		this.value += value;
		if (this.value > this.maxValue) {
			this.value = this.maxValue;
		} else if (this.value < 0) {
			this.value = 0;
		}
		this.updateWidth();
	}
	
	/**
	 * Obtiene el valor actual de la barra.
	 * 
	 * @return un float.
	 */
	public float getValue() {
		return this.value;
	}

	/**
	 * Cambia el valor de la barra de forma absoluta.
	 * 
	 * @param currentValue un float
	 */
	public void setValue(float currentValue) {
		if (currentValue >= 0 && currentValue <= this.maxValue)
		this.value = currentValue;
		this.updateWidth();
	}
	/**
	 * Cambia el color de la barra en relación al porcentaje de vida que le queda al personaje.
	 */
	public void changeBarColor(){
		float percent = this.value / this.maxValue;
		System.out.println("Life percent:" + percent);
		if(percent >= 0.5){
			// Verde, sano
			this.valueRectangle.setColor(Color.GREEN);
			
		} else if(percent < 0.5 && percent >= 0.2){
			// Amarillo, levemente herido
			this.valueRectangle.setColor(Color.YELLOW);
			
		} else if(percent < 0.2){
			// Naranja, gravemente herido
			this.valueRectangle.setColor(Color.RED);
		}
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	
	private void updateWidth() {
		this.valueRectangle.setWidth(this.valueToPixelRatio * this.getValue());
		this.changeBarColor();
	}
}
