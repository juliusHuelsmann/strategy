package game.model.map;


public abstract class MapItemGeneral {

	private int width, height;
	public MapItemGeneral(final int _width, final int _height) {
		
		this.width = _width;
		this.height = _height;
	}

	public abstract void setSuccessorV(MapItemGeneral _mi);
	public abstract void setSuccessorH(MapItemGeneral _mi);
	public abstract void setPredecessorV(MapItemGeneral _mi);
	public abstract void setPredecessorH(MapItemGeneral _mi);

	public abstract MapItemGeneral getSuccessorV();
	public abstract MapItemGeneral getSuccessorH();
	public abstract MapItemGeneral getPredecessorV();
	public abstract MapItemGeneral getPredecessorH();
	
	
	
	public abstract int getVisibiltyRange();
	public abstract int getX();
	public abstract int getY();

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}


}
