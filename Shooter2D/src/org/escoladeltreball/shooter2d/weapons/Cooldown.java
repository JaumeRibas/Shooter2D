package org.escoladeltreball.shooter2d.weapons;

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
