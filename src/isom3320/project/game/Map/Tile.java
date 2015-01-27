package isom3320.project.game.Map;

import javafx.scene.image.Image;

public class Tile {
	public static final int NORMALTILE = 0;
	public static final int BLOCKTILE = 1;
	
	private Image image;
	private int type;
	
	public Tile(Image image, int type) {
		this.image = image;
		this.type = type;
	}
	
	public Image getImage() {
		return image;
	}
	
	public int getType() {
		return type;
	}
}
