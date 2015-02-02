package isom3320.project.game.object;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;

public abstract class Bullet extends GameObject {
	protected final int FLYING = 0;
	protected final int HITING = 1;

	protected ArrayList<ArrayList<Image>> sprites;
	protected boolean isHit;
	protected boolean shouldBeRemoved;
	protected int damage;

	public Bullet(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}
	
	public void isHit() {
		if(!isHit) {
			dx = 0;
			isHit = true;
			animation.setFrames(sprites.get(HITING));
			animation.setDelay(50);
		}
	}
	
	public int getDamage() {
		return damage;
	}
	
	public boolean shoudBeRemove() {
		return shouldBeRemoved;
	}

	@Override
	public void update() {
		int currentRow = (int) (yPosition / tileSize);
		
		double nextX = xPosition + dx;
		
		if(dx > 0) {
			if(map.getTileType(currentRow, (int)((xPosition + 10) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		else if(dx < 0) {
			if(map.getTileType(currentRow, (int)((xPosition - 10) / tileSize)) == Tile.BLOCKTILE) {
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		
		xPosition = nextX;
		
		if(dx == 0 && !isHit) {
			isHit();
		}
		
		animation.update();
		
		if(isHit && animation.playedOnce()) {
			shouldBeRemoved = true;
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(right) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
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