package game;

import java.util.logging.Logger;

import game.control.MapControl;

public final class Start {

	
	private static Logger logger = Logger.getLogger("name");
	
	/**
	 * Empty utility class constructor.
	 */
	private Start() {
		
	}
	
	
	
	public static void main(String[]args){
		new MapControl();
	}



	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}
}
