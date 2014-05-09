package org.escoladeltreball.shooter2d.ui;


public interface GameObserver {

	/**
	 * Usado para notificar eventos al {@link GameObserver}
	 * 
	 * @param notifier
	 * @param data
	 * @return 
	 */
	public abstract void notify(Object notifier, Object data);
}
