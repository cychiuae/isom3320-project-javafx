package isom3320.project.game.scene;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class SceneManager {
	private static SceneManager instance;
	
	public static final int MENU = 0;
	public static final int LEVEL1 = 1;
	
	private int currentLevel;
	private ArrayList<Scene> scenes;
	
	public static SceneManager getIntance() {
		if(instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}
	
	private SceneManager() {
		scenes = new ArrayList<Scene>();
		currentLevel = MENU;
		scenes.add(new MenuScene());
	}
	
	public void changeSceneLevel(int sceneLevel) {
		currentLevel = sceneLevel;
		scenes.get(sceneLevel).init();
	}
	
	public Scene getCurrentScene() {
		return scenes.get(currentLevel);
	}
	
	public void keyPressed(KeyCode keyCode) {
		getCurrentScene().keyPressed(keyCode);
	}
	
	public void keyReleased(KeyCode keyCode) {
		getCurrentScene().keyReleased(keyCode);
	}
}
