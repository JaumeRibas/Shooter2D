package org.escoladeltreball.shooter2d.weapons;

public class Cooldown {
	
	private float cooldown_time;
	private float timer;
	
	public Cooldown(float cooldown_time) {
		super();
		this.cooldown_time = cooldown_time;
		this.timer = cooldown_time;
	}
	
	public void updateTimer(float pSecondsElapsed){
		if(this.timer > 0){
			this.timer -= pSecondsElapsed;
		}
		if(this.timer < 0){
			this.timer = 0;
		}
	}
	
	public boolean cooldownReady(){
		boolean ready = this.timer == 0;
		if(ready){
			this.timer = cooldown_time;
		}
		return ready;
	}
}
