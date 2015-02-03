package isom3320.project.game;

import isom3320.project.game.scene.SceneManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class GameWindow is inherited from Canvas, which help to 
 * defines window sizes and draw background image.
 * @author kevingok
 * */
public class GameWindow extends Canvas {
	
	/** Defines graphic context variable for background image*/
	private GraphicsContext gc;
	
	/** 
	 * Class constructor specifying window width and height 
	 * as well as getting image.
	 * @param width		Sets the width of the window
	 * @param height	Sets the height of the window*/
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
