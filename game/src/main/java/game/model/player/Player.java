package game.model.player;



public class Player extends Person {

	/**
	 * Contains the amount of own MapItems which "see" the specified field.
	 * If equal to 0, the field is invisible.
	 */
	private byte[][] visibilityCount;
	
	
	public Player(final int _width, final int _height) {
		
		visibilityCount = new byte[_width][_height];
		for (int x = 0; x < visibilityCount.length; x++) {
			for (int y = 0; y < visibilityCount[x].length; y++) {
				visibilityCount[x][y] = 0;
			}
		}
//		ls_posessions = new SecureList<MapItem>();
	}

	public void step() {
		
//		/*
//		 * Start transaction and closed action.
//		 */
//		final int transactionID 
//		= ls_posessions.startTransaction("step", SecureList.ID_NO_PREDECESSOR);
//		final int closedActionID 
//		= ls_posessions.startClosedAction("step", SecureList.ID_NO_PREDECESSOR);
//		
//		
//		while(!ls_posessions.isEmpty() && !ls_posessions.isBehind()) {
//			ls_posessions.next(transactionID, closedActionID);
//			
//		}
//		
//
//		ls_posessions.finishClosedAction(closedActionID);
//		ls_posessions.finishTransaction(transactionID);
	}

	/**
	 * @return the visibilityCount
	 */
	public byte[][] getVisibilityCount() {
		return visibilityCount;
	}
	
}
