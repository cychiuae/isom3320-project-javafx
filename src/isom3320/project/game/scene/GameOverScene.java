package isom3320.project.game.scene;

import isom3320.project.game.multimedia.MultimediaHelper;
import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class GameOverScene is scene shown when player is dead. Such game over scene
 * is a background image of traditional window error blue screen covered with 
 * scores highlighted in yellow.
 * 
 * @author kevingok
 *
 */
public class GameOverScene extends Scene {
	/**Declare background instance*/
	private Background background;
	/**Declare Color of wordings*/
	private Color color;
	/**Declare Font of wordings*/
	private Font font;

	/**Declare score variable*/
	private int score;
	
	/**Define game over sound effect*/
	private static Media GAMEOVERSOUND = MultimediaHelper.getMusicByName("gameover.wav");

	/**Class constructor declares background image, font details of
	 * final score description*/ 
	public GameOverScene() {
		// TODO Auto-generated constructor stub
		background = new Background("gameoverscene.gif", 1);
		background.setVector(0, 0);
		
		color = Color.YELLOW;
		font = Font.font("Lucida Console", FontWeight.BOLD, 12);
	}
	
	/**
	 * Initiate the latest score before death of player and emits game over 
	 * sound effect.
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		score = ScoreSystem.getInstance().getLatestScore();
		new MediaPlayer(GAMEOVERSOUND).play();
	}

	/**
	 * Update background.
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		background.update();
	}

	/**
	 * Draw background and print score details.
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		
		gc.setFill(color);
		gc.setFont(font);
		gc.fillText("Your score is " + score, 6, 360);
		gc.fillText("It is quite easy actually. Isn't?", 6, 370);
	}

	/**
	 * Handle key pressed event. By pressing ENTER, user will prompted back
	 * to menu scene.
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case RIGHT:
		case UP:
		case LEFT:
		case DOWN:
		case F:
		case D:
			break;
		case ENTER:
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
			break;
		}
	}

	/**Handle key release event*/
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}
}