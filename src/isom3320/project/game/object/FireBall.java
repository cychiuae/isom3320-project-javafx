package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.image.Image;

public class FireBall extends Bullet {
	public FireBall(Map map, boolean right) {
		super(map);
		// TODO Auto-generated constructor stub
		this.right = right;
		dx = right ? 3.8 : -3.8;
		damage = 10;
		
		width = height = 60;
		collisionHeight = collisionWidth = 40;

		sprites = new ArrayList<ArrayList<Image>>();
		Image spritesheet = MultimediaHelper.getImageByName("fireball.gif");
		ArrayList<Image> ballframes = new ArrayList<Image>();
		for(int i = 0; i < 4; i++) {
			ballframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		sprites.add(ballframes);
		ArrayList<Image> explosionframes = new ArrayList<Image>();
		for(int i = 0; i < 3; i++) {
			explosionframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), (int) height, (int) width, (int) height));
		}
		sprites.add(explosionframes);
		
		animation = new Animation();
		animation.setFrames(sprites.get(FLYING));
		animation.setDelay(100);
	}
}
