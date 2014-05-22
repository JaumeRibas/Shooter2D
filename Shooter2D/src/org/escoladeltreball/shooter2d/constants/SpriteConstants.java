package org.escoladeltreball.shooter2d.constants;

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

/**
 * La clase Abstracta SpriteConstants es una biblioteca de rutas de Sprites.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public final class SpriteConstants {
	
	public static final String SPRITE_FOLDER = "gfx/";
	
	public static final String PLAYER_SPRITE = SPRITE_FOLDER + "player.png";
	public static final int PLAYER_SPRITE_COLUMNS = 2;
	public static final int PLAYER_SPRITE_ROWS = 4;

	public static final String ZOMBIE_SPRITE = SPRITE_FOLDER + "character.png";
	public static final int ZOMBIE_SPRITE_COLUMNS = 6;
	public static final int ZOMBIE_SPRITE_ROWS = 1;
	
	public static final String CONTROL_KNOB_SPRITE = SPRITE_FOLDER + "onscreen_control_knob.png";
	public static final String CONTROL_BASE_SPRITE = SPRITE_FOLDER + "onscreen_control_base.png";

	public static final String SPLASH_IMAGE = SPRITE_FOLDER + "splash.png";
	public static final String BULLET_SPRITE = SPRITE_FOLDER + "bullet.png";
	public static final int BULLET_SPRITE_COLUMNS = 2;
	public static final int BULLET_SPRITE_ROWS = 1;

}
