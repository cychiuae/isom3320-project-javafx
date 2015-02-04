package isom3320.project.game.scene;

import isom3320.project.game.multimedia.MultimediaHelper;
import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.object.Player;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WinScene extends Scene {
	/**Declare background instance*/
	private Background background;
	/**Declare Color of wordings*/
	private Color color;
	/**Declare Font of wordings*/
	private Font font;

	private MediaPlayer mediaPlayer;

	private int score;

	public WinScene() {
		// TODO Auto-generated constructor stub
		background = new Background("winscene.gif", 1);
		background.setVector(0, 0);

		color = Color.YELLOW;
		font = Font.font("Lucida Console", FontWeight.BOLD, 12);
		mediaPlayer = new MediaPlayer(MultimediaHelper.getMusicByName("win.mp3"));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		score = ScoreSystem.getInstance().getLatestScore();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		background.update();
		mediaPlayer.play();
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);

		gc.setFill(color);
		gc.setFont(font);
		gc.fillText("Your score is " + score, 6, 360);
		gc.fillText("You seems lucky. Isn't?", 6, 370);
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
		default:
		{
			mediaPlayer.stop();
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
		}
		}
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub

	}
}