package isom3320.project.game.object;

import isom3320.project.game.Map.Map;

public abstract class Character extends GameObject {

	protected int hp;
	protected int maxHp;
	protected boolean isDead;
	protected boolean facingRight;
	
	public Character(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}

	public int getHp() {
		return hp;
	}
	
	public int getMaxHP() {
		return maxHp;
	}
	
	public boolean isDead() {
		return isDead;
	}
}
