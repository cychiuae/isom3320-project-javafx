package isom3320.project.game.scene;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import isom3320.project.game.Game;
import isom3320.project.game.Map.Map;
import isom3320.project.game.multimedia.graphics.Background;
import isom3320.project.game.object.Boss;
import isom3320.project.game.object.Coin;
import isom3320.project.game.object.EatPeopleFlower;
import isom3320.project.game.object.Enemy;
import isom3320.project.game.object.Mushroom;
import isom3320.project.game.object.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Level1 extends Scene {

	private Map map;
	private Background background;

	private int score;
	private Font font;

	private int currentOption;
	private String[] options;
	
	private boolean stop;

	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Coin> coins;
	private ArrayList<EatPeopleFlower> flowers;

	public Level1() {
		stop = false;
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		map = new Map("Resources/level1-1.txt", 60);
		map.setPosition(0);

		background = new Background("background.png", 0.1);

		score = 0;
		font = Font.font("Arial", FontWeight.NORMAL, 24);

		player = new Player(map);
		player.setPosition(100, 100);

		enemies = new ArrayList<Enemy>();
		createEnemies();

		coins = new ArrayList<Coin>();		
		createCoins();
		
		flowers = new ArrayList<EatPeopleFlower>();
		createFlowers();
		
		currentOption = 0;
		options = new String[] {
				"Resume",
				"Quit"
		};
	}

	private void createFlowers() {
		for(int i = 0; i < 10; i++) {
			EatPeopleFlower f = new EatPeopleFlower(map);
			f.setPosition(995 + i * 30, 400);
			flowers.add(f);
		}
		
		for(int i = 0; i < 12; i++) {
			EatPeopleFlower f = new EatPeopleFlower(map);
			f.setPosition(2310 + i * 30, 400);
			flowers.add(f);
		}
	}

	private void createEnemies() {
		Enemy boss = new Boss(map);
		boss.setPosition(6300, 350);
		enemies.add(boss);
		
		int[][] enemiesCoord = new int[][] {
				{300, 200},
				{1530, 390},
				{1955, 390},
				{2930, 390},
				{3290, 390},
				{3570, 390},
				{4400, 390},
				{4600, 390},
				{4800, 390}
		};
		
		for(int i = 0; i < enemiesCoord.length; i++) {
			Enemy mushroom = new Mushroom(map);
			mushroom.setPosition(enemiesCoord[i][0], enemiesCoord[i][1]);
			enemies.add(mushroom);
		}
	}

	private void createCoins() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Resources/coin.txt"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				String[] coords = line.split(",");
				int x = Integer.parseInt(coords[0].trim());
				int y = Integer.parseInt(coords[1].trim());
				
				Coin c = new Coin(map);
				c.setPosition(x, y);
				coins.add(c);
			}
			
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		if(player.isDead()) {
			SceneManager.getInstance().changeSceneLevel(SceneManager.GAMEOVERSCENE);
		}

		for(int i = 0; i < coins.size(); i++) {
			Coin c = coins.get(i);
			c.update();

			if(player.gotCoin(c)) {
				c.gotIt();
			}

			if(c.isGot()) {
				score += 10;
				coins.remove(i);
				i--;
			}
		}

		Enemy boss = enemies.get(0);
		
		for(int i = 1; i < enemies.size(); i++) {
			Enemy mushroom = enemies.get(i);

			mushroom.update();

			if(player.intersects(mushroom)) {
				player.hit(mushroom.getDamage());
			}

			player.checkHit(mushroom);

			if(mushroom.isDead()) {
				score += 5;
				enemies.remove(i);
				i--;
			}
		}

		for(EatPeopleFlower f : flowers) {
			f.update();
			player.checkAteByFlower(f);
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
				score += 100;
				SceneManager.getInstance().changeSceneLevel(SceneManager.WINSCENE);
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

		for(Coin c : coins) {
			c.render(gc);
		}
		
		for(EatPeopleFlower f : flowers) {
			f.render(gc);
		}

		gc.setFill(Color.WHITE);
		gc.fillText("Score: " + score, 500, 20);

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
