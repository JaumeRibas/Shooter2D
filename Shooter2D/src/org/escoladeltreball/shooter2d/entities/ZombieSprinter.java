package org.escoladeltreball.shooter2d.entities;

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
