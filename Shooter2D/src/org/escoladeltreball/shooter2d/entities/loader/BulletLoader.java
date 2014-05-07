package org.escoladeltreball.shooter2d.entities.loader;

import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.ResourceManager;
import org.escoladeltreball.shooter2d.entities.Bullet;

import android.content.res.AssetManager;

public class BulletLoader {

	/**
	 * La clase PlayerLoader se encarga cargar los recursos del jugador y del propio jugador.
	 * 
	 * @author Carlos Serrano
	 * @author Elvis Puertas
	 * @author Jaume Ribas
	 */
	public static Bullet loadBullet(Camera camera, int x, int y, TextureManager textureManger,
			AssetManager assets,
			VertexBufferObjectManager vertexBufferObjectManager, float angle, int strengh) {
		
			Bullet bullet = null;
		
			TiledTextureRegion pTiledTextureRegion;
			pTiledTextureRegion = ResourceManager.getInstance().bulletTextureRegion;

			bullet = new Bullet(x, y, pTiledTextureRegion, vertexBufferObjectManager, angle, strengh);
		return bullet;


	}
}