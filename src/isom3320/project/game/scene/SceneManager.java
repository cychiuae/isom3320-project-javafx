package isom3320.project.game.scene;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

public class SceneManager {
	private static SceneManager instance;
	
	public static final int MENU = 0;
	public static final int LEVEL1 = 1;
	public static final int HIGHESTSCORESCENE = 2;
	public static final int HELPSCENE = 3;
	public static final int GAMEOVERSCENE = 4;
	public static final int WINSCENE = 5;
	
	private int currentLevel;
	private ArrayList<Scene> scenes;
	
	public static SceneManager getInstance() {
		if(instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}
	
	private SceneManager() {
		scenes = new ArrayList<Scene>();
		currentLevel = MENU;
		scenes.add(new MenuScene());
		scenes.add(new Level1());
		scenes.add(new HighScoreScene());
		scenes.add(new HelpScene());
		scenes.add(new GameOverScene());
		scenes.add(new WinScene());
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
