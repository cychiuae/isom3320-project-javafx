package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.Map.Tile;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class Mushroom extends Enemy {
	
	private Font font;

	public Mushroom(Map map) {
		super(map);
		font = Font.font("Arial", 18);
		height = width = 60;
		collisionWidth = 50;
		collisionHeight = 40;
		hp = maxHp = 10;

		facingRight = right = true;

		damage = 2;
		Image spritesheet = MultimediaHelper.getImageByName("mushroom.png");
		ArrayList<Image> frames = new ArrayList<Image>();
		for(int i = 0; i < 3; i++) {
			frames.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), (int) height, (int) width, (int) height));
		}
		animation = new Animation();
		animation.setFrames(frames);
		animation.setDelay(40);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(left || right) {
			facingRight = right;
			dx = right ? 3 : -3;
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

		animation.update();
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
		drawBody(gc);
		gc.setFont(font);
		gc.fillText(hp + "/" + maxHp, xPosition + map.getX() - 20, yPosition - 20);
	}

	@Override
	public void startFiring() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startJumping() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void checkHit(Player player) {
		// TODO Auto-generated method stub
		
	}
}
