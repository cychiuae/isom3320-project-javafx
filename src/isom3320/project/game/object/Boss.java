package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Boss extends Enemy {
	private static final int WALKING = 0;
	private static final int FIRING = 1;

	private ArrayList<FireBomb> balls;
	private int numOfFire;
	private int maxFire;
	private boolean firing;

	private boolean attack;
	private int attackDamage;
	private int attackRange;

	private boolean flying;

	private ArrayList<ArrayList<Image>> sprites;

	public Boss(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		width = 80;
		height = 92;
		collisionHeight = 90;
		collisionWidth = 80;

		dx = 1.6;
		facingRight = true;

		hp = maxHp = 10;

		balls = new ArrayList<FireBomb>();
		numOfFire = maxFire = 5;

		attackDamage = 5;
		attackRange = 80;

		sprites = new ArrayList<ArrayList<Image>>();

		Image spritesheet = MultimediaHelper.getImageByName("boss.gif");
		int[] numFrames = new int[] {6, 6};
		for(int i = 0; i < 2; i++) {
			ArrayList<Image> frames = new ArrayList<Image>();

			for(int j = 0; j < numFrames[i]; j++) {
				frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(j * width), (int)(i * height), (int)width, (int)height));
			}
			sprites.add(frames);
		}

		animation = new Animation();
		currentAction = WALKING;
		animation.setFrames(sprites.get(WALKING));
		animation.setDelay(400);
	}

	public void checkHit(Player p) {
		for(int i = 0; i < balls.size(); i++) {
			Bullet fb = balls.get(i);

			if(fb.intersects(p)) {
				p.hit(fb.getDamage());

				fb.isHit();
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(left || right) {
			facingRight = right;
			dx = right ? 0.3 : -0.3;
		}

		if(falling) {
			dx = 0;
			dy += 0.15;
		}

		int currentCol = (int) (xPosition / tileSize);
		int currentRow = (int) (yPosition / tileSize);

		double nextX = xPosition + dx;
		double nextY = yPosition + dy;

		if(dx > 0) {
			if(map.getTileType(currentRow, (int)((xPosition + 30) / tileSize)) == Tile.BLOCKTILE) 
			{
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}
		else if(dx < 0) {
			if(map.getTileType(currentRow, (int)((xPosition - 30) / tileSize)) == Tile.BLOCKTILE) {
				dx = 0;
				nextX = xPosition;
			}
			else {
				nextX += dx;
			}
		}

		if(dy > 0) {
			if(map.getTileType((int) ((nextY + height / 2 - 1) / tileSize), (int) ((xPosition - (width - 15) / 2) / tileSize)) == Tile.BLOCKTILE || 
					map.getTileType((int) ((nextY + height / 2 - 1) / tileSize), (int) ((xPosition + (width - 15) / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				falling = false;
				nextY = yPosition;
			}
			else {
				nextY += dy;
			}
		}
		else if(dy < 0) {
			if(map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition - (width - 15) / 2) / tileSize)) == Tile.BLOCKTILE || 
					map.getTileType((int) ((nextY - height / 2) / tileSize), (int) ((xPosition + (width - 15) / 2 - 1) / tileSize)) == Tile.BLOCKTILE) 
			{
				dy = 0;
				nextY = yPosition;
			}
			else {
				nextY += dy;
			}
		}

		if(!falling) {
			if(!(map.getTileType(currentRow + 1, (int) ((xPosition + (width - 15) / 2 - 1) / tileSize) ) == Tile.BLOCKTILE) && !(map.getTileType(currentRow + 1, (int) ((xPosition - (width - 15) / 2) / tileSize)) == Tile.BLOCKTILE)) {
				falling = true;
			}

			if(right && dx == 0) {
				left = true;
				right = facingRight = false;
			}
			else if(left && dx == 0) {
				left = false;
				right = facingRight = true;
			}
		}

		if(isHit) {
			if((System.nanoTime() - hitTimer) / 1000000 > 1000) {
				isHit = false;
			}
		}

		xPosition = nextX;
		yPosition = nextY;

		animation.update();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub		
		if(isHit) {
			if((System.nanoTime() - hitTimer) / 100000000 % 2 == 0) {
				return;
			}
		}
		if(facingRight) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
		for(int i = 0; i < balls.size(); i++) {
			balls.get(i).render(gc);
		}
	}
}