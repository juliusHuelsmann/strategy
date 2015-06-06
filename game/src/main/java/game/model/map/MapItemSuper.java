package game.model.map;

import game.Start;


public class MapItemSuper extends MapItemGeneral {

	
	/**
	 * May be used for deciding whether to use the horizontal or the vertical
	 * mapItem.
	 */
	private int linelength = 0;

	
	/**
	 * Position. One of them is -1 because this item is a map super item.
	 */
	private int positionX, positionY;
	
	
	/**
	 * The successor of the super map item.
	 */
	private MapItemGeneral successor, predecessor;

	
	private final boolean left;
	
	/**
	 * Location.
	 * @param _location
	 * @param _left
	 */
	public MapItemSuper(int _location, boolean _left) {
		
		//call super with size 1, 1
		super(1, 1);
		
		predecessor = this;
		successor = this;
		
		
		this.left = _left;
		
		
		if (_left) {
			positionX = _location;
			positionY = -1;
		} else {
			positionY = _location;
			positionX = -1;
			
		}
	}
	
	
	public int getVisibiltyRange() {
		return 0;
	}
	
	
	public void insertAt() {
		
	}


	/**
	 * @return the successor
	 */
	public MapItemGeneral getSuccessor() {
		return successor;
	}

	/**
	 * @return the successor
	 */
	public MapItemGeneral getPredecessor() {
		return predecessor;
	}


	/**
	 * @param successor the successor to set
	 */
	@Override
	public void setSuccessorV(MapItemGeneral successor) {

		if (left) {
			this.successor = successor;
			
		} else {
			Start.getLogger().warning("error");
		}
	}
	/**
	 * @param successor the successor to set
	 */
	@Override
	public void setSuccessorH(MapItemGeneral successor) {
		
		if (!left) {
			this.successor = successor;
			
		} else {
			Start.getLogger().warning("error");
		}
	}
	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public void setPredecessorV(MapItemGeneral predecessor) {

		if (left) {
			this.predecessor = predecessor;
			
		} else {
			Start.getLogger().warning("error");
		}
	}
	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public void setPredecessorH(MapItemGeneral predecessor) {
		
		if (!left) {
			this.predecessor = predecessor;
			
		} else {
			Start.getLogger().warning("error");
		}
	}


	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public MapItemGeneral getPredecessorH() {
		
		if (!left) {
			return this.predecessor;
			
		} else {
			
			Start.getLogger().warning("error");
			return null;
		}
	}

	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public MapItemGeneral getPredecessorV() {
		
		if (left) {
			return this.predecessor;
			
		} else {
			
			Start.getLogger().warning("error");
			return null;
		}
	}
	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public MapItemGeneral getSuccessorH() {
		
		if (!left) {
			return this.successor;
			
		} else {
			
			Start.getLogger().warning("error");
			return null;
		}
	}


	/**
	 * @param predecessor the successor to set
	 */
	@Override
	public MapItemGeneral getSuccessorV() {
		
		if (left) {
			return this.successor;
			
		} else {
			
			Start.getLogger().warning("error");
			return null;
		}
	}

	/**
	 * @return the positionX
	 */
	public int getX() {
		return positionX;
	}



	/**
	 * @return the positionY
	 */
	public int getY() {
		return positionY;
	}




	/**
	 * @return the linelength
	 */
	public int getLinelength() {
		return linelength;
	}





	public void increaseLineLength() {
		linelength++;
	}
	public void decreaseLineLength() {
		linelength--;
	}

}
