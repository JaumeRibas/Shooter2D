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

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.color.Color;
import org.escoladeltreball.shooter2d.constants.HPConstants;

import com.badlogic.gdx.physics.box2d.Body;

public class ZombieSprinter extends Zombie {
	
	public static final String RESPAWN_NAME = "charger";

	public ZombieSprinter(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			Engine engine, ActorEntity actorEntity) {
		super(pX, pY, pTiledTextureRegion, engine, actorEntity);
		this.setMaxHealthPoints(HPConstants.ZOMBIE_HEALTH / 2);
		this.setHealthpoints(HPConstants.ZOMBIE_HEALTH / 2);
		super.speed = HPConstants.ZOMBIE_WALK_SPEED * 2f;
		super.vision_radius = (int) (HPConstants.ZOMBIE_SIGHT_RADIUS * 1.3);
		this.setColor(Color.CYAN);
	}
	
	@Override
	public void endsContactWith(Body otherBody) {
		super.endsContactWith(otherBody);
		this.speed = HPConstants.ZOMBIE_WALK_SPEED * 1.5f;
	}
}
