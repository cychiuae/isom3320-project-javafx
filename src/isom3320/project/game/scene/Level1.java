package isom3320.project.game.scene;

import java.util.ArrayList;

import isom3320.project.game.Game;
import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.object.FireBall;
import isom3320.project.game.object.Player;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Level1 extends Scene {

	private Map map;
	private Background background;

	private Score score;
	private Font font;

	private int currentOption;
	private String[] options;

	private boolean inited;
	private boolean stop;
	
	private Player player;
	private ArrayList<FireBall> fireBalls;

	public Level1() {
		inited = false;
		stop = false;
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		map = new Map("Resources/level1-1.txt", 60);
		map.setPosition(0);

		background = new Background("grassbg1.gif", 0.1);

		score = new Score("Player", 0);
		font = Font.font("Arial", FontWeight.NORMAL, 24);
		
		player = new Player(map);
		player.setPosition(100, 100);
		
		fireBalls = new ArrayList<FireBall>();

		currentOption = 0;
		options = new String[] {
				"Resume",
				"Restart",
				"Quit"
		};
		
		if(inited) {
			ScoreSystem.getInstance().addScoreRecord(score);	
		}
		
		inited = true;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		map.setPosition(Game.WIDTH / 2 - player.getXPosition());
		player.update();
		
		for(int i = 0; i < fireBalls.size(); i++) {
			fireBalls.get(i).update();
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		map.render(gc);
		player.render(gc);
	}

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		player.keyPressed(keyCode);
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		player.keyReleased(keyCode);
	}

}
