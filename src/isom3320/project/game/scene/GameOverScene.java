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

public class GameOverScene extends Scene {
	/**Declare background instance*/
	private Background background;
	/**Declare Color of wordings*/
	private Color color;
	/**Declare Font of wordings*/
	private Font font;

	private int score;
	
	private static Media GAMEOVERSOUND = MultimediaHelper.getMusicByName("gameover.wav");

	public GameOverScene() {
		// TODO Auto-generated constructor stub
		background = new Background("gameoverscene.gif", 1);
		background.setVector(0, 0);
		
		color = Color.YELLOW;
		font = Font.font("Lucida Console", FontWeight.BOLD, 12);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		score = ScoreSystem.getInstance().getLatestScore();
		new MediaPlayer(GAMEOVERSOUND).play();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		background.update();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		
		gc.setFill(color);
		gc.setFont(font);
		gc.fillText("Your score is " + score, 6, 360);
		gc.fillText("It is quite easy actually. Isn't?", 6, 370);
	}

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

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}
}