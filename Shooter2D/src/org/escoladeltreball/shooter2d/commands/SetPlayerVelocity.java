package org.escoladeltreball.shooter2d.commands;

import org.andengine.util.math.MathUtils;
import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocity implements AnalogChangeCommand {

	private Player player;
	private Body playerBody;
	public int SPEED = 10;
	private boolean isWalking = false;
	
	public SetPlayerVelocity(Player player, Body playerBody) {
		this.player = player;
		this.playerBody = playerBody;
	}

	@Override
	public void execute(float pValueX, float pValueY) {
		//cambiar la velocidad del jugador
		//cambiar este codigo
		if(pValueX != 0 && pValueY != 0){
			if(!this.isWalking){
				this.player.animate(200);
				this.isWalking = true;
			}			
			this.playerBody.setTransform(this.playerBody.getWorldCenter(), MathUtils.radToDeg((float)Math.atan2(pValueX, pValueY)));
			//this.playerBody.setLinearVelocity(this.playerBody.getWorldCenter().pValueX * 2, pValueY * 2);
		} else {
			this.player.stopAnimation();
			this.player.setCurrentTileIndex(0);
			this.isWalking = false;
		}
	}

}
