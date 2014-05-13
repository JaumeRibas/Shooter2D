package org.escoladeltreball.shooter2d.weapons;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;

/**
 * Esta clase se ocupa de administrar las armas.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class WeaponFactory {
	public static Gun getGun(Scene scene, Engine engine) {
		return new Gun(scene, engine);
	}
}
