package org.escoladeltreball.shooter2d.commands;

import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.math.MathUtils;
import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;

import com.badlogic.gdx.math.Vector2;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocityAndOrientation implements AnalogChangeCommand {

	private Player player;
	public float SPEED = 2;
	private boolean isWalking = false;
	
	public SetPlayerVelocityAndOrientation(Player player) {
		this.player = player;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		if(pValueX != 0 && pValueY != 0){
			if(!this.isWalking){
				this.player.animate(200);
				this.isWalking = true;
			}			
			this.player.getBody().setTransform(this.player.getBody().getPosition(), (float)Math.atan2(-pValueX, pValueY));
			//obtine la dirección a la que mira el body jugador suponiendo que al crearlo miraba hacia arriba (0,1)
			Vector2 playerVector = this.player.getBody().getWorldVector(Vector2Pool.obtain(0, 1));
			this.player.getBody().setLinearVelocity(pValueX * SPEED, pValueY * SPEED);
		} else {
			this.player.stopAnimation();
			this.player.setCurrentTileIndex(0);
			this.player.getBody().setLinearVelocity(0, 0);
			this.isWalking = false;
		}
	}

}
