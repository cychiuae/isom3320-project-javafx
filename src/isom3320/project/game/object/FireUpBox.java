package isom3320.project.game.object;

import isom3320.project.game.Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class FireUpBox extends GameObject {

	private int numOfFireBall;
	private boolean used;
	
	public FireUpBox(Map map, int numOfFireBall) {
		super(map);
		// TODO Auto-generated constructor stub
		width = height = 60;
		
		this.numOfFireBall = numOfFireBall;
		used = false;
	}
	
	public int getNumOfBall() {
		return numOfFireBall;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}

}
