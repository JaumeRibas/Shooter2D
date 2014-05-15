package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Bullet;
import org.escoladeltreball.shooter2d.entities.Player;

import android.content.res.AssetManager;

/**
 * La clase Bullet se encarga cargar las balas.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class BulletLoader {
	
	/** El angulo de la sprite de la bala en radianes */
	public static final float BULLET_SPRITE_ANGLE = 0;//-(float) (Math.PI / 2f);

	/**
	 * Crea una nueva bala.
	 * 
	 * @param x a integer, la posición horizontal de la bala
	 * @param y a integer, la posición vertical de la bala
	 * @param assets a {@link AssetManager}
	 * @param vertexBufferObjectManager un {@link VertexBufferObjectManager}
	 * @param angle a float, angulo en grados de la trayectoria de la bala
	 * @param strengh a int, daño que realizará la bala
	 * @return a {@link Player}
	 */	
	public static Bullet loadBullet(float x, float y, float angle, int strengh,	Engine engine) {
		Bullet bullet = null;
		TiledTextureRegion pTiledTextureRegion;
		pTiledTextureRegion = ResourceManager.getInstance().bulletTextureRegion;
		bullet = new Bullet(x, y, pTiledTextureRegion, engine, angle + BULLET_SPRITE_ANGLE, strengh);
		return bullet;
	}
}