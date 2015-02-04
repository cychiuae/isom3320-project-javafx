package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public class Boss extends Enemy {
	private static final int WALKING = 0;
	private static final int FIRING = 1;
	private static final int JUMPING = 2;
	private static Media BOMBSOUND = MultimediaHelper.getMusicByName("bomb.wav");

	private ArrayList<FireBomb> balls;
	private boolean firing;

	private boolean jumping;

	private ArrayList<ArrayList<Image>> sprites;
	
	private Font font;

	public Boss(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		font = Font.font("Arial", 12);
		
		width = 64;
		height = 80;
		collisionHeight = 80;
		collisionWidth = 64;

		dx = 0.7;
		facingRight = right = true;

		hp = maxHp = 100;
		firing = jumping = false;
		damage = 5;

		balls = new ArrayList<FireBomb>();

		sprites = new ArrayList<ArrayList<Image>>();

		Image spritesheet = MultimediaHelper.getImageByName("boss.gif");
		int[] numFrames = new int[] {6, 6, 6};
		for(int i = 0; i < 3; i++) {
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
				new MediaPlayer(BOMBSOUND).play();
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
			dx = right ? 1.7 : -1.7;
		}

		if(falling) {
			dx = 0;
			dy += 0.15;
		}

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
		
		updateAction();
		animation.update();
	}

	@Override
	public void startFiring() {
		firing = true;
	}
	
	@Override
	public void startJumping() {
		jumping = true;
	}
	
	private void fireBall() {
		FireBomb fb = new FireBomb(map, facingRight);
		if(facingRight) {
			fb.setPosition(xPosition + 30, yPosition);
		}
		else {
			fb.setPosition(xPosition, yPosition);
		}
		balls.add(fb);
	}
	
	private void updateAction() {
		if(firing) {
			if(currentAction != FIRING) {
				currentAction = FIRING;
				animation.setFrames(sprites.get(FIRING));
				animation.setDelay(50);
				width = 60;
				fireBall();
			}
			if(animation.playedOnce()) {
				firing = false;
			}
		}
		else if(jumping) {
			if(currentAction != JUMPING) {
				currentAction = JUMPING;
				animation.setFrames(sprites.get(JUMPING));
				animation.setDelay(100);
				width = 60;
				falling = true;
				dy = -2.8;
			}
			if(animation.playedOnce()) {
				jumping = false;
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
		for(int i = 0; i < balls.size(); i++) {
			FireBomb fb = balls.get(i);
			
			if(fb.shoudBeRemove()) {
				balls.remove(i);
				i--;
			}
			else {
				fb.update();
			}
		}
	}
	
	private void drawBody(GraphicsContext gc) {
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
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub	
		gc.setFont(font);
		gc.fillText(hp + "/" + maxHp, xPosition + map.getX() - width / 2, yPosition - 40);
		
		drawBody(gc);
		
		for(int i = 0; i < balls.size(); i++) {
			balls.get(i).render(gc);
		}
	}
}
