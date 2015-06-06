package game.model.map;

import game.model.Constants;
import game.model.player.Person;
import game.model.player.Player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class MapItem extends MapItemGeneral {


	//double linked 2d list.
	
	
	private MapItemGeneral p2a_h_pred , p2a_v_pred;
	private MapItemGeneral p2a_h_succ, p2a_v_succ;
	private Person owner;
	private int [] rgbArray;
	
	
	private int x, y;
	
	public MapItem(final Rectangle _bounds, 
			final Person _owner, final int[] _rgbArray) {
		super(_bounds.width, _bounds.height);
		
		this.owner = _owner;
		this.x = _bounds.x;
		this.y = _bounds.y;
		this.rgbArray = _rgbArray;
		
		
		for (int i = 0; i < _rgbArray.length; i++) {

			if (owner instanceof Player) {

				int radius = getVisibiltyRange() / 2;

				/*
				 * add new visibility
				 */
				int visibilityStartX 
				=  Math.max(0, getX() - (radius));
				int visibilityStartY 
				= Math.max(0, getY() - (radius));

				for (int x = visibilityStartX; 
						x < visibilityStartX + radius + getWidth(); x++) {
					for (int y = visibilityStartY;
							y < radius + visibilityStartY + getHeight(); y++) {
						
						int distance = (int) Math.round(
								Math.sqrt( Math.pow(getX() - x, 2)
										+ Math.pow(getY() - y, 2)));
						
						if (distance <= radius) {
							if (x >= 0 && y >= 0 
									&& x < ((Player) owner).getVisibilityCount().length
									&& y < ((Player) owner).getVisibilityCount()[x].length) {

								((Player) owner).getVisibilityCount()[x][y]++;
							}
						}
					}
				}
			}
		}
	}
	
	

	public final void changeLocation(int _dX, int _dY) {
		
		

		if (owner instanceof Player) {
			//
			//                   v
			//			      v  v  v
			//             v  v  v  v  v
			//          v  v  v  X  v  v  v
			//             v  v  v  v  v
			//                v  v  v
			//                   v
			//
			
			
			int radius = getVisibiltyRange() / 2;

			
			/*
			 * Remove old visibility
			 */
			int visibilityStartX 
			=  Math.max(0, getX() - (radius)),
			visibilityStartY 
			= Math.max(0, getY() - (radius));

			for (int x = visibilityStartX; 
					x < visibilityStartX + radius + getWidth(); x++) {
				for (int y = visibilityStartY;
						y < radius + visibilityStartY + getHeight(); y++) {
					
					int distance = (int) Math.round(
							Math.sqrt( Math.pow(getX() - x, 2)
									+ Math.pow(getY() - y, 2)));
					
					if (distance <= radius) {
						((Player) owner).getVisibilityCount()[x][y]--;
					}
				}
			}
		}
		
		
		/*
		 * Update location
		 */
		x += _dX;
		y += _dY;
		

		if (owner instanceof Player) {

			int radius = getVisibiltyRange() / 2;

			/*
			 * add new visibility
			 */
			int visibilityStartX 
			=  Math.max(0, getX() - (radius));
			int visibilityStartY 
			= Math.max(0, getY() - (radius));

			for (int x = visibilityStartX; 
					x < visibilityStartX + radius + getWidth(); x++) {
				for (int y = visibilityStartY;
						y < radius + visibilityStartY + getHeight(); y++) {
					
					int distance = (int) Math.round(
							Math.sqrt( Math.pow(getX() - x, 2)
									+ Math.pow(getY() - y, 2)));
					
					if (distance <= radius) {
						((Player) owner).getVisibilityCount()[x][y]++;
					}
				}
			}
			
			
			// wenn 
		}
		
		
	}
	
	


	/**
	 * @return the p2a_h_pred
	 */
	@Override
	public final MapItemGeneral getPredecessorH() {
		return p2a_h_pred;
	}


	/**
	 * @param p2a_h_pred the p2a_h_pred to set
	 */
	@Override
	public final void setPredecessorH(final MapItemGeneral p2a_h_pred) {
		this.p2a_h_pred = p2a_h_pred;
	}


	/**
	 * @return the p2a_h_succ
	 */
	@Override
	public final MapItemGeneral getSuccessorH() {
		return p2a_h_succ;
	}


	/**
	 * @param p2a_h_succ the p2a_h_succ to set
	 */
	@Override
	public final void setSuccessorH(final MapItemGeneral p2a_h_succ) {
		this.p2a_h_succ = p2a_h_succ;
	}


	/**
	 * @return the p2a_v_pred
	 */
	@Override
	public final MapItemGeneral getPredecessorV() {
		return p2a_v_pred;
	}


	/**
	 * @param p2a_v_pred the p2a_v_pred to set
	 */
	@Override
	public final void setPredecessorV(final MapItemGeneral p2a_v_pred) {
		this.p2a_v_pred = p2a_v_pred;
	}


	/**
	 * @return the p2a_v_succ
	 */
	@Override
	public final MapItemGeneral getSuccessorV() {
		return p2a_v_succ;
	}


	/**
	 * @param p2a_v_succ the p2a_v_succ to set
	 */
	@Override
	public final void setSuccessorV(final MapItemGeneral p2a_v_succ) {
		this.p2a_v_succ = p2a_v_succ;
	}



	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}




	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}



	

	public void step() {

//		final MapItemGeneral _miNorth, final MapItem _miWest,
//		final MapItemGeneral _miEast, final MapItem _miSouth
//		final MapItem miNorth, miEast;
//		if (_miNorth instanceof MapItem) {
//			miNorth = (MapItem) _miNorth;
//		}
//		if (_miEast instanceof MapItem) {
//			miEast = (MapItem) _miEast;
//		}
		
		
		
		
	}


	private boolean isVisibleForPlayer(Player _viewer) {

		
		for (int x = getX(); x < getX() + getWidth(); x++) {

			for (int y = getY(); y < getY() + getHeight(); y++) {
				if (_viewer.getVisibilityCount()[x][y] != 0) {
					return true;
				}
			}	
		}
		
		return false;
	}

	public void paint(int x2, int y2, BufferedImage bi, Player _viewer) {
		
		
		if(isVisibleForPlayer(_viewer)) {
			
			

			int xAtBi = (getX() - x2) * Constants.displaySize;
			int yAtBi = (getY() - y2) * Constants.displaySize;
			
			if (xAtBi > 0 && yAtBi > 0 
					&& xAtBi +  getWidth() * Constants.displaySize < bi.getWidth()
					&& yAtBi +  getHeight() * Constants.displaySize 
					< bi.getHeight()) {

				bi.setRGB(xAtBi, yAtBi, getWidth() * Constants.displaySize,
						getHeight()* Constants.displaySize, rgbArray, 0, 
						getWidth()* Constants.displaySize);
			} else {
				
				for (int x = xAtBi; 
						x < getWidth() * Constants.displaySize; x++) {
					for (int y = yAtBi; 
							y < getHeight() * Constants.displaySize; y++) {

						if (x > 0 && y > 0 
								&& x < bi.getWidth()
								&& y < bi.getHeight()) {
							bi.setRGB(x, y, rgbArray[x + y * getWidth() 
							                         * Constants.displaySize]);
						}
					}
				}
				//TODO: das hier muss auch gezeichnet werden, nur halb.
//				System.out.println("(" + xAtBi + ", " + yAtBi  + ","
//						+ getWidth() * Constants.displaySize + ", "
//						+ getHeight() * Constants.displaySize + ")");
//				System.out.println(bi.getWidth() + "," +  bi.getHeight());
			}

		}
	}



	/**
	 * @return the rgbArray
	 */
	public int [] getRgbArray() {
		return rgbArray;
	}



	/**
	 * @param rgbArray the rgbArray to set
	 */
	public void setRgbArray(int [] rgbArray) {
		this.rgbArray = rgbArray;
	}
}
