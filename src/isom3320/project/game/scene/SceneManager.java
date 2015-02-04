package isom3320.project.game.scene;

import java.util.ArrayList;

import javafx.scene.input.KeyCode;

/**
 * Class SceneManager is for managing different scene of game. 
 * @author kevingok
 *
 */
public class SceneManager {
	
	private static SceneManager instance;
	
	/**Define Menu scene as 0*/
	public static final int MENU = 0;
	/**Define Level 1 scene as 0*/
	public static final int LEVEL1 = 1;
	/**Define Highest scoreboard scene as 0*/
	public static final int HIGHESTSCORESCENE = 2;
	/**Define Help scene as 0*/
	public static final int HELPSCENE = 3;
	/**Define Game over scene as 0*/
	public static final int GAMEOVERSCENE = 4;
	/**Define Win scene as 0*/
	public static final int WINSCENE = 5;
	
	/**Declare current level scene*/
	private int currentLevel;
	/**Declare Scene ArrayList*/
	private ArrayList<Scene> scenes;
	
	/**
	 * Get instance of SceneManager
	 * @return 		Instance of SceneManager
	 */
	public static SceneManager getInstance() {
		if(instance == null) {
			instance = new SceneManager();
		}
		return instance;
	}
	
	/**
	 * Class constructor initiates all scene in an ArrayList and
	 * sets initial scene as menu.
	 */
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
	
	/**
	 * Change scene of game.
	 * @param sceneLevel		Type of scene as integer
	 */
	public void changeSceneLevel(int sceneLevel) {
		currentLevel = sceneLevel;
		scenes.get(sceneLevel).init();
	}
	
	/**
	 * Retrieve current type of scene.
	 * @return 			Type of scene as integer
	 */
	public Scene getCurrentScene() {
		return scenes.get(currentLevel);
	}
	
	/**
	 * Handle Keyboard pressed event.
	 * @param keyCode		Keyboard code
	 */
	public void keyPressed(KeyCode keyCode) {
		getCurrentScene().keyPressed(keyCode);
	}
	
	/**
	 * Handle Keyboard released event.
	 * @param keyCode		Keyboard code
	 */
	public void keyReleased(KeyCode keyCode) {
		getCurrentScene().keyReleased(keyCode);
	}
}
