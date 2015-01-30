package isom3320.project.game.object;

import isom3320.project.game.Map.Map;

public abstract class Character extends GameObject {

	protected int hp;
	protected int maxHp;
	
	protected boolean isDead;
	protected boolean facingRight;
	protected boolean isHit;
	protected long hitTimer;
	
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
	
	public boolean facingRight() {
		return facingRight;
	}
	
	public void hit(int damage) {
		if(isDead || isHit) {
			return;
		}
		
		hp -= damage;
		if(hp < 0) {
			hp = 0;
		}
		if(hp == 0) {
			isDead = true;
		}
		
		isHit = true;
		hitTimer = System.nanoTime();
	}
}
