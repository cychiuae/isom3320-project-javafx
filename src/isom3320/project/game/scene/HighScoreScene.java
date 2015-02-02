package isom3320.project.game.scene;

import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class HighScoreScene extends Scene {
	private Background background;
	
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	private Score[] scores;
	
	public HighScoreScene() {
		background = new Background("menubg.gif", 1);
		background.setVector(-0.1, 0);
		
		titleColor = Color.RED;
		titleFont = Font.font("Centry Gothic", FontWeight.NORMAL, 56);
		font = Font.font("Arial", FontWeight.NORMAL, 24);
		
		scores = ScoreSystem.getInstance().getTopThreeScore();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		scores = ScoreSystem.getInstance().getTopThreeScore();
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

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.ENTER) {
			SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
		}
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
	}
}
