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
	
	private static final float HUMAN_BODY_RADIUS = 17;
	private static final float HUMAN_BODY_LINEAR_DAMPING = 1f;
	
	public static final String WALL_USER_DATA = "wall";
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
		Body humanBody = PhysicsFactory.createCircleBody(MainActivity.mPhysicsWorld, positionX, positionY, HUMAN_BODY_RADIUS, BodyType.DynamicBody, FixtureFactory.getFleshFixture());
		humanBody.setLinearDamping(HUMAN_BODY_LINEAR_DAMPING);
		return humanBody;
	}
	
	
	/**
	 * Crea un {@link Body} para las paredes rectangulares.
	 * Este {@link Body} tiene com userData {@link BodyFactory#WALL_USER_DATA}.
	 * 
	 * @param positionX
	 * @param positionY
	 * @param width
	 * @param height
	 * @return el {@link Body}
	 */
	public static Body createRectangleWallBody(float positionX, float positionY, float width, float height) {
		Body wall = PhysicsFactory.createBoxBody(MainActivity.mPhysicsWorld, positionX, positionY, width, height, BodyType.StaticBody, FixtureFactory.getWallFixture());
		wall.setUserData(WALL_USER_DATA);
		return wall;
	}
}
