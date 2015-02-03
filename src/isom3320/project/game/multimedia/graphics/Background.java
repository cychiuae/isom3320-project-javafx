package isom3320.project.game.multimedia.graphics;

import isom3320.project.game.Game;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class Background is for drawing background image while main character
 * is moving forward or backward.
 * @author kevingok
 *
 */
public class Background {
	/**Defines image of background*/
	private Image image;
	/**Defines x-coordinate*/
	private double xPosition;
	/**Defines y-coordinate*/
	private double yPosition;
	/**Defines x vector*/
	private double dx;
	/**Defines y vector*/
	private double dy;
	/**Defines moving scale*/
	private double moveScale;
	
	/**
	 * Class constructor to initiate background image and move scale.
	 * @param imageName		filename of background image
	 * @param moveScale		scale of move
	 */
	public Background(String imageName, double moveScale) {
		image = MultimediaHelper.getImageByName(imageName);
		this.moveScale = moveScale;
	}
	
	/**
	 * Set the position of the background image
	 * @param x		x-coordinate of background image to be set
	 * @param y		y-coordinate of background image to be set
	 */
	public void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	/**
	 * Set the vector position of the background image
	 * @param dx	x vector of background image to be set
	 * @param dy	y vector of background image to be set
	 */
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Update background image movement according to 
	 * width and height of the game window.
	 */
	public void update() {
		xPosition += dx;
		yPosition += dy;
		
		xPosition = (xPosition * moveScale) % (int)Game.WIDTH;
		yPosition = (yPosition * moveScale) % (int)Game.HEIGHT;
	}
	
	/**
	 * Draw the background image.
	 * @param gc		image of the background
	 */
	public void render(GraphicsContext gc) {
		gc.drawImage(image, xPosition, yPosition, Game.WIDTH, Game.HEIGHT);
		
		if(xPosition < 0) {
			gc.drawImage(image, xPosition + Game.WIDTH, yPosition, Game.WIDTH, Game.HEIGHT);
		}
		if(xPosition > 0) {
			gc.drawImage(image, xPosition - Game.WIDTH, yPosition, Game.WIDTH, Game.HEIGHT);
		}
	}
}
