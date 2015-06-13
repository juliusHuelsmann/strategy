package game.view.util;

import game.model.Constants;
import game.model.map.Status;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test extends JFrame {

	private final boolean field;
	private JLabel jlbl_1, jlbl_2;
	public Test(final boolean _field) {
		super();
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLayout(null);
		super.setBackground(Color.lightGray);
//		getContentPane().setBackground(Color.lightGray);
		
		this.field = _field;
		jlbl_1 = new JLabel();
		jlbl_1.setVisible(true);
		super.add(jlbl_1);
		
		jlbl_2 = new JLabel();
		jlbl_2.setVisible(true);
		super.add(jlbl_2);
		
		
		
		
		
		
		
		
		
		jlbl_1.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent e) {

				testMapCompareStartField(e.getPoint());						
			}
		});
		jlbl_1.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDragged(MouseEvent e) {

				testMapCompareStartField(e.getPoint());						
			}
		});
		
		
		
		
		
		
		
		
		jlbl_2.addMouseMotionListener(new MouseMotionListener() {
			
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseDragged(MouseEvent e) {

				testMapComparePX(e.getPoint());		
			}
		});
		jlbl_2.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) { }
			
			public void mousePressed(MouseEvent e) { }
			
			public void mouseExited(MouseEvent e) { }
			
			public void mouseEntered(MouseEvent e) { }
			
			public void mouseClicked(MouseEvent e) { 

				testMapComparePX(e.getPoint());		
			}
		});
		
		
		
		
		
		
		
		
		super.setVisible(true);
		super.setResizable(true);
	}
	
	@Override  public void validate() {
		super.validate();
		
		if (jlbl_1 != null && jlbl_2 != null) {

			jlbl_1.setSize(getWidth() / 2, getHeight());
			jlbl_2.setSize(jlbl_1.getSize());
			jlbl_2.setLocation(jlbl_1.getWidth(), 0);
		}
	}
	
	
	
	public void paintPoints(Point _pnt_map1, Point _pnt_map2, 
			Point _pnt_view1, Point _pnt_view2, final int resultSize) {

		BufferedImage bi_model 
		= new BufferedImage(resultSize, resultSize, BufferedImage.TYPE_INT_ARGB), 
		bi_view
		= new BufferedImage(resultSize, resultSize, BufferedImage.TYPE_INT_ARGB);
		
		
		for (int i = 0; i < bi_model.getWidth(); i++) {
			for (int j = 0; j < bi_model.getHeight(); j++) {
				bi_model.setRGB(i, j, Color.white.getRGB());
				bi_view.setRGB(i, j, Color.white.getRGB());
			}
		}
		
		
		//					   y!
		//		             _|_|_           
		//		           _|_|_|_|_         
		//		         _|_|_|_|_|_|_       
		//		       _|_|_|_|_|_|_|_|_     
		//		     _|_|_|_|_|_|_|_|_|_|_   
		//		   _|_|_|_|_|_|_|_|_|_|_|_|_ 
		//		  |s|_|_|_|_|_|_|_|_|_|_|_|_|  <- x
		//		    |_|_|_|_|_|_|_|_|_|_|_|  
		//		      |_|_|_|_|_|_|_|_|_|    
		//		        |_|_|_|_|_|_|_|      
		//		          |_|_|_|_|_|        
		//		            |_|_|_|           
		//		              |_|
		// -> coord (s) = (0, len(y) / 2);

		
		
		// display paintable area

		// in image1
		for (int y = 0; y < resultSize; y++) {
			for (int j = Math.abs(resultSize / 2- y); 
					j < resultSize - Math.abs(resultSize / 2 - y); j++) {

				bi_model.setRGB(j, y,
						Color.gray.getRGB());	
				
			}
		}
		// in image2
		int marge = (int) (resultSize / 2 * (1 - 1 / Math.sqrt(2)));
		for (int x = marge;
				x < resultSize - marge * 2; x++) {

			for (int y = marge;
					y < resultSize - marge * 2; y++) {					

				bi_view.setRGB(x, y,
						Color.gray.getRGB());	
				
			}
		}
		
		// display center
		for (int i = -2; i <= 2; i++) {

			bi_model.setRGB(resultSize / 2 + i, resultSize / 2 + i,
					Color.white.getRGB());	
			bi_model.setRGB(resultSize / 2 + i, resultSize / 2 - i,
					Color.white.getRGB());	
		}
		
		// display circle around center with radius len(_pnt)
		
		if (_pnt_map1 != null) {

			double circleRad = Math.sqrt(
					Math.pow(_pnt_map1.x - resultSize / 2, 2)
					+ Math.pow(_pnt_map1.y - resultSize / 2, 2));
			
			for (double i = 0; i < 2 * Math.PI; i+= 0.01) {
				
				double x = Math.sin(i);
				double y = Math.cos(i);
				
				double len = Math.sqrt(x * x + y * y);
				x = x / len * circleRad + resultSize / 2;
				y = y / len * circleRad + resultSize / 2;

				if (x >= 0 && y >= 0 && x < bi_model.getWidth()
						&& y < bi_model.getHeight()) {

					bi_model.setRGB((int)x,  (int)y, Color.yellow.getRGB());
				}
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		final int paintRad = 3;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// display start point @ bi_model

		
		
		
		
		
		for (int i = -paintRad; i <= paintRad; i++) {
			for (int j = -paintRad; j <= paintRad; j++) {
				

				if (_pnt_map1 != null) {

					int x = _pnt_map1.x + i, 
							y = _pnt_map1.y + j;
					

					if (x >= 0 && y >= 0 && x < bi_model.getWidth()
							&& y < bi_model.getHeight()) {
						bi_model.setRGB(x, y,
								Color.green.getRGB());	
					}

				}
				
				if (_pnt_view1 != null) {

					int x = _pnt_view1.x / Constants.displaySize + i; 
					int y = _pnt_view1.y / Constants.displaySize + j;

					if (x >= 0 && y >= 0 && x < bi_view.getWidth()
							&& y < bi_view.getHeight()) {
						bi_view.setRGB(x, y,
								Color.black.getRGB());	
						bi_model.setRGB(x, y,
								Color.black.getRGB());	
					}
				}
				

				if (_pnt_view2 != null) {

					int x = _pnt_view2.x / Constants.displaySize + i; 
					int y = _pnt_view2.y / Constants.displaySize + j;

					if (x >= 0 && y >= 0 && x < bi_view.getWidth()
							&& y < bi_view.getHeight()) {
						bi_view.setRGB(x, y,
								Color.black.getRGB());	
						bi_model.setRGB(x, y,
								Color.black.getRGB());	
					}
				}
				
				if (_pnt_map2 != null ){

					int x = _pnt_map2.x + i; 
					int y = _pnt_map2.y + j;
					
					if (x >= 0 && y >= 0 && x < bi_model.getWidth()
							&& y < bi_model.getHeight()) {
						bi_model.setRGB(x, y,
								Color.red.getRGB());	
					}
				}
			}
		}
		
		compare(bi_model, bi_view);
	}
	
	
	

	
	public void testMapComparePX(Point pnt_start) {
		
		setTitle("Comparison of shift between model and view map");
		final int size = 200;
		final int resultSize = 2 * size;
		Status stats = new Status(size, null);
		
		Point pnt_result = stats.pxToFld(pnt_start);
		Point pnt_compare = stats.fldToPx(pnt_result);
		
		System.out.println(pnt_start);
		System.out.println(pnt_result);
		System.out.println(pnt_compare);
		System.out.println(
				"Distance in [x]:\t" + (pnt_start.x - pnt_compare.x)
				+"\nDistance in [y]:\t" + (pnt_start.y - pnt_compare.y));

		paintPoints(pnt_result, null, pnt_start, pnt_compare, resultSize);
		
	}
	
	public void testMapCompareStartField(Point pnt_start) {
		
		setTitle("Comparison of shift between model and view map");
		final int size = 200;
		final int resultSize = 2 * size;
		Status stats = new Status(size, null);
		
		Point pnt_result = stats.fldToPx(new Point(pnt_start));
		Point pnt_compare = stats.pxToFld(pnt_result);
		
		System.out.println(pnt_result);
		System.out.println(pnt_compare);
		System.out.println(
				"Distance in [x]:\t" + (pnt_start.x - pnt_compare.x)
				+"\nDistance in [y]:\t" + (pnt_start.y - pnt_compare.y));

		paintPoints(pnt_start, pnt_compare, pnt_result, null, resultSize);
		
	}
	
	public static void main(String[]args) {

		new Test(false).testMapComparePX(new Point(6883, 5769));
//		new Test(true).testMapCompareStartField(new Point(200 ,200));
		// Resultate zeichnen
	}
	
	
	public void compare(BufferedImage _bi_1, BufferedImage _bi_2) {
		jlbl_1.setIcon(new ImageIcon(_bi_1));
		jlbl_2.setIcon(new ImageIcon(_bi_2));
		
		final int width = _bi_1.getWidth() * 2;
		final int height = _bi_1.getHeight();
		if (getWidth() != width || getHeight() != height) {

			super.setSize(width, height);
			super.validate();
		}
	}
}
