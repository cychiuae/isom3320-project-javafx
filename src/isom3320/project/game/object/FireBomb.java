package isom3320.project.game.object;

import java.util.ArrayList;

import javafx.scene.image.Image;
import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.MultimediaHelper;

public class FireBomb extends Bullet {
	public FireBomb(Map map, boolean right) {
		super(map);
		// TODO Auto-generated constructor stub
		this.right = right;
		dx = right ? 3.0 : -3.0;
		damage = 5;
		
		width = 40;
		height = 22;
		collisionWidth = 40;
		collisionHeight = 22;
		
		sprites = new ArrayList<ArrayList<Image>>();
		Image spritesheet = MultimediaHelper.getImageByName("fireball.gif");
		ArrayList<Image> ballframes = new ArrayList<Image>();
		for(int i = 0; i < 3; i++) {
			ballframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		sprites.add(ballframes);
		
		int explosionWidth = 30, explosionHeight = 30;
		ArrayList<Image> explosionframes = new ArrayList<Image>();
		for(int i = 0; i < 4; i++) {
			explosionframes.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * explosionWidth), (int) explosionHeight, (int) explosionWidth, (int) explosionHeight));
		}
		sprites.add(explosionframes);
		
		animation = new Animation();
		animation.setFrames(sprites.get(FLYING));
		animation.setDelay(100);
	}
}