package org.escoladeltreball.shooter2d.weapons;

public class Cooldown {
	
	private long cooldownNanoseconds;
	private long nextReadyMoment;
	
	public Cooldown(float cooldownSeconds) {
		this.cooldownNanoseconds = (long) (cooldownSeconds * 1000000000);
		restart();
	}
	
	public synchronized boolean cooldownReady(){
		if (System.nanoTime() >= this.nextReadyMoment) {
			restart();
			return true;
		}
		return false;
	}
	
	public void restart() {
		this.nextReadyMoment = System.nanoTime() + this.cooldownNanoseconds;
	}
}
