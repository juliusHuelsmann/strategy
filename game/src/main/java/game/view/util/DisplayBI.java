//package declaration
package game.view.util;

//import declarations
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;


/**
 * View class for testing some BufferedImages.
 * Displays BufferedImage.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public final class DisplayBI extends JFrame {
	
	
	/**
	 * The JLabel which contains the BufferedImage that is displayed.
	 */
	private JLabel jlbl_background;
	
	/**
	 * Standard size of the JFrame is size x size.
	 */
	private final int size = 700;
	
	
	/**
	 * Constructor: initializes the Graphical user Interface components.
	 */
	public DisplayBI() {
		
		//initialize JFrame and alter settings.
		super("Empty interface for displaysing BufferedImages");
		super.setSize(size, size);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		// initialize JLabel and ScrollPane which is inserted to the JFrame.
		jlbl_background = new JLabel();
		jlbl_background.setSize(getSize());
		JScrollPane sp = new JScrollPane(jlbl_background);
		super.add(sp);
		
		// set JFrame invisible as long as nothing is displayed.
		super.setVisible(false);
	}
	
	
	
	
	/**
	 * Displays BufferedImage and sets title of the JFrame.
	 * @param _bi		the BufferedImage that is displayed.
	 * @param _title	the new JFrame's title.
	 */
	public final void display(
			final BufferedImage _bi, 
			final String _title) {
		
		//set title 
		super.setTitle(_title);
		
		//paint the BufferedImage to the JLabel.
		jlbl_background.setIcon(new ImageIcon(_bi));
		
		// set JFrame visible.
		super.setVisible(true);
	}

}
