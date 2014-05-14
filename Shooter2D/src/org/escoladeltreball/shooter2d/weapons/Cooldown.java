package org.escoladeltreball.shooter2d.weapons;

public class Cooldown {
	
	private long cooldownNanoseconds;
	private long nextReadyMoment;
	
	public Cooldown(float cooldownSeconds, boolean ready) {
		this.cooldownNanoseconds = (long) (cooldownSeconds * 1000000000);
		if (ready) {
			this.nextReadyMoment = System.nanoTime();
		} else {
			restart();
		}
	}
	
	public Cooldown(float cooldownSeconds) {
		this(cooldownSeconds, false);
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
