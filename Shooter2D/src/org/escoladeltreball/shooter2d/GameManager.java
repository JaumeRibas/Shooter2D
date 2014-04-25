package org.escoladeltreball.shooter2d;

/**
 * La clase GameManager contiene la información relacionada
 * con el gameplay del juego. Esta clase utiliza el patrón singleton.
 * 
 * @author Carlos Serrano
 * @author Elvis Puertas
 * @author Jaume Ribas
 */
public class GameManager {
	/** instancia unica */
	private static GameManager instance;
	
	private GameManager() {}
	
	/**
	 * Este metodo devuelve la instancia unica de GameManager.
	 * Si no existe, la crea.
	 * 
	 * @return la instancia unica de GameManager
	 */
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
}
