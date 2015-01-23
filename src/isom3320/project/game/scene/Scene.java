package isom3320.project.game.scene;

import isom3320.project.game.multimedia.graphics.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public abstract class Scene {
	
	protected Background background;
	
	public abstract void init(); 
	
	public abstract void update();
	
	public abstract void render(GraphicsContext gc);
	
	public abstract void keyPressed(KeyCode keyCode);
	
	public abstract void keyReleased(KeyCode keyCode);
}
