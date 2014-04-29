package org.escoladeltreball.shooter2d.commands;

import org.andengine.util.math.MathUtils;
import org.escoladeltreball.shooter2d.commands.interfaces.AnalogChangeCommand;
import org.escoladeltreball.shooter2d.entities.Player;

/**
 * Cambia la velocidad dirección y sentido del movimiento del jugador
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class SetPlayerVelocity implements AnalogChangeCommand {

	private Player player;
	public int SPEED = 10;
	private boolean isWalking = false;
	
	public SetPlayerVelocity(Player player) {
		this.player = player;
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
			this.player.setRotation(MathUtils.radToDeg((float)Math.atan2(pValueX, pValueY)));
			this.player.setPosition(this.player.getX() + pValueX * 10, this.player.getY() + pValueY  * 10);	
		} else {
			this.player.stopAnimation();
			this.player.setCurrentTileIndex(0);
			this.isWalking = false;
		}
		
	}

}
