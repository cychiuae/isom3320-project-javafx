package isom3320.project.game.scene;

import isom3320.project.game.Game;
import isom3320.project.game.multimedia.graphics.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MenuScene extends Scene {

	private final String title;
	private Font titleFont;
	private Color titleColor;
	private Font normalFont;
	
	private String[] options;
	private int currentOption;
	public MenuScene() {
		background = new Background("menubg.gif", 1);
		background.setVector(-0.1, 0);
		
		title = Game.GAMETITLE;
		titleFont = Font.font("Century Gothic", FontWeight.NORMAL, 56);
		titleColor = Color.web("0xf00");
		
		normalFont = Font.font("Arial", FontWeight.NORMAL, 24);
		
		currentOption = 0;
		options = new String[] {
				"Start",
				"Highest Scores",
				"Help",
				"Quit"
		};
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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

		gc.setFont(titleFont);
		gc.setFill(titleColor);
		gc.fillText(title, 140, 140);
		
		gc.setFont(normalFont);
		for(int i = 0; i < options.length; i++) {
			if(currentOption == i) {
				gc.setFill(Color.RED);
			}
			else {
				gc.setFill(Color.BLACK);
			}
			
			if(i == 1) {
				gc.fillText(options[i], 240, 280 + i * 30);
			}
			else {
				gc.fillText(options[i], 290, 280 + i * 30);
			}	
		}
	}

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(keyCode == KeyCode.UP) {
			currentOption--;
			if(currentOption < 0) {
				currentOption = options.length - 1;
			}
		}
		if(keyCode == KeyCode.DOWN) {
			currentOption++;
			if(currentOption >= options.length) {
				currentOption = 0;
			}
		}
		if(keyCode == KeyCode.ENTER) {
			if(currentOption == options.length - 1) {
				System.exit(0);
			}
			if(currentOption == 0) {
				SceneManager.getInstance().changeSceneLevel(SceneManager.LEVEL1);
			}
			if(currentOption == 1) {
				SceneManager.getInstance().changeSceneLevel(SceneManager.HIGHESTSCORESCENE);
			}
		}
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		
	}

}
