package org.escoladeltreball.shooter2d.entities.loader;

import java.io.IOException;

import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.AssetBitmapTexture;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.res.AssetManager;

public class EntityLoader {
	private AssetBitmapTexture mGameEntityTexture;
	private TiledTextureRegion mGameEntityTextureRegion;

	public TiledTextureRegion loadResources(TextureManager textureManger, AssetManager assets, String spritePath, int spriteCols, int spriteRows) throws IOException{
		this.mGameEntityTexture = new AssetBitmapTexture(textureManger, assets, spritePath);
		this.mGameEntityTextureRegion = TextureRegionFactory.extractTiledFromTexture(this.mGameEntityTexture, spriteCols, spriteRows);
		this.mGameEntityTexture.load();
		return mGameEntityTextureRegion;
	}
}
