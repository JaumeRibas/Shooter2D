package org.escoladeltreball.shooter2d.entities;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.physics.BodyFactory;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Player es una GameEntity que controlara el jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Player extends ActorEntity {

	/**
	 * Constructor del Player.
	 * 
	 * @param pX
	 *            an integer posicion horizontal del Player
	 * @param pY
	 *            an integer posicion vertical del Player
	 * @param pTiledTextureRegion
	 *            a ITiledTextureRegion
	 * @param pVertexBufferObjectManager
	 *            a VertexBufferObjectManager
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		Body body = BodyFactory.createHumanBody(pX, pY);
		this.setBody(body);

		 this.setMaxHealthPoints(HPConstants.HUMAN_HEALTH);
		 this.setHealthpoints(HPConstants.HUMAN_HEALTH);
	}
	
	@Override
	public void hurt(int strengh){
		super.hurt(strengh);
		System.out.println("PLAYER HEALTH: " + getHealthpoints() + "/" + getMaxHealthPoints());
	}
}