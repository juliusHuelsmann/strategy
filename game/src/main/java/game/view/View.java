package game.view;

import game.control.interfaces.Listener;
import game.model.Constants;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class View extends JFrame {

	private Map map;
	
	public View(Listener _mml) {
		super("main window");
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setSize(Constants.amountMIDisplayedGuiWidth
				* Constants.displaySize, 
				Constants.amountMIDisplayedGuiHeight
				* Constants.displaySize);
		super.setLocationRelativeTo(null);
		super.setLayout(null);
		
		map = new Map(_mml);
		map.setSize(getSize());
		super.add(map);
		
		
		
		super.setVisible(true);
		super.setResizable(true);
		
	}
	
	public void validate() {
		super.validate();
		map.setSize(getWidth(), getHeight());
	}
	
	public void setSize(int _width, int _height) {
		super.setSize(_width, _height);
		map.setSize(_width, _height);
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
