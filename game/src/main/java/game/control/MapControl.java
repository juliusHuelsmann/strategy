package game.control;

import game.control.interfaces.Listener;
import game.model.Constants;
import game.model.Game;
import game.model.map.Status;
import game.model.map.mapItems.MapItemImplement;
import game.view.View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Random;


public class MapControl extends Listener{

	
	
	
	
	private View view;
	private Game game;
	
	public MapControl() {
		

		view = new View(this);
		final int mapSize = Constants.amountMIDisplayedGuiHeight;
		game = new Game(mapSize, mapSize, view.getMap());
		
		double time0 = System.currentTimeMillis();

		final int maxZufall = 1 *mapSize * mapSize / 3;
		System.out.println("Total:\t" + maxZufall);
		for (int currentZufall = 0; currentZufall < maxZufall; currentZufall++) {
			int randX = new Random().nextInt(mapSize);
				int randY = new Random().nextInt(mapSize);
				game.getStatus().insertNew(
						new MapItemImplement(randX, randY, game.getPlayer()));
				
		}
		
		
		
		System.out.println(
				":\ta1:\t" + Status.a1 
				+ "\n:\ta42:\t " +  Status.a42 
				+ "\n:\ta23:\t " +  Status.a23 
				+ "\n:\ta41:\t " +  Status.a41);

		double time1 = System.currentTimeMillis();
//		game.getStatus().refreshGui(
//				view.getMap().getLocation().x,
//				view.getMap().getLocation().y,
//				view.getMap().getWidth(),
//				view.getMap().getHeight());
		

		double time2 = System.currentTimeMillis();
		System.out.println("insert\t" + (time1 - time0));
		System.out.println("paint\t" + (time2 - time1));
		game.start();
		
//		new DisplayBI().display(bi_horiz, "horizontal1");		

//		BufferedImage bi_vert2 = status.draw(true, Color.blue.getRGB());
//		BufferedImage bi_horiz2 = status.draw(false, Color.green.getRGB());
//		
//		new DisplayBI().display(bi_vert2, "vertical2");
//		new DisplayBI().display(bi_horiz2, "horizontal2");
		
	}
	
	

	
	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(final MouseEvent _event) {
		
		
		if (_event.getSource() instanceof Component) {

			Component c = (Component) _event.getSource();
			int x = (int) _event.getPoint().getX();
			int y = (int) _event.getPoint().getY();
			final int pageBorder = 10;
			
			if (x < pageBorder || y < pageBorder 
					|| c.getWidth() - x < pageBorder 
					|| c.getHeight() - y < pageBorder) {
				
				System.out.println("move");
			} else {

				
				test(_event);
			}
		}
	}

	public void mouseClicked(final MouseEvent _event) {
		insert(_event);
	}

	public void mouseEntered(MouseEvent arg0) {
	    Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	    view.setCursor(c);
	}

	public void mouseExited(MouseEvent arg0) {

	    Cursor c = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	    view.setCursor(c);
	}

	public void mousePressed(MouseEvent arg0) {
		
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	private void test(final MouseEvent _event) {


		int x = (int) (_event.getX() / Constants.displaySize);
		int y = (int) (_event.getY() / Constants.displaySize);
		
		
		MapItemImplement mii = new MapItemImplement(
				x, y, game.getGaia());
		Color clr;
		if (game.getStatus().checkInsertNew(mii)){
			clr = new Color(190, 240, 150, 150);
		} else {

			clr = new Color(240, 190, 150, 150);
		}

		int width = mii.getWidth() * Constants.displaySize;
		int height = mii.getHeight() * Constants.displaySize;
		
		view.getMap().setHighlight(
				x* Constants.displaySize, 
				y* Constants.displaySize, width, height,
				clr);
	
	}
	
	
	
	private void insert(final MouseEvent _event) {

		
		int x = (int) (_event.getX() / Constants.displaySize);
		int y = (int) (_event.getY() / Constants.displaySize);
		
		
		MapItemImplement mii = new MapItemImplement(
				x, y, game.getPlayer());
		game.getStatus().insertNew(mii);
		
		game.getStatus().refreshGui(
				view.getMap().getLocation().x,
				view.getMap().getLocation().y,
				view.getMap().getWidth(),
				view.getMap().getHeight(),
				game.getPlayer());
	
	}
}
