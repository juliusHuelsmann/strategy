package game.model.map;

import game.Start;
import game.model.Constants;
import game.model.player.Player;
import game.model.util.adt.list.SecureListSort;
import game.view.util.DisplayBI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;


/**
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public class Status extends Observable {

	
	/*
	 * Time statistics (1000x1000)
	 * igs333333
	 *	:	a1:		66029
	 *	:	a42:	 0
	 *	:	a23:	 120359
	 *	:	a41:	 323
	 *	painted total:				146622
	 *	painted total:				146622
	 *	time insert					4765.0
	 *	time paintEverything		1016.0
	 * 
	 * 
	 * Time statistic of first version of status class without control checking
	 * whether the insertion location is valid.
	 * 
	 * 	painted total:	125000
	 *	painted total:	125000
	 *	to paint:		125000
	 *
	 *	time elapsed [second / 1000] for: 
	 *	 (1)	creation 500 x 500
	 *		 	1.0
	 *	 (2)	filling with 125000
	 *		 	1497.0
	 *	 (3)	drawing vertically
	 *		 	369.0
	 *	 (4)	drawing horizontally
	 *		 	130.0
	 *	 (5)	cross vertically
	 *		 	6.0
	 *	entirely	1.997 Seconds
	 *
	 *
	 * Time statistics for the intuitive implementation as 2dArray with same
	 * size:
	 * 
	 * huge map: creation time 
	 *			implementation as 2dArray:			 11 sec
	 *			without visibility scope:	 11 - 0.031 sec
	 */
	
	
	/**
	 * The Map Items that contain the entire map. Because during the game,<br>
	 * the map is never entirely filled, it is much quicker not to create<br>
	 * a two dimensional array but to create two one dimensional array <br>
	 * serving as the first elements in rows and columns of a double-linked <br>
	 * 2dimensional list.<br><br><br><br>
	 * 
	 *    
	 *  msi_left<br>
	 *    |<br>
	 *    |<br>
	 *    !<br>
	 *   ___________________________________<br>
	 *  |____|____|____|____|____|____| ____|  <--- msi_top<br>
	 *  |____|                ...<br>
	 *  |____|       ___________________<br>
	 *  |____|      |                   |<br>
	 *  |____|      |   double linked   |<br>
	 *  |____|  ... |   2dim. lists     |<br>
	 *  |____|      |                   |<br>
	 *  |____|                ...<br>
	 *  
	 *  <br><br><br>
	 *  
	 *  Thus, the msi_top are lists that look like:<br>
	 *               __________<br>
	 *              |top_master|<br>
	 *              |__________|<br>
	 *                   !<br>
	 *               __________<br>
	 *  (...) <->   |__________|    <-> (...)<br>
	 *		  <br>
	 *   no link         !     in general no link!(not the same x location)<br>
	 *			 	 __________<br>
	 *  (...) <->   |__________|    <-> (...)<br>
	 *		  <br>
	 *                   !<br>
	 *	       <br>
	 *                  null<br>
	 *	  <br>
	 *  The msi_left are lists that look like:<br>
	 *  <br><br>
	 *  
	 *                          (...)      no link  (...) <br>
	 *                          <br>
	 *                            !                   ! <br>
	 *   ___________         ___________         ___________<br>
	 *  |left_master|       |           |       |           |<br>
	 *  |           |  <->  |           |  <->  |           |  ->  null<br>
	 *  |___________|       |___________|       |___________|<br>
	 *  <br>
	 *                            !                   ! <br>
	 *                            <br>
	 *                          (...)      no link  (...) <br>
	 *  
	 *  <br><br>
	 *  Thus, <br>
	 *  	msi_top.length 	== _width,<br>
	 *  	mis_left.length == _height.<br>
	 *  
	 */
	private MapItemSuper[] msi_left, msi_top;

	public static int a42 = 0, a23 = 0, a41 = 0, a1 = 0;

	
	
	/**
	 * Constructor: creates two arrays of the the super-mapItem that serve
	 * as the beginning in x and y coordinate of the two dimensional double
	 * linked list.
	 * 
	 * @see msi_top, msi_left.
	 * 
	 * @param _width		the map's width,
	 * @param _height		the map's height.
	 */
	public Status(int _width, int _height, final Observer _obs) {
		
		// initializes the msi_top and msi_left (for visual information see 
		// its JavaDoc) 
		// @see msi_top, msi_left.
		this.msi_top = new MapItemSuper[_width];
		this.msi_left = new MapItemSuper[_height];

		
		for (int i = 0; i < msi_top.length; i++) {
			msi_top[i] = new MapItemSuper(i, true);
		}
		for (int i = 0; i < msi_left.length; i++) {
			msi_left[i] = new MapItemSuper(i, false);
		}
		
		addObserver(_obs);
	}
	
	
	/**
	 * Insert new MapItem into two dimensional list.
	 * @param _mi		the new MapItem.
	 */
	public void insertNew(final MapItem _mi) {
	
		// if the location of the mapItem is in range continue. Otherwise
		// log an error.
		if (_mi == null) {

			Start.getLogger().warning("error: MapItem is null.");
			
		} else if (_mi.getX() >= 0 && _mi.getX() < msi_top.length
				&& _mi.getY() >= 0 && _mi.getY() < msi_left.length) {

			boolean backwardsTop = _mi.getY() > msi_left.length / 2, 
					backwardsLeft = _mi.getX() > msi_top.length / 2;

			
					
			// get four general MapItems that are horizontally / vertically
			// 	(1) in front of
			// 	(2) behind
			// insert position.
			MapItemGeneral gmi_leftBefore = 
					msi_left[_mi.getY()];
			MapItemGeneral gmi_leftBehind = null;
			if (backwardsLeft) {
				gmi_leftBehind = msi_left[_mi.getY()].getPredecessor();
			} else {

				gmi_leftBehind = msi_left[_mi.getY()].getSuccessor();
			}
			
			MapItemGeneral gmi_topBefore =
					msi_top[_mi.getX()];
			MapItemGeneral gmi_topBehind = null;
			if (backwardsTop) {

				gmi_topBehind = msi_top[_mi.getX()].getPredecessor();
			} else {

				gmi_topBehind = msi_top[_mi.getX()].getSuccessor();
			}
			
			
			
			// find the mapItem that are horizontally in front of 
			// the insert position by crossing the elements of the current
			// item of msi_left
			while(
					// stay inside while loop until the successor GMI-element
					// (gmi_insrtBeforeLeft) is either 
					//		(a) the last element in list or has
					//		(b)	greater x position than the insert element
					gmi_leftBehind instanceof MapItem
					&& (gmi_leftBehind.getX() <= _mi.getX()) != backwardsLeft) {
				MapItem mi = (MapItem) gmi_leftBehind;
				
				gmi_leftBefore = mi;
				if (backwardsLeft) {
					gmi_leftBehind = gmi_leftBefore.getPredecessorH();
				} else {

					gmi_leftBehind = gmi_leftBefore.getSuccessorH();
				}
			}

			// find the mapItem that are vertically in front of 
			// the insert position by crossing the elements of the current
			// item of msi_left
			while(
					(gmi_topBehind instanceof MapItem)
					&& (gmi_topBehind.getY() <= _mi.getY() != backwardsTop) ) {
				MapItem mi = (MapItem) gmi_topBehind;
				gmi_topBefore = mi;
				if (backwardsTop) {
					gmi_topBehind = mi.getPredecessorV();
				} else {
					gmi_topBehind = mi.getSuccessorV();
				}
			}
			

			//check whether the insertion location is okay

			MapItemGeneral gmi_leftbv = gmi_leftBefore;
			if (backwardsLeft) {
				gmi_leftbv = gmi_leftBehind;
			}

			MapItemGeneral gmi_topbv = gmi_topBefore;
			if (backwardsTop) {
				gmi_topbv = gmi_topBehind;
			}
			
			if (checkInsertLocation(_mi, gmi_leftbv, gmi_topbv, false)) {

				
				/*
				 * Set Successor and Predecessor elements for TOP.
				 */
				if (backwardsTop) {

					gmi_topBefore.setPredecessorV(_mi);
					gmi_topBehind.setSuccessorV(_mi);
					_mi.setSuccessorV(gmi_topBefore);
					_mi.setPredecessorV(gmi_topBehind);
				} else {

					gmi_topBefore.setSuccessorV(_mi);
					gmi_topBehind.setPredecessorV(_mi);
					_mi.setPredecessorV(gmi_topBefore);
					_mi.setSuccessorV(gmi_topBehind);
				}

				
				
				/*
				 * Set Successor and Predecessor elements for LEFT.
				 */
				if (backwardsLeft) {

					gmi_leftBefore.setPredecessorH(_mi);
					gmi_leftBehind.setSuccessorH(_mi);
					_mi.setSuccessorH(gmi_leftBefore);
					_mi.setPredecessorH(gmi_leftBehind);
				} else {

					gmi_leftBefore.setSuccessorH(_mi);
					gmi_leftBehind.setPredecessorH(_mi);
					_mi.setPredecessorH(gmi_leftBefore);
					_mi.setSuccessorH(gmi_leftBehind);
				}
				

				// increase the amount of items that are contained by the current
				// row/ column
				msi_left[_mi.getY()].increaseLineLength();
				msi_top [_mi.getX()].increaseLineLength();
			}
			
		} else {
			
			Start.getLogger().warning("error: mapItem out of range:\n\t" 
					+ "(" + _mi.getX()  + ", " + _mi.getY() + ").");
		}
	}
	
	

	/**
	 */
	public boolean checkInsertNew(final MapItem _mi) {
	
		// if the location of the mapItem is in range continue. Otherwise
		// log an error.
		if (_mi == null) {

			Start.getLogger().warning("error: MapItem is null.");

			return false;
		} else if (_mi.getX() >= 0 && _mi.getX() < msi_top.length
				&& _mi.getY() >= 0 && _mi.getY() < msi_left.length) {

			
			// get four general MapItems that are horizontally / vertically
			// 	(1) in front of
			// 	(2) behind
			// insert position.
			MapItemGeneral gmi_leftBefore = 
					msi_left[_mi.getY()];
			MapItemGeneral gmi_leftBehind =
					msi_left[_mi.getY()].getSuccessor();
			
			MapItemGeneral gmi_topBefore =
					msi_top[_mi.getX()];
			MapItemGeneral gmi_topBehind = 
					msi_top[_mi.getX()].getSuccessor();
			
			
			
			// find the mapItem that are horizontally in front of 
			// the insert position by crossing the elements of the current
			// item of msi_left
			while(
					// stay inside while loop until the successor GMI-element
					// (gmi_insrtBeforeLeft) is either 
					//		(a) the last element in list or has
					//		(b)	greater x position than the insert element
					gmi_leftBehind instanceof MapItem
					&& gmi_leftBehind.getX() <= _mi.getX()) {
				gmi_leftBefore = gmi_leftBehind;
				gmi_leftBehind = gmi_leftBehind.getSuccessorH();
			}

			// find the mapItem that are vertically in front of 
			// the insert position by crossing the elements of the current
			// item of msi_left
			while(gmi_topBehind instanceof MapItem
					&& gmi_topBehind.getY() <= _mi.getY()) {
				gmi_topBefore = gmi_topBehind;
				gmi_topBehind = gmi_topBehind.getSuccessorV();
			}
			

			//check whether the insertion location is okay
			return (checkInsertLocation(_mi, gmi_leftBefore, gmi_topBefore, false));
			
		} else {
			
			Start.getLogger().warning("error: mapItem out of range:\n\t" 
					+ "(" + _mi.getX()  + ", " + _mi.getY() + ").");
			return false;
		}
	}
	

	private int [] rgb_empty;
	BufferedImage bi;
	
	/**
	 * 
	 * @param _screenXpx
	 * @param _screenYpx
	 * @param _screenWidthpx
	 * @param _screenHeightpx
	 */
	public void refreshGui(
			final int _screenXpx, 
			final int _screenYpx,
			final int _screenWidthpx, 
			final int _screenHeightpx,
			final Player _viewer) {

//		final int x_bi		= _screenXpx - _screenXpx % Constants.displaySize;
//		final int y_bi		= _screenYpx - _screenYpx % Constants.displaySize;
		final int width_bi	= _screenWidthpx - _screenWidthpx
				% Constants.displaySize;
		final int height_bi	= _screenHeightpx - _screenHeightpx
				% Constants.displaySize;

		final int x 		= _screenXpx / Constants.displaySize;
		final int y 		= _screenYpx / Constants.displaySize;
		final int width		= _screenWidthpx / Constants.displaySize;
		final int height	= _screenHeightpx / Constants.displaySize;
		
		if (bi == null || bi.getWidth() != width_bi 
				|| bi.getHeight() != height_bi) {

			bi = new BufferedImage(width_bi, 
					height_bi, BufferedImage.TYPE_INT_ARGB);
			rgb_empty = new int[width_bi * height_bi];
			int color_white = new Color(255, 255, 255, 0).getRGB();
			for (int i = 0; i < rgb_empty.length; i++) {
				rgb_empty[i] = color_white;
			}
		}
		bi.setRGB(0, 0, width_bi, height_bi, rgb_empty, 0, width_bi);
		SecureListSort<MapItem> sls = goToArea(x, y, width, height);
		sls.toFirst(SecureListSort.ID_NO_PREDECESSOR,
					SecureListSort.ID_NO_PREDECESSOR);
		while(sls != null
				&& !sls.isBehind()
				&& !sls.isEmpty()) {
			sls.getItem().paint(x, y, bi, _viewer );
			sls.next(SecureListSort.ID_NO_PREDECESSOR,
					SecureListSort.ID_NO_PREDECESSOR);
		}
		
		setChanged();
		notifyObservers(bi);
	}
	
	public void testDraw() {

		BufferedImage bi2 = draw(true, null, new Color(20, 40, 250, 50));
		draw(false, bi2, Color.black);
		new DisplayBI().display(bi2, "test");
	}
	
	/**		
	 * Task:		Check whether there is enough space to insert the 
	 * 				currently created item. In other words check
	 * 				whether the insertion of the new MapItem is valid
	 * 				at the specified location
	 * 				
	 * 				Because MapItems have got sizes different from it 
	 * 				may be possible that there are MapItems different
	 * 				from gmi_top/leftBefore that restrict the available
	 * 				location.
	 *
	 * Situation:	The items that are already inserted are valid.
	 * 				Especially gmi_topBefore and gmi_leftBefore are
	 * 				valid.
	 * 
	 * Solution:	The areas A1, A2, A3 and A4 have to be checked:
	 * 					A2, A3	are Areas that only contain
	 * 							one MapItem (gmi_left/topBefore)
	 * 					A1		is an Area that may contain 0...n items
	 * 							in the following rectangle:
	 * 					A4		the area which is used by the new
	 * 							inserted item
	 * 				
	 * 				rectangle for A1:
	 * 					(x, y, width, height) = 
	 * 					(gmi_leftBefore.x,
	 * 					 gmi_topBefore.y,
	 * 					 gmi_topBefore.x - gmi_leftBefore.x,
	 * 					 gmi_leftBefore.y - gmi_topBefore.y);
	 * 
	 * 				rectangle for A4:
	 * 					(x, y, width, height) = 
	 * 					(_gmi_new.x,
	 * 					 _gmi_new.y,
	 * 					 _gmi_new.width,
	 * 					 _gmi_new.height);
	 * 
	 * 				Because gmi_topBefore /gmi_leftBefore are valid,
	 * 				it is impossible that MapItems above/left from
	 * 				those items restrict the available location for
	 * 				the new mapItem.
	 * 
	 * _________________________________________________
	 * |A1                          |   A3 gmi_topBefore|
	 * | 					        |___________________|	
	 * |           hugeItem			|		(...)	
	 * |____________________________|__________________________									
	 * |A2 	gmi_leftBefore  |       | A4  (incl. width, height)|
	 * |                    | (...) | x                 (...)  |gmi_leftBehind
	 * |____________________|       |_________(...)____________|
	 *                                      gmi_topBehind
	 *                        
	 *                        
	 *                        
	 * @param _gmi_new 			the MapItemGeneral which the location is 
	 * 							checked.
	 * 
	 * @param _gmi_leftBefore	the MapItemGeneral which is left before.
	 * 							@see insertNew
	 * 
	 * @param _gmi_topBefore	the MapItemGeneral which is top before.
	 * 							@see insertNew
	 * 	
	 * @param _rect_A1		the rectangle in map that is checked (A1)
	 * 
	 * 
	 * @return 					whether the location of the _gmi_new is valid
	 * 							or not.
	 */
	private boolean checkInsertLocation(
			final MapItemGeneral _gmi_new, 
			final MapItemGeneral _gmi_leftBefore, 
			final MapItemGeneral _gmi_topBefore,
			final boolean _print) {

		
		final Rectangle rect_A1 = new Rectangle(
				Math.max(_gmi_leftBefore.getX(), 0),
				Math.max(_gmi_topBefore.getY(), 0),
				
				Math.max(_gmi_topBefore.getX(), 0) - 
				Math.max(_gmi_leftBefore.getX(), 0)
				+ _gmi_new.getWidth() - 1,
				
				Math.max(_gmi_leftBefore.getY(), 0) - 
				Math.max(_gmi_topBefore.getY(), 0)
				+ _gmi_new.getHeight() - 1);
		
		if((_gmi_new.getX() != _gmi_topBefore.getX())
				|| (_gmi_new.getY() != _gmi_leftBefore.getY())) {

			Start.getLogger().severe("wrong left before or top before:"
					+ (_gmi_new.getX() == _gmi_topBefore.getX()) + "\n"
					+ (_gmi_new.getY() == _gmi_leftBefore.getY()));
			return false;
		}

		if (_gmi_new == null || _gmi_leftBefore == null
				|| _gmi_topBefore == null) {
			Start.getLogger().severe("wrong parameter" 
				+ _gmi_new + ", " + _gmi_leftBefore + ", " + _gmi_topBefore 
				+ ".");
			return false;
		} else if (rect_A1.getX() < 0) {
			rect_A1.x = 0;
		} else if (rect_A1.getY() < 0) {
			rect_A1.y = 0;
		} else if (rect_A1.getX() + rect_A1.getWidth() > msi_top.length
				|| rect_A1.getY() + rect_A1.getHeight() > msi_left.length){

			Start.getLogger().severe("rect_A1 illegal value" + rect_A1
					+ "\n mapsize: (" + msi_left.length 
					+ ", " + msi_top.length + ").");
			return false;
		}

		final Rectangle rect_A4 = new Rectangle(
				_gmi_new.getX(), _gmi_new.getY(),
				_gmi_new.getWidth(), _gmi_new.getHeight());
	
		// check whether the rectangle that is to insert is in range.
		if (rect_A4.getX() < 0 || rect_A4.getY() < 0
					|| rect_A4.getX() + rect_A4.getWidth() > msi_top.length
					|| rect_A4.getY() + rect_A4.getHeight() > msi_left.length){

			if (_print) {
				System.out.println("a41");
			}
			a41++;
			return false;
		 }
		
		
		// check A2 and A3
		if (
				_gmi_leftBefore.getX() + _gmi_leftBefore.getWidth() 
				> _gmi_new.getX()
				|| _gmi_topBefore.getY() + _gmi_topBefore.getHeight() 
				> _gmi_new.getY()) {
			
			if (_print) {

				System.out.println("a23:\t"
						 + _gmi_leftBefore.getX() + ", " 
						 + _gmi_leftBefore.getY() + ", " 
						 + _gmi_leftBefore.getWidth() + ", " 
						 + _gmi_leftBefore.getHeight() 
						 + "\n\t"
						 + _gmi_topBefore.getX() + ", "
						 + _gmi_topBefore.getY() + ", " 
						 + _gmi_topBefore.getWidth() + ", " 
						 + _gmi_topBefore.getHeight() );
				
				System.out.println("gmi_new"
						 + _gmi_new.getX() + ", " + _gmi_new.getY());
			}
			a23++;
			return false;
		}
		
		
		// check A1
		
		// 
		for (int x = rect_A1.x; 
				x <= rect_A1.x + rect_A1.width; x++) {

			MapItemGeneral mi_current = msi_top[x].getSuccessor();
			while(mi_current instanceof MapItem 
					&& mi_current.getY() 
					<= rect_A1.y + rect_A1.height) {
				
			
				if (
						(mi_current.getY() + mi_current.getHeight() 
						> _gmi_new.getY())
					
						&& (mi_current.getX() + mi_current.getWidth() 
						> _gmi_new.getX())) {


					if (_print) {
						System.out.println("a1");
					}
					a1++;
					return false;
				}
				mi_current = mi_current.getSuccessorV();
			}
		}
		

		// check A4
		for (int x = rect_A4.x; 
				x < rect_A4.x + rect_A4.width; 
				x++) {

			
			MapItemGeneral mi_current = msi_top[x].getSuccessor();

			
			
			while(
					mi_current instanceof MapItem 
					
					&& mi_current.getY() 
					< rect_A4.y + rect_A4.height) {
				
				if (
						_gmi_new.getY() < mi_current.getY()
						&& _gmi_new.getX() < mi_current.getX()
						&& _gmi_new.getY() + _gmi_new.getHeight() 
						> mi_current.getY()
						&& _gmi_new.getX() + _gmi_new.getWidth() 
						> mi_current.getX()) {
					a42++;

					if (_print) {
						System.out.println("ra4" + rect_A4);
						System.out.println(mi_current.getX() + ".." + mi_current.getY()); 
						System.out.println("a42");
					}
					return false;
				}
				mi_current = mi_current.getSuccessorV();
			}
		}
		
		return true;
	}
	
	
	
	
	/**
	 * 
	 * @param _left
	 * @param _rgb
	 * @return
	 */
	public BufferedImage draw(final boolean _left,
			BufferedImage _bi, final Color _rgb) {
		
		int sizeItem = Constants.displaySize;
		int width = msi_top.length * sizeItem;
		int height = msi_left.length * sizeItem;
		int sum = 0;
		
		BufferedImage bi_image;
		if (_bi == null) {

			bi_image = new BufferedImage(width, height, 
					BufferedImage.TYPE_INT_ARGB);


			int rgba = new Color(255, 255, 255, 0).getRGB();
			for (int x = 0; x < bi_image.getWidth(); x++) {
				for (int y = 0; y < bi_image.getHeight(); y++) {
					bi_image.setRGB(x, y, rgba);
				}
			}
		} else if (_bi.getWidth() == width && _bi.getHeight() == height) {
			bi_image = _bi;
		} else {
			bi_image = null;

			System.err.println(getClass() + "error");
			return _bi;
		}
		
		Graphics g = bi_image.getGraphics();
		

		g.setColor(Color.black);

		if (_left) {
			
			for (int i = 0; i < msi_left.length; i++) {
				MapItemGeneral mi_current = msi_left[i].getSuccessor();
				
				
				while(mi_current instanceof MapItem) {
					
					//paint
					int startX = mi_current.getX() * sizeItem;
					int startY = mi_current.getY() * sizeItem;

					for (int j = 1;
							j < sizeItem * mi_current.getWidth() - 1; 
							j++) {
						
						for (int k = 1;
								k < sizeItem * mi_current.getHeight() - 1;
								k++) {

							int newX = startX + j;
							int newY = startY  + k;
							
							if (newX >= 0 && newX < bi_image.getWidth()
									&& newY >= 0 
									&& newY < bi_image.getHeight()) {

								int alpha = new Color(bi_image.getRGB(newX, newY), true).getAlpha();
								Color clr;
								if (Math.min(alpha + _rgb.getAlpha(), 255) > 50) {

									clr = Color.orange;
								} else {

									clr = new Color(_rgb.getRed(),
											_rgb.getGreen(),_rgb.getBlue(),
											Math.min(alpha + _rgb.getAlpha(), 255));
								}
								
								bi_image.setRGB(newX, newY, clr.getRGB());
							}
						}
					}
					mi_current = mi_current.getSuccessorH();
					sum++;
//					g.drawString("" + sum, 
//							startX + sizeItem / 3, 
//							startY + sizeItem / 3);
				}
			}
		} else {

			for (int i = 0; i < msi_top.length; i++) {
				
				MapItemGeneral mi_current = msi_top[i].getSuccessor();
				while(mi_current instanceof MapItem) {
					
					//paint
					int startX = mi_current.getX() * sizeItem;
					int startY = mi_current.getY() * sizeItem;

					for (int j = 0;
							j < sizeItem * mi_current.getWidth() - 1; j++) {
						
						for (int k : new int[]{
								0, 
								sizeItem * mi_current.getHeight() - 1}) {

							int newX = startX + j;
							int newY = startY  + k;
							
							if (newX >= 0 && newX < bi_image.getWidth()
									&& newY >= 0 
									&& newY < bi_image.getHeight()) {

								bi_image.setRGB(newX, newY, _rgb.getRGB());
							}
						}
					}

					for (int k = 0;
							k < sizeItem * mi_current.getHeight() - 1; k++) {
						
						for (int j : new int[]{
								0, 
								sizeItem * mi_current.getWidth() - 1}) {

							int newX = startX + j;
							int newY = startY  + k;
							
							if (newX >= 0 && newX < bi_image.getWidth()
									&& newY >= 0 
									&& newY < bi_image.getHeight()) {

								bi_image.setRGB(newX, newY, _rgb.getRGB());
							}
						}
					}
//					g.drawString("" + sum, 
//							startX + sizeItem / 3, 
//							startY + sizeItem / 3);

					mi_current = mi_current.getSuccessorV();
					sum++;
				}
			}
		}
		
		System.out.println("painted total:\t" + sum);
		return bi_image;
	}
	
	

	/**
	 * 
	 * @param _vertical
	 * @param _rgb
	 * @return
	 */
	public int cross(final boolean _vertical) {
		
		boolean printStuff = false;
		int sum = 0;
		
		if (_vertical) {
			for (int i = 0; i < msi_left.length; i++) {
				if (printStuff) {

					System.out.println("\n\nnew for:" + " i = " + i);
				}
				
				
				MapItemGeneral mi_current = msi_left[i].getSuccessor();
				while(mi_current instanceof MapItem) {

					if (printStuff) {
						System.out.println(mi_current.getY());
					}
					mi_current = mi_current.getSuccessorH();
					sum++;
				}
			}
		} else {

			for (int i = 0; i < msi_top.length; i++) {
				if (printStuff) {

					System.out.println("\n\nnew for:" + " i = " + i);
				}
				
				
				MapItemGeneral mi_current = msi_top[i].getSuccessor();
				while(mi_current instanceof MapItem) {
					if (printStuff) {
						System.out.println(mi_current.getX());
					}
					
					mi_current = mi_current.getSuccessorV();
					sum++;
				}
			}
		}
		return sum;
	}

	
	
	public SecureListSort<MapItem> goToArea(
			int _x, int _y, int _width, int _height) {

		final SecureListSort<MapItem> sls_paint
		= new SecureListSort<MapItem>();
		boolean backwardsTop = _y > msi_left.length / 2, 
				backwardsLeft = _x > msi_top.length / 2;
				backwardsLeft = false;
						backwardsTop = false;
		
		if (_x + _width < _y + _height) {
			for (int i = _y; i < _y + _height; i++) {

				
				MapItemGeneral mi_current;
				if (!backwardsLeft) {
					mi_current = msi_left[i].getSuccessor();
				} else {
					mi_current  = msi_left[i].getPredecessor();
				}


				while (mi_current instanceof MapItem 
						&& mi_current.getX() <= _x + _width != backwardsLeft) {

					if ( mi_current.getX() + mi_current.getWidth() > _x) {
						sls_paint.insertSorted(
								(MapItem) mi_current, 
								mi_current.getY(), 
								SecureListSort.ID_NO_PREDECESSOR);
					}
					
					if (!backwardsLeft) {
						mi_current = mi_current.getSuccessorH();
					} else {
						mi_current  = mi_current.getPredecessorH();
					}

//					counter++;
				}
			}
//			System.out.println("items" + counter);
//			System.out.println("percentage: "
//			+  counter * 100 / _height / (_x + _width));
		} else {

			for (int i = _x; i < _x + _width; i++) {
				
				MapItemGeneral mi_current;
				
				if (!backwardsTop) {
					mi_current = msi_top[i].getSuccessor();
				} else {
					mi_current  = msi_top[i].getPredecessor();
				}

				if (mi_current instanceof MapItem) {

					while(mi_current instanceof MapItem
							&& mi_current.getY() <= _y + _width!= backwardsTop) {

						if (mi_current.getY() + mi_current.getHeight() > _y) {
							sls_paint.insertSorted(
									(MapItem) mi_current, 
									mi_current.getY(), 
									SecureListSort.ID_NO_PREDECESSOR);
						}

						if (!backwardsTop) {
							mi_current = mi_current.getSuccessorV();
						} else {
							mi_current  = mi_current.getPredecessorV();
						}

//						counter++;
					}
				}
			}
			
//			System.out.println("items" + counter);
//			System.out.println("percentage: "
//			+  counter * 100 / _width / (_y + _height));
		}
		return sls_paint;
	}

	public void step() {

		for (int i = 0; i < msi_left.length; i++) {
			
			MapItemGeneral mi_current = msi_left[i].getSuccessor();
			while(mi_current instanceof MapItem) {
				((MapItem) mi_current).step();
				mi_current = mi_current.getSuccessorH();
			}
		}
	
	}
}
