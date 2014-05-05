package org.escoladeltreball.shooter2d.ui;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class HUDBar extends Rectangle {
	
	private float currentValue;
	private float maxValue;
	private float minValue;

	public HUDBar(float pX, float pY, float pWidth, float pHeight,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, vertexBufferObjectManager);
	}
	
	/**
	 * Cambia el valor de la barra.
	 * 
	 * @param value
	 */
	public void change(float value) {
		this.currentValue += value;
		if (this.currentValue > this.maxValue) {
			this.currentValue = this.maxValue;
		} else if (this.currentValue < this.minValue) {
			this.currentValue = 0;
		}
	}
	
	public float getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(float currentValue) {
		this.currentValue = currentValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}
}
