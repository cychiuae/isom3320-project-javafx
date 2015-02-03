package isom3320.project.game.scene;

import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class HighScoreScene shows the game scene of score board. 
 * @author kevingok
 *
 */
public class HighScoreScene extends Scene {
	/**Declare background instance*/
	private Background background;
	/**Declare Font color of the title*/
	private Color titleColor;
	/**Declare Font of the title*/
	private Font titleFont;
	/**Declare Font of other wordings*/
	private Font font;
	
	/**Define scores array for showing highest three score on the list*/
	private Score[] scores;

	/**
	 * Class constructor defines all variables for scene initialization.
	 */
	public HighScoreScene() {
		background = new Background("menubg.gif", 1);
		background.setVector(-0.1, 0);
		
		titleColor = Color.RED;
		titleFont = Font.font("Centry Gothic", FontWeight.NORMAL, 56);
		font = Font.font("Arial", FontWeight.NORMAL, 24);
		
		scores = ScoreSystem.getInstance().getTopThreeScore();
	}
	
	/**
	 * Initiate score board that is retrieved from score history file 
	 */
	@Override
	public void init() {
		// TODO Auto-generated method stub
		scores = ScoreSystem.getInstance().getTopThreeScore();
	}

	/**
	 * Update background
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		background.update();
	}

	/**
	 * Draw the background image and wordings that will needed on scene
	 */
	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		
		gc.setFill(titleColor);
		gc.setFont(titleFont);
		gc.fillText("Highest Score", 140, 140);
		
		gc.setFont(font);
		for(int i = 0; i < scores.length; i++) {
			gc.fillText(i + 1 + ". " + scores[i].getPlayerName() + "......................................." + scores[i].getScore(), 140, 200 + 40 * i);
		}
		
		gc.setFill(Color.RED);
		gc.fillText("Back", 290, 390);
	}

	/**
	 * Handle keyboard pressed event
	 */
	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.ENTER) {
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
		}
	}
	
	/**
	 * Handle keyboard released event
	 */
	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}
}
