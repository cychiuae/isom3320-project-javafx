/** Class GameWindow is inherited from Canvas, which help to defines window sizes and draw background image.*/
package isom3320.project.game;

import isom3320.project.game.scene.SceneManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameWindow extends Canvas {
	
	/** Defines graphic context variable for background image*/
	private GraphicsContext gc;
	
	/** Set window width and height and get image*/
	public GameWindow(int width, int height) {
		super(width, height);
		gc = getGraphicsContext2D();
	}
	
	/** Update current scene*/
	public void update() {
		SceneManager.getInstance().getCurrentScene().update();
	}
	
	/** Render scene and print background image*/
	public void render() {
		gc.clearRect(0, 0, getWidth(), getHeight());
		SceneManager.getInstance().getCurrentScene().render(gc);
	}
}
