package org.escoladeltreball.shooter2d.physics;

import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.escoladeltreball.shooter2d.MainActivity;
import org.escoladeltreball.shooter2d.entities.ColisionableEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Contruye los {@link Body}s para las diferentes entidades
 * que extienden de {@link ColisionableEntity}.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class BodyFactory {
	
	private static final float HUMAN_BODY_RADIUS = 0;

	/**
	 * Crea un {@link Body} para el {@link Player},
	 * los {@link Zombie}s y otras {@link ColisionableEntity}s
	 * que tengan un cuerpo humano.
	 * 
	 * @param positionX
	 * @param positionY
	 * @return un {@link Body}
	 */
	public static Body createHumanBody(float positionX, float positionY) {
		return PhysicsFactory.createCircleBody(MainActivity.mPhysicsWorld, positionX, positionY, HUMAN_BODY_RADIUS, BodyType.DynamicBody, FixtureFactory.getFleshFixture());
	}
	
//	public static Body createWallBody(float positionX, float positionY, Entity entity) {
//		return PhysicsFactory.createBoxBody(MainActivity.mPhysicsWorld, positionX, positionY, entity, BodyType.StaticBody, FixtureFactory.getWallFixture());
//	}
}
