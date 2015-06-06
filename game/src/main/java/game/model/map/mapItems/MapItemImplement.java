package game.model.map.mapItems;

import java.awt.Rectangle;
import game.model.Constants;
import game.model.map.MapItem;
import game.model.player.Person;
import game.model.util.Util;

public class MapItemImplement extends MapItem {

	
	public static int[] rgb = Util.resize("haus.png", 
			3 * Constants.displaySize,
			3  * Constants.displaySize).getRGB(0, 0, 3 * Constants.displaySize,
					3 * Constants.displaySize, null, 0, 
					3 * Constants.displaySize);

	
	public MapItemImplement(int _x, int _y, Person _owner) {
		super(new Rectangle(_x, _y, 3, 3), _owner, rgb);
		
		
		
		
	}
	

	public int getVisibiltyRange() {
		return 5;
	}
}
