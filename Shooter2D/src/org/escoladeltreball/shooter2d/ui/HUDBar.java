package org.escoladeltreball.shooter2d.ui;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.color.Color;

public class HUDBar extends Rectangle {
	
	private float currentValue;
	private float maxValue;
	private float valueToPixelRatio;
	
	public static final boolean CROPS_TO_LEFT = false;
	public static final boolean CROPS_TO_RIGHT = true;

	public HUDBar(float pX, float pY, float pWidth, float pHeight, float maxValue, float currentValue, float angle, VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, vertexBufferObjectManager);
		this.setRotation(angle);
		this.setMaxValue(maxValue);
		this.valueToPixelRatio = this.maxValue / pWidth;
		this.setCurrentValue(currentValue);
		this.setColor(Color.GREEN);
	}
	
	/**
	 * Cambia el valor de la barra de forma relativa.
	 * 
	 * @param value un float
	 */
	public void change(float value) {
		this.currentValue += value;
		if (this.currentValue > this.maxValue) {
			this.currentValue = this.maxValue;
		} else if (this.currentValue < 0) {
			this.currentValue = 0;
		}
		this.updateWidth();
	}
	
	/**
	 * Obtiene el valor actual de la barra.
	 * 
	 * @return un float.
	 */
	public float getCurrentValue() {
		return currentValue;
	}

	/**
	 * Cambia el valor de la barra de forma absoluta.
	 * 
	 * @param currentValue un float
	 */
	public void setCurrentValue(float currentValue) {
		if (currentValue >= this.maxValue && currentValue <= this.maxValue)
		this.currentValue = currentValue;
		this.updateWidth();
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
	
	private void updateWidth() {
		this.setWidth(this.valueToPixelRatio * this.getCurrentValue());
	}
}
