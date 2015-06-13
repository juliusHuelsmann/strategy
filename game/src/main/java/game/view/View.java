package game.view;

import java.awt.Toolkit;

import game.control.interfaces.Listener;
import game.model.Constants;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class View extends JFrame {

	private Map map;
	
	public View(Listener _mml) {
		super("main window");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		super.setLocationRelativeTo(null);
		super.setLayout(null);
		
		map = new Map(_mml);
		super.add(map);
		
		
		
		super.setVisible(true);
		super.setResizable(true);
		
	}
	
	public void validate() {
		super.validate();
	}
	
	public void setSize(int _width, int _height) {
		super.setSize(_width, _height);
		
	}

	/**
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(Map map) {
		this.map = map;
	}
}
