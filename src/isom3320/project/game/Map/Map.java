package isom3320.project.game.Map;

import java.io.BufferedReader;
import java.io.FileReader;

import isom3320.project.game.Game;
import isom3320.project.game.multimedia.MultimediaHelper;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 * Class Map is for generating the whole map of the level from Resource.
 * @author kevingok
 */
public class Map {
	/**Defines position of x.*/
	private double xPosition;
	/**Defines minimum and maximum of x.*/
	private double xMin, xMax;
	
	/**Defines 2D array storing row and column of ..*/
	private int[][] map;
	/**Defines tiles array for storing all tile indices.*/
	private Tile[] tiles;
	/**Defines size of tile.*/
	private double tileSize;
	
	/**Defines total row number of map image.*/
	private int numRows;
	/**Defines total column number of map image.*/
	private int numCols;
	/**Defines row offset number of map image.*/
	private int rowOffset;
	/**Defines column number of map image.*/
	private int colOffset;
	/**Defines row number of map image to be drawn.*/
	private int numRowsToDraw;
	/**Defines column number of map image to be drawn.*/
	private int numColsToDraw;
	
	/**
	 * Class constructor triggering loadMap and loadTile methods to generate 
	 * complete map object.
	 * @param mapName			Sets filename of map
	 * @param tileSize			Sets size of the tile*/
	public Map(String mapName, double tileSize) {
		this.tileSize = tileSize;
		numRowsToDraw = (int)(Game.HEIGHT / tileSize + 2);
		numColsToDraw = (int)(Game.WIDTH / tileSize + 2);
		xPosition = 10;
		loadMap(mapName);
		loadTiles();
	}

	/**
	 * Load the map index file from Resource folder and store total number of
	 * column and row of indices into 2D array.
	 * @param mapName			Sets filename of map
	 * @throws Exception 		If input exception occurred*/
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
			
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Load the map image file from Resource folder. Method will define whether 
	 * each tile is a block or able to be passed through*/
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
	
	/**
	 * Set position of x.
	 * @param x			Sets position*/
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
	
	/**
	 * Get position of x.
	 * @return 			Position of x
	 */
	public double getX() {
		return xPosition;
	}
	
	/**
	 * Get size of each tile.
	 * @return 			size of tile
	 */
	public double getTileSize() {
		return tileSize;
	}
	
	/**
	 * Get type of tile.
	 * @param row 		Total row number of map indices
	 * @param col		Total column number of map indices
	 * @return			type integer
	 */
	public int getTileType(int row, int col) {
		return tiles[map[row][col]].getType();
	}
	
	/**
	 * Get map array.
	 * @return			2D array of map
	 */
	public int[][] getMap() {
		return map;
	}
	
	/**
	 * Draw map from tile image.
	 * @param gc		Image of entire tile
	 */
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
