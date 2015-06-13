package game.model;

import java.util.Observer;

import game.model.map.Status;
import game.model.player.Gaia;
import game.model.player.Player;


/**
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class Game extends Thread {

	private Player player;
	private Gaia gaia;
	private Status status;
	
	public Game(int _x_ffld, int _height, Observer _obs_map) {
		gaia = new Gaia();
		player = new Player(_x_ffld, _height);
		status = new Status(_x_ffld, _obs_map);
	}
	
	public void run() {
		long gameSecond = 0;
		
//		status.testDraw();
		double time0 = System.currentTimeMillis();
		while(!isInterrupted()) {
			status.step();
			player.step();
			
			
			status.refreshGui(0, 0, 
						Constants.amountMIDisplayedGuiWidth
						* Constants.displaySize, 
						Constants.amountMIDisplayedGuiHeight
						* Constants.displaySize, player);
//				status.goToArea(1000 - amountXAtScreen, 
//						1000 - amountYAtScreen,
//						amountXAtScreen, amountYAtScreen);

			
			// 12 / 1000 seconds for 1000 steps
			// 0.012 	 s 	     for 1000 steps
			// 0.000012	 s		 for    1 step

			gameSecond++;
			System.out.println("steps:\t" + gameSecond);
			System.out.println("time: " 
					+ (System.currentTimeMillis() - time0) / gameSecond);
			try {
				Thread.sleep(Constants.sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return the gaia
	 */
	public Gaia getGaia() {
		return gaia;
	}

	/**
	 * @param gaia the gaia to set
	 */
	public void setGaia(Gaia gaia) {
		this.gaia = gaia;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
