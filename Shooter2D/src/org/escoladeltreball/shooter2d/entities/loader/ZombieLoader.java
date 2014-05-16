package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.Engine;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.ActorEntity;
import org.escoladeltreball.shooter2d.entities.Zombie;

/**
 * La clase ZombieLoader se encarga cargar los recursos del jugador y del propio jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class ZombieLoader {
	
	private ZombieLoader(){};
	
	/**
	 * Devuelve un zombie nuevo.
	 * 
	 * @param x a integer, la posición horizontal del zombie
	 * @param y a integer, la posición vertical del zombie
	 * @param engine a {@link Engine}
	 * @param actorEntity a {@link ActorEntity} a perseguir
	 * @return a {@link Zombie}
	 */

	public static Zombie loadZombie(float x, float y, Engine engine, ActorEntity actorEntity) {
		Zombie zombie = null;
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().zombieTextureRegion;
		zombie = new Zombie(x, y, pTiledTextureRegion, engine, actorEntity);
		return zombie;
	}
}
