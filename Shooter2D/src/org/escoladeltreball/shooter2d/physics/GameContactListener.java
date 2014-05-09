package org.escoladeltreball.shooter2d.physics;

import org.escoladeltreball.shooter2d.entities.ColisionableEntity;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameContactListener implements ContactListener {

	private static GameContactListener instance;

	@Override
	public void beginContact(Contact contact) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		if (bodyA.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyA.getUserData()).beginsContactWith(bodyB);
		if (bodyB.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyB.getUserData()).beginsContactWith(bodyA);
	}

	@Override
	public void endContact(Contact contact) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		if (bodyA.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyA.getUserData()).endsContactWith(bodyB);
		if (bodyB.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyB.getUserData()).endsContactWith(bodyA);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		if (bodyA.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyA.getUserData()).isContactingWith(bodyB);
		if (bodyB.getUserData() instanceof ColisionableEntity) 
			((ColisionableEntity)bodyB.getUserData()).isContactingWith(bodyA);
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

	/**
	 * Devuevle al instancia unica del {@link GameContactListener}
	 * 
	 * @return
	 */
	public static ContactListener getInstance() {
		if (instance == null) {
			instance = new GameContactListener();
		}
		return instance;
	}

	/**
	 * Determina si a un {@link Body}s lo estan tocando
	 * en un {@link Contact} determinado.
	 * 
	 * @param pBody
	 * @param pContact
	 * @return true si lo estan tocando o false en caso contrario
	 */
	public boolean isBodyContacted(Body pBody, Contact pContact) {
		if (pContact.getFixtureA().getBody().equals(pBody)
				|| pContact.getFixtureB().getBody().equals(pBody))
			return true;
		return false;
	}

	/**
	 * Determina si dos {@link Body}s se estan tocando
	 * en un {@link Contact} determinado.
	 * 
	 * @param pBody1
	 * @param pBody2
	 * @param pContact
	 * @return true si se estan tocando o false en caso contrario
	 */
	public boolean areBodiesContacted(Body pBody1, Body pBody2, Contact pContact) {
		if (pContact.getFixtureA().getBody().equals(pBody1)
				|| pContact.getFixtureB().getBody().equals(pBody1))
			if (pContact.getFixtureA().getBody().equals(pBody2)
					|| pContact.getFixtureB().getBody().equals(pBody2))
				return true;
		return false;
	}
}
