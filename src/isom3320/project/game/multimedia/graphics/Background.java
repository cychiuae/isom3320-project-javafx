package isom3320.project.game.multimedia.graphics;

import isom3320.project.game.Game;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background {
	private Image image;
	private double xPosition, yPosition;
	private double dx, dy;
	private double moveScale;
	
	public Background(String imageName, double moveScale) {
		image = MultimediaHelper.getImageByName(imageName);
		this.moveScale = moveScale;
	}
	
	public void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update() {
		xPosition += dx;
		yPosition += dy;
		
		xPosition = (xPosition * moveScale) % (int)Game.WIDTH;
		yPosition = (yPosition * moveScale) % (int)Game.HEIGHT;
	}
	
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
