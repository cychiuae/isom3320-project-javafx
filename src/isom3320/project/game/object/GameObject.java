package isom3320.project.game.object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import isom3320.project.game.Map.Map;

public abstract class GameObject {
	protected Map map;
	protected double tileSize;
	
	protected double xPosition;
	protected double yPosition;
	protected double dx;
	protected double dy;
	
	protected double width;
	protected double height;
	protected double collisionWidth;
	protected double collisionHeight;
	
	protected Animation animation;
	protected int currentAction;
	
	protected boolean left;
	protected boolean right;
	protected boolean up;
	protected boolean down;
	protected boolean jumping;
	protected boolean falling;
	
	public GameObject(Map map) {
		this.map = map;
		tileSize = map.getTileSize();
	}
	
	public boolean intersects(GameObject gameObject) {
		Rectangle r = new Rectangle(xPosition, yPosition, collisionWidth, collisionHeight);
		return r.intersects(gameObject.getXPosition(), gameObject.getYPosition(), gameObject.getWidth(), gameObject.getHeight());
	}
	
	public double getXPosition() {
		return xPosition;
	}
	
	public double getYPosition() {
		return yPosition;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getHeight() {
		return height;
	}
	
	public void setPosition(double x, double y) {
		xPosition = x;
		yPosition = y;
	}
	
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}

	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	public abstract void update();
	
	public abstract void render(GraphicsContext gc);
	
	public abstract void keyPressed(KeyCode keyCode);
	
	public abstract void keyReleased(KeyCode keyCode);
}
