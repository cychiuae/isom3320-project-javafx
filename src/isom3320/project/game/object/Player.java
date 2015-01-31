package isom3320.project.game.object;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimediaHelper;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class Player extends Character {
	private static final int IDLE = 0;
	private static final int WALKING = 1;
	private static final int JUMPING = 2;
	private static final int FALLING = 3;
	private static final int FLYING = 4;
	private static final int FIRING = 5;
	private static final int ATTACKING = 6;

	private ArrayList<FireBall> balls;
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
		collisionHeight = 60;
		collisionWidth = 50;

		dx = 1.6;
		facingRight = true;

		hp = maxHp = 10;

		balls = new ArrayList<FireBall>();
		numOfFire = maxFire = 5;

		attackDamage = 5;
		attackRange = 80;

		sprites = new ArrayList<ArrayList<Image>>();

		Image spritesheet = MultimediaHelper.getImageByName("playersprites.gif");
		int[] numFrames = new int[] {2, 8, 1, 2, 4, 2, 5};
		for(int i = 0; i < 7; i++) {
			ArrayList<Image> frames = new ArrayList<Image>();

			for(int j = 0; j < numFrames[i]; j++) {
				if(i != ATTACKING) {
					frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(j * width), (int)(i * height), (int)width, (int)height));
				}
				else {
					frames.add(MultimediaHelper.getSubImage(spritesheet, (int)(j * width * 2), (int)(i * height), (int)width * 2, (int)height));
				}
			}
			sprites.add(frames);
		}

		animation = new Animation();
		currentAction = IDLE;
		animation.setFrames(sprites.get(IDLE));
		animation.setDelay(400);
	}
	
	public void checkHit(Enemy e) {
		for(int i = 0; i < balls.size(); i++) {
			Bullet fb = balls.get(i);
			
			if(fb.intersects(e)) {
				e.hit(fb.getDamage());
				
				fb.isHit();
			}
		}
		
		if(attack) {
			if(facingRight) {
				if(e.getXPosition() > xPosition && e.getXPosition() < xPosition + attackRange && e.getYPosition() > yPosition - height / 2 && e.getYPosition() < yPosition + height / 2) {
					e.hit(attackDamage);
				}
			}
			else {
				if(e.getXPosition() < xPosition && e.getXPosition() > xPosition - attackRange && e.getYPosition() > yPosition - height / 2 && e.getYPosition() < yPosition + height / 2) {
					e.hit(attackDamage);
				}
			}
			
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

		if(left || right) {
			facingRight = right;
			dx = right ? 1.6 : -1.6;
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

		if(isHit) {
			if((System.nanoTime() - hitTimer) / 1000000 > 1000) {
				isHit = false;
			}
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
		}

		if(firing && currentAction != FIRING) {
			if(numOfFire > 0) {
				numOfFire--;
				FireBall fb = new FireBall(map, facingRight);
				if(facingRight) {
					fb.setPosition(xPosition + 30, yPosition);
				}
				else {
					fb.setPosition(xPosition, yPosition);
				}
				balls.add(fb);
			}
		}

		for(int i = 0; i < balls.size(); i++) {
			FireBall fb = balls.get(i);
			
			if(fb.shoudBeRemove()) {
				balls.remove(i);
				i--;
			}
			else {
				fb.update();
			}
		}

		xPosition = nextX;
		yPosition = nextY;

		updateAction();
		animation.update();
	}

	private void updateAction() {
		if(firing) {
			if(currentAction != FIRING) {
				currentAction = FIRING;
				animation.setFrames(sprites.get(FIRING));
				animation.setDelay(100);
				width = 60;
			}
			if(animation.playedOnce()) {
				firing = false;
			}
		}
		else if(dy > 0) {
			if(flying) {
				if(currentAction != FLYING) {
					currentAction = FLYING;
					animation.setFrames(sprites.get(FLYING));
					animation.setDelay(100);
					width = 60;
				}
			}
			else {
				if(currentAction != FALLING) {
					currentAction = FALLING;
					animation.setFrames(sprites.get(FALLING));
					animation.setDelay(100);
					width = 60;
				}
			}
		}
		else if(dy < 0) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(30);
				width = 60;
			}
		}
		else if(attack) {
			if(currentAction != ATTACKING) {
				currentAction = ATTACKING;
				animation.setFrames(sprites.get(ATTACKING));
				animation.setDelay(50);
				width = 120;
				if(animation.playedOnce()) {
					attack = false;
				}
			}
		}
		else if(left || right) {
			if(currentAction != WALKING) {
				currentAction = WALKING;
				animation.setFrames(sprites.get(WALKING));
				animation.setDelay(100);
				width = 60;
			}
		}
		else {
			if(currentAction != IDLE) {
				currentAction = IDLE;
				animation.setFrames(sprites.get(IDLE));
				animation.setDelay(400);
				width = 60;
			}
		}
	}

	private void drawPlayer(GraphicsContext gc) {
		if(isHit) {
			if((System.nanoTime() - hitTimer) / 100000000 % 2 == 0) {
				return;
			}
		}

		if(facingRight) {
			gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2, width, height);
		}
		else {
			gc.drawImage(animation.getImage(), xPosition + map.getX() + width / 2 , yPosition - height / 2, -width, height);
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		drawPlayer(gc);
		
		for(int i = 0; i < balls.size(); i++) {
			balls.get(i).render(gc);
		}

		gc.setFill(Color.WHITE);
		gc.fillText("FireBall: " + numOfFire + "/" + maxFire, 10, 20);
		gc.fillText("HP: " + hp + "/" + maxHp, 10, 50);
		gc.fillText("x: " + xPosition, 10, 100);

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
		if(keyCode == KeyCode.SPACE) {
			flying = true;
		}
		if(keyCode == KeyCode.F) {
			firing = true;
		}
		if(keyCode == KeyCode.D) {
			attack = true;
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
		if(keyCode == KeyCode.SPACE) {
			flying = false;
		}
		if(keyCode == KeyCode.D) {
			attack = false;
		}
	}

}
