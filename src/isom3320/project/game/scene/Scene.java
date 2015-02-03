package isom3320.project.game.scene;

import isom3320.project.game.multimedia.graphics.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

/**
 * Abstract class Scene regulate all behavior of its children  
 * @author kevingok
 *
 */
public abstract class Scene {
	
	/**Declare background instance*/
	protected Background background;
	
	/**Initiate ??*/
	public abstract void init(); 
	
	/** update background*/
	public abstract void update();
	
	/**Draw background image and text*/
	public abstract void render(GraphicsContext gc);
	
	/**Handle keyboard pressed event*/
	public abstract void keyPressed(KeyCode keyCode);
	
	/**Handle keyboard released event*/
	public abstract void keyReleased(KeyCode keyCode);
}
