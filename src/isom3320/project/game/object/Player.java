package isom3320.project.game.object;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimeidaHelper;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Player extends Character {
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int FLYING = 4;
	private static final int FIRING = 5;
	private static final int ATTACKING = 6;
	
	private int numOfFire;
	private int maxFire;
	
	private boolean firing;

	private boolean attack;
	private int attackDamage;
	private int attackRange;
	
	private boolean flying;
	
	private ArrayList<ArrayList<Image>> sprites;
	
	public Player(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		width = 60;
		height = 60;
		
		dx = 1.6;
		facingRight = true;
		
		hp = maxHp = 10;
		numOfFire = maxFire = 5;
		
		attackDamage = 5;
		attackRange = 80;
		
		sprites = new ArrayList<ArrayList<Image>>();
		
		Image spritesheet = MultimeidaHelper.getImageByName("playersprites.gif");
		int[] numFrames = new int[] {2, 8, 1, 2, 4, 2, 5};
		for(int i = 0; i < 7; i++) {
			ArrayList<Image> frames = new ArrayList<Image>();
			
			for(int j = 0; j < numFrames[i]; j++) {
				if(i != ATTACKING) {
					frames.add(MultimeidaHelper.getSubImage(spritesheet, (int)(j * width), (int)(i * height), (int)width, (int)height));
				}
				else {
					frames.add(MultimeidaHelper.getSubImage(spritesheet, (int)(j * width), (int)(i * height), (int)width * 2, (int)height));
				}
			}
			sprites.add(frames);
		}
		
		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	boolean tl, tr, bl, br;
	public void calCon(double x, double y) {
		int lt = (int) ((x - width / 2) / tileSize);
		int rt = (int) ((x + width / 2 - 1) / tileSize);
		int tt = (int) ((y - height / 2) / tileSize);
		int bt = (int) ((y + height / 2 - 1) / tileSize);
		
		tl = map.getTileType(tt, lt) == Tile.BLOCKTILE;
		tr = map.getTileType(tt, rt) == Tile.BLOCKTILE;
		bl = map.getTileType(bt, lt) == Tile.BLOCKTILE;
		br = map.getTileType(bt, rt) == Tile.BLOCKTILE;
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
		if(left || right) {
			facingRight = right;
			dx = right ? 1.6 : -1.6;
			
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 60;
			}
		}
		else if(dx > 0 || dx < 0) {	
			dx = 0;
		}
		
		if((currentAction == ATTACKING || currentAction == FIRING) && !jumping && !falling) {
			dx = 0;
		}
		
		if(jumping && !falling) {
			dy = -4.8;
			falling = true;
		}
		
		if(falling) {
			if(dy > 0) {
				jumping = false;
				
				if(flying) {
					dy += 0.15 * 0.1;
				}
				else {
					dy += 0.15;
				}
				
				if(dy > 4) {
					dy = 4;
				}
			}
			else if(dy < 0 && !jumping) {
				dy += 0.3;
			}
			else {
				dy += 0.15;
			}
		}
		
		int currentCol = (int) (xPosition / tileSize);
		int currentRow = (int) (yPosition / tileSize);
		
		double nextX = xPosition + dx;
		double nextY = yPosition + dy;
		
		if(dx > 0) {
			if(map.getTileType((int) ((yPosition - height / 2) / tileSize), (int) ((nextX + width / 2 - 1) / tileSize)) == Tile.BLOCKTILE || 
			   map.getTileType((int) ((yPosition + height / 2 - 1) / tileSize), (int) ((nextX + width / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		else if(dx < 0) {
			if(map.getTileType((int) ((yPosition - height / 2) / tileSize), (int) ((nextX - width / 2) / tileSize)) == Tile.BLOCKTILE || 
			   map.getTileType((int) ((yPosition + height / 2 - 1) / tileSize), (int) ((nextX - width / 2) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		
		if(dy > 0) {
			if(map.getTileType((int) ((nextY + height / 2 - 1) / tileSize), (int) ((xPosition - width / 2) / tileSize)) == Tile.BLOCKTILE || 
			   map.getTileType((int) ((nextY + height / 2 - 1) / tileSize), (int) ((xPosition + width / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				falling = false;
				nextY = (currentRow + 1) * tileSize - height / 2;
			}
			else {
				nextY += dy;
			}
		}
		else if(dy < 0) {
			if(map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition - width / 2) / tileSize)) == Tile.BLOCKTILE || 
			   map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition + width / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				nextY = currentRow * tileSize + height / 2;
			}
			else {
				nextY += dy;
			}
		}
		
		if(!falling) {
			if(!(map.getTileType((int) ((nextY + 1 + height / 2 - 1) / tileSize), (int) ((xPosition - width / 2) / tileSize)) == Tile.BLOCKTILE) || 
			   !(map.getTileType((int) ((nextY + 1 + height / 2 - 1) / tileSize), (int) ((xPosition + width / 2 - 1) / tileSize)) == Tile.BLOCKTILE)) {
				falling = true;
			}
		}
		
		if(left || right) {
			
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 60;
			}
		}
		
		xPosition = nextX;
		yPosition = nextY;
		System.out.println(nextX);
		animation.update();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if(facingRight) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
		
	}

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.RIGHT) {
			right = true;
		}
		if(keyCode == KeyCode.LEFT) {
			left = true;
		}
		if(keyCode == KeyCode.UP) {
			jumping = true;
		}
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.RIGHT) {
			right = false;
		}
		if(keyCode == KeyCode.LEFT) {
			left = false;
		}
	}

}
