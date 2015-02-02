package isom3320.project.game.scene;

import java.util.ArrayList;
import java.util.Random;

import isom3320.project.game.Game;
import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.object.Boss;
import isom3320.project.game.object.Enemy;
import isom3320.project.game.object.Mushroom;
import isom3320.project.game.object.Player;
import isom3320.project.game.scoresystem.Score;
import isom3320.project.game.scoresystem.ScoreSystem;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
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
	private ArrayList<Enemy> enemies;

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

		background = new Background("background.png", 0.1);

		score = new Score("Player", 0);
		font = Font.font("Arial", FontWeight.NORMAL, 24);
		
		player = new Player(map);
		player.setPosition(200, 100);
		
		enemies = new ArrayList<Enemy>();
		createEnemies();

		currentOption = 0;
		options = new String[] {
				"Resume",
				"Quit"
		};
		
		if(inited) {
			ScoreSystem.getInstance().addScoreRecord(score);	
		}
		
		inited = true;
	}
	
	private void createEnemies() {
		Enemy boss = new Boss(map);
		boss.setPosition(6300, 350);
		enemies.add(boss);
		for(int i = 0; i < 15; i++) {
			Random random = new Random();
			Enemy mushroom = new Mushroom(map);
			mushroom.setPosition((random.nextDouble() * 5400) + 100, 100);
			enemies.add(mushroom);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(stop) {
			return;
		}
		
		map.setPosition(Game.WIDTH / 2 - player.getXPosition());
		player.update();

		Enemy boss = enemies.get(0);
		
		for(int i = 1; i < enemies.size(); i++) {
			Enemy mushroom = enemies.get(i);
			
			mushroom.update();
			
			if(player.intersects(mushroom)) {
				player.hit(mushroom.getDamage());
			}
			
			player.checkHit(mushroom);
			
			if(mushroom.isDead()) {
				enemies.remove(i);
				i--;
			}
		}
		
		if(player.getXPosition() > 5850) {
			boss.update();
			boss.checkHit(player);
			if(player.intersects(boss)) {
				player.hit(boss.getDamage());
			}
			player.checkHit(boss);
			if(boss.isDead()) {
				enemies.remove(0);
			}
			
			int[][] mapData = map.getMap();
			for(int i = 5; i < 7; i++) {
				mapData[i][94] = 3;
				mapData[i][97] = 4;

				mapData[i][95] = 22;
				mapData[i][96] = 22;
			}
			
			if((player.facingRight() && (player.getXPosition() < boss.getXPosition()) && !boss.facingRight()) ||
					(!player.facingRight() && (player.getXPosition() > boss.getXPosition()) && boss.facingRight())) {
				boss.startFiring();
			}
			
			if(player.getYPosition() < 300) {
				boss.startJumping();
			}
		}
	}

	@Override
	public void render(GraphicsContext gc) {
		// TODO Auto-generated method stub
		background.render(gc);
		map.render(gc);
		player.render(gc);
		
		for(Enemy e : enemies) {
			e.render(gc);
		}
		
		if(stop) {
			gc.setFill(Color.rgb(200, 200, 200, 0.5));
			gc.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
			drawOption(gc);
		}
	}
	
	private void drawOption(GraphicsContext gc) {
		gc.setFont(font);
		
		for(int i = 0; i < options.length; i++) {
			if(i == currentOption) {
				gc.setFill(Color.RED);
			}
			else {
				gc.setFill(Color.BLACK);
			}
			
			gc.fillText(options[i], 290, 240 + i * 30);
		}
	}

	@Override
	public void keyPressed(KeyCode keyCode) {
		// TODO Auto-generated method stub
		if(!stop) {
			player.keyPressed(keyCode);
		}
		
		if(stop) {
			if(keyCode == KeyCode.UP) {
				currentOption --;
				if(currentOption < 0) {
					currentOption = options.length - 1;
				}
			}
			
			if(keyCode == KeyCode.DOWN) {
				currentOption++;
				if(currentOption == options.length) {
					currentOption = 0;
				}
			}
			
			if(keyCode == KeyCode.ENTER) {
				stop = !stop;
				if(currentOption == options.length - 1) {
					SceneManager.getInstance().changeSceneLevel(SceneManager.MENU);
				}
			}
		}
		
		if(keyCode == KeyCode.ESCAPE) {
			stop = !stop;
		}
	}

	@Override
	public void keyReleased(KeyCode keyCode) {
		// TODO Auto-generated method stub
		player.keyReleased(keyCode);
	}

}
