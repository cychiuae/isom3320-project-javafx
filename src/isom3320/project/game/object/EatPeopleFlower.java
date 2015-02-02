package isom3320.project.game.object;

import java.util.ArrayList;
import java.util.Random;

import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class EatPeopleFlower extends GameObject {
	
	double collisionWidth, collisionHeight;
	
	int damage;
	
	public EatPeopleFlower(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		width = height = 60;
		collisionWidth = 28;
		collisionHeight = 30;
		
		damage = 1;
		
		Image spritesheet = MultimediaHelper.getImageByName("flower.png");
		ArrayList<Image> sprite = new ArrayList<Image>();
		for(int i = 0; i < 2; i++) {
			sprite.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(new Random().nextInt(300) + 200);
	}

	public double getCollisionWidth() {
		return collisionWidth;
	}

	public double getCollisionHeight() {
		return collisionHeight;
	}
	
	public int getDamage() {
		return damage;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		animation.update();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage(animation.getImage(), xPosition + map.getX() - width / 2, yPosition - height / 2, width, height);
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
