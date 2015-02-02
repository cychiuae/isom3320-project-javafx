package isom3320.project.game.object;

import java.util.ArrayList;

import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Coin extends GameObject {

	public Coin(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
		
		width = height = 60;
		
		Image spritesheet = MultimediaHelper.getImageByName("coin.png");
		ArrayList<Image> sprite = new ArrayList<Image>();
		for(int i = 0; i < 6; i++) {
			if(i == 4)
				continue;
			sprite.add(MultimediaHelper.getSubImage(spritesheet, (int) (i * width), 0, (int) width, (int) height));
		}
		
		animation = new Animation();
		animation.setFrames(sprite);
		animation.setDelay(100);
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
