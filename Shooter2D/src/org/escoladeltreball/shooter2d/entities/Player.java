package org.escoladeltreball.shooter2d.entities;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.escoladeltreball.shooter2d.constants.HPConstants;
import org.escoladeltreball.shooter2d.physics.BodyFactory;
import org.escoladeltreball.shooter2d.weapons.Gun;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * La clase Player es una {@link ActorEntity} que controlara el jugador.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class Player extends ActorEntity {

	private Gun gun = null;
	private Bullet bullet;
	
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
	 * @param scene 
	 */
	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, Scene scene) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		Body body = BodyFactory.createHumanBody(pX, pY);
		this.setBody(body);
		this.bullet = null;
		this.setGun(new Gun(1, scene, this, bullet));
		this.setMaxHealthPoints(HPConstants.HUMAN_HEALTH);
		this.setHealthpoints(HPConstants.HUMAN_HEALTH);
	}
	
	public Player(float x, float y, TiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager vertexBufferObjectManager, Scene scene,
			Bullet playerbullet) {
		super(x, y, pTiledTextureRegion, vertexBufferObjectManager);
		Body body = BodyFactory.createHumanBody(x, y);
		this.setBody(body);
		this.bullet = playerbullet;
		this.setGun(new Gun(1, scene, this, playerbullet));
		this.setMaxHealthPoints(HPConstants.HUMAN_HEALTH);
		this.setHealthpoints(HPConstants.HUMAN_HEALTH);
	}

	/**
	 * Realiza las acciones que el jugador realiza cuando es herido.
	 * 
	 * @param strengh un integer, la fuerza del ataque recibido enemigo.
	 */
	@Override
	public void hurt(int strengh){
		super.hurt(strengh);
		// TODO Efectos de sonido del zombie herido
		// TODO Animación y particulas de zombie herido
		System.out.println("PLAYER HEALTH: " + getHealthpoints() + "/" + getMaxHealthPoints());
	}
	
	/**
	 * Realiza las acciones que el jugador colisiona con otras entidades.
	 * 
	 * @param otherBody un body de otra entidad.
	 */
	@Override
	public void collidesWith(Body otherBody) {
		// TODO Auto-generated method stub
		
	}

	public Gun getGun() {
		return gun;
	}

	public void setGun(Gun gun) {
		this.gun = gun;
	}
	
	/**
	 * Actualizamos el cooldown de su arma.
	 * 
	 * @param pSecondsElapsed
	 *            el tiempo pasado entre la actualización anterior y esta.
	 */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		this.gun.getGunCooldown().updateTimer(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
	}
}
