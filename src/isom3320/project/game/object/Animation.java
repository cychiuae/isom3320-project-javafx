package isom3320.project.game.object;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Animation {
	private ArrayList<Image> frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(ArrayList<Image> frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public void update() {
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.size()) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	public Image getImage() {
		return frames.get(currentFrame);
	}
	
	public boolean playedOnce() {
		return playedOnce;
	}
}
