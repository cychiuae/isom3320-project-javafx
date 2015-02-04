package isom3320.project.game.scoresystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class ScoreSystem aims at creating, reading and writing score history file. 
 * It also include sorting of all score records and retrieving the best three 
 * scores for displaying on the screen.
 * 
 * @author kevingok
 *
 */
public class ScoreSystem {
	/**Define a instance of ScoreSystem class.*/
	private static ScoreSystem instance;
	/**Define filename of score history text file.*/
	private static final String scoreFile = "scores.txt";
	
	/**Define scores ArrayList.*/
	private ArrayList<Score> scores;
	

	/**
	 * Class constructor creates new ArrayList to store score data, and
	 * run method to read score text file. 
	 */
	private ScoreSystem() {
		scores = new ArrayList<Score>();
		readScoreFile();
	}
	
	/**
	 * Retrieve an instance of ScoreSytem class, otherwise create a new one.
	 * @return		Created or newly created ScoreSystem instance
	 */
	public static ScoreSystem getInstance() {
		if(instance == null) {
			instance = new ScoreSystem();
		}
		return instance;
	}
	
	/**
	 * Read score history data text file and store into ArrayList.
	 */
	private void readScoreFile() {
		try {
			File f = new File(scoreFile);
			if(f.isFile()) {
				BufferedReader br = new BufferedReader(new FileReader(f));
				
				String line = null;
				while((line = br.readLine()) != null) {
					String[] lineData = line.split(",");
					Score s = new Score(lineData[0].trim(), Integer.parseInt(lineData[1].trim()));
					scores.add(s);
				}
				
				br.close();
			}		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Save history data text file.
	 */
	public void saveScoreFile() {
		try {
			PrintWriter writer = new PrintWriter(scoreFile, "UTF-8");
			
			for(Score s : scores) {
				writer.println(s.getPlayerName() + " , " + s.getScore());
			}
			
			writer.close();
			
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Add new score record into text file.
	 * @param s		New score after current game play finished.
	 */
	public void addScoreRecord(Score s) {
		scores.add(s);
	}
	
	/**
	 * Sort all scores in text file.
	 */
	private void sortScores() {
		scores.sort(new Comparator<Score>() {
			@Override
			public int compare(Score o1, Score o2) {
				// TODO Auto-generated method stub
				return -(o1.getScore() - o2.getScore());
			}
		});
	}
	
	public int getLatestScore() {
		if(scores.size() > 0) {
			return scores.get(scores.size() - 1).getScore();
		}
		return 0;
	}
	
	/**
	 * Retrieve the top highest 3 scores array 
	 * @return		Top 3 highest scores as an array
	 */
	public Score[] getTopThreeScore() {
		ArrayList<Score> result = new ArrayList<Score>();
		sortScores();
		
		for(int i = 0; i < scores.size() && i < 3; i++) {
			result.add(scores.get(i));
		}

		return result.toArray(new Score[result.size()]);
	}
}
