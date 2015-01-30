package isom3320.project.game.Map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import isom3320.project.game.Game;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Map {
	private double xPosition;
	private double xMin, xMax;
	
	private int[][] map;
	private Tile[] tiles;
	private double tileSize;
	
	private int numRows;
	private int numCols;
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public Map(String mapName, double tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = (int)(Game.HEIGHT / tileSize + 2);
		numColsToDraw = (int)(Game.WIDTH / tileSize + 2);
		xPosition = 10;
		loadMap(mapName);
		loadTiles();
	}
	
	public void loadMap(String mapName) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(mapName));
			
			numCols = Integer.parseInt(br.readLine());
			numRows = Integer.parseInt(br.readLine());
			
			map = new int[numRows][numCols];
			
			xMin = Game.WIDTH - numCols * tileSize;
			xMax = 0;
			
			String regex = "\\s+";
			for(int row = 0; row < numRows; row++) {
				String[] tokens = br.readLine().split(regex);
				
				for(int col = 0; col < numCols; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
			
//			for(int i = 0; i < 8; i++) {
//				for(int j = 0; j < 107; j++) {
//					System.out.printf("%-5d", map[i][j]);
//				}
//				System.out.println();
//			}
			
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadTiles() {
		Image tileSet = MultimediaHelper.getImageByName("grasstileset.gif");
		int numOfCol = (int) (tileSet.getWidth() / tileSize);
		
		tiles = new Tile[2 * numOfCol];
		
		for(int i = 0; i < numOfCol; i++) {
			WritableImage wi = MultimediaHelper.getSubImage(tileSet, i * (int)tileSize, 0, (int)tileSize, (int)tileSize);
			tiles[i] = new Tile(wi, Tile.NORMALTILE);
			wi = MultimediaHelper.getSubImage(tileSet, i * (int)tileSize, (int)tileSize, (int)tileSize, (int)tileSize);
			tiles[i + numOfCol] = new Tile(wi, Tile.BLOCKTILE);
		}
	}
	
	public void setPosition(double x) {
		xPosition = x;
		
		if(xPosition < xMin) {
			xPosition = xMin;
		}
		if(xPosition > xMax) {
			xPosition = xMax;
		}
		
		colOffset = (int) (-xPosition / tileSize);
	}
	
	public double getX() {
		return xPosition;
	}
	
	public double getTileSize() {
		return tileSize;
	}
	
	public int getTileType(int row, int col) {
		return tiles[map[row][col]].getType();
	}
	
	public void render(GraphicsContext gc) {
		for(int row = rowOffset; row < numRowsToDraw + rowOffset && row < numRows; row++) {
			for(int col = colOffset; col < numColsToDraw + colOffset && col < numCols; col++) {
				if(map[row][col] == 0) {
					continue;
				}
				else {
					gc.drawImage(tiles[map[row][col]].getImage(), xPosition + col * tileSize, row * tileSize, tileSize, tileSize);
				}
			}
		}
	}
}
