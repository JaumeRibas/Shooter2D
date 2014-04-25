package org.escoladeltreball.shooter2d.entities;

import java.io.IOException;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.vbo.ITiledSpriteVertexBufferObject;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.res.AssetManager;

/**
 * La clase Abstracta GameEntity gestiona la carga gráfica de la entidad y las acciones
 * comunes de todas las entidades, el jugador y los distintos monstruos.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */

public abstract class GameEntity extends AnimatedSprite {
	
	private AssetBitmapTexture mGameEntityTexture;
	
	public GameEntity(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}
	
	
	
	public void loadResources(TextureManager textureManger, AssetManager assets, String spritePath, int spriteCols, int spriteRows) throws IOException{
		this.mGameEntityTexture = new AssetBitmapTexture(textureManger, assets, spritePath);
		TextureRegionFactory.extractTiledFromTexture(this.mGameEntityTexture, spriteCols, spriteRows);
		this.mGameEntityTexture.load();
	}
}
