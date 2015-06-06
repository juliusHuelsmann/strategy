package game.view;

import game.control.interfaces.Listener;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

@SuppressWarnings("serial")
public class Map extends JPanel implements Observer {

	

	private JPanel jpnl_map;
	/**
	 * The JLabel which contains the BufferedImage that is displayed.
	 */
	private JLabel jlbl_background;
	
	
	/**
	 * JLabel for highlighting something for debugging purpose.
	 */
	private JLabel jlbl_highlight;
	
	private JScrollPane sp;
	/**
	 * Constructor: initializes the Graphical user Interface components.
	 */
	public Map(Listener _mml) {
		
		//initialize JFrame and alter settings.
		super( );
		super.setLayout(null);

		jpnl_map = new JPanel();
		jpnl_map.setBackground(new Color(190, 240, 150));
		jpnl_map.setOpaque(true);
		jpnl_map.setSize(getSize());
		jpnl_map.setLayout(null);
		
		jlbl_highlight = new JLabel();
		jlbl_highlight.setOpaque(true);;
		jlbl_highlight.setLocation(0, 0);
		jlbl_highlight.setBorder(new LineBorder(Color.black));
		jlbl_highlight.setBackground(new Color(240, 200, 150, 150));
		jpnl_map.add(jlbl_highlight);
		
		// initialize JLabel and ScrollPane which is inserted to the JFrame.
		jlbl_background = new JLabel();
		jlbl_background.setLocation(0, 0);
		jlbl_background.addMouseMotionListener(_mml);
		jlbl_background.addMouseListener(_mml);
		jlbl_background.setSize(getSize());
		jlbl_background.setBorder(new LineBorder(Color.black));
		jpnl_map.add(jlbl_background);

		sp = new JScrollPane(jpnl_map);
		super.add(sp);
	}

	
	public void setHighlight(
			int _x, int _y, int _width, int _height,
			Color _clr_back) {
		jlbl_highlight.setBounds(_x, _y, _width, _height);
		jlbl_highlight.setBackground(_clr_back);
	}
	
	public void setSize(int _width, int _height) {
		super.setSize(_width, _height);
		sp.setSize(_width, _height);
	}
	
	/**
	 * Displays BufferedImage.
	 * @param _bi		the BufferedImage that is displayed.
	 */
	public final void update(
			final BufferedImage _bi) {
		
		//paint the BufferedImage to the JLabel.
		jlbl_background.setIcon(new ImageIcon(_bi));
		jlbl_background.setSize(_bi.getWidth(), _bi.getHeight());
		jpnl_map.setSize(_bi.getWidth(), _bi.getHeight());
		// set JFrame visible.
		super.setVisible(true);
		repaint();
	}


	public void update(Observable arg0, Object arg1) {

		if (arg1 instanceof BufferedImage) {
			update((BufferedImage) arg1);
		} else {
			System.err.println("error");
		}
		
	}

}
