package isom3320.project.game.object;

import isom3320.project.game.Map.Map;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public abstract class Enemy extends Character {

	protected int damage;
	
	public Enemy(Map map) {
		super(map);
	}
	
	public int getDamage() {
		return damage;
	}
	
}
