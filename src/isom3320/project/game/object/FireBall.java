package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimeidaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class FireBall extends GameObject {
	private final int FLYING = 0;
	private final int HITING = 1;

	private ArrayList<ArrayList<Image>> sprites;
	private boolean isHit;
	private boolean shouldBeRemoved;
	private int damage;
	
	public FireBall(Map map, boolean right) {
		super(map);
		// TODO Auto-generated constructor stub
		this.right = right;
		dx = right ? 3.8 : -3.8;
		damage = 5;
		
		width = height = 60;
		collisionHeight = collisionWidth = 40;

		sprites = new ArrayList<ArrayList<Image>>();
		Image spritesheet = MultimeidaHelper.getImageByName("fireball.gif");
		ArrayList<Image> frames = new ArrayList<Image>();
		for(int i = 0; i < 4; i++) {
			frames.add(MultimeidaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		sprites.add(frames);
		ArrayList<Image> frames2 = new ArrayList<Image>();
		for(int i = 0; i < 3; i++) {
			frames2.add(MultimeidaHelper.getSubImage(spritesheet, (int) (i * width), (int) height, (int) width, (int) height));
		}
		sprites.add(frames2);
		
		animation = new Animation();
		animation.setFrames(sprites.get(FLYING));
		animation.setDelay(100);
	}
	
	public void isHit() {
		if(!isHit) {
			dx = 0;
			isHit = true;
			animation.setFrames(sprites.get(HITING));
			animation.setDelay(50);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		int currentCol = (int) (xPosition / tileSize);
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
	
	public int getDamage() {
		return damage;
	}
	
	public boolean shoudBeRemove() {
		return shouldBeRemoved;
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
