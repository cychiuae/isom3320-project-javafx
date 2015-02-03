package isom3320.project.game.Map;

import javafx.scene.image.Image;
/**
 * Class Tile is for defining tile object property.
 * @author kevingok
 */
public class Tile {
	/**Defines tile that can be passed through.*/
	public static final int NORMALTILE = 0;
	/**Defines tile that cannot be passed through.*/
	public static final int BLOCKTILE = 1;
	
	/**Defines image of tile.*/
	private Image image;
	/**Defines type of tile.*/
	private int type;
	
	/**
	 * Class constructor to initiate each tile image and its type.
	 * @param image		image of the tile
	 * @param type		type of the tile
	 */
	public Tile(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	/**
	 * Get image of tile instance.
	 * @return 			image of tile instance
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Get type of tile instance
	 * @return 			type of the tile
	 */
	public int getType() {
		return type;
	}
}
