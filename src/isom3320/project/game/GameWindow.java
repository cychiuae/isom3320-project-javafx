package isom3320.project.game;

import isom3320.project.game.scene.SceneManager;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GameWindow extends Canvas {
	
	private GraphicsContext gc;
	
	public GameWindow(int width, int height) {
		super(width, height);
		gc = getGraphicsContext2D();
	}
	
	public void update() {
		SceneManager.getIntance().getCurrentScene().update();
	}
	
	public void render() {
		gc.clearRect(0, 0, getWidth(), getHeight());
		SceneManager.getIntance().getCurrentScene().render(gc);
	}
}
