package isom3320.project.game.scoresystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;

public class ScoreSystem {
	private static ScoreSystem instance;
	private static final String scoreFile = "scores.txt";
	
	private ArrayList<Score> scores;
	
	public static ScoreSystem getInstance() {
		if(instance == null) {
			instance = new ScoreSystem();
		}
		return instance;
	}
	
	private ScoreSystem() {
		scores = new ArrayList<Score>();
		readScoreFile();
	}
	
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
	
	public void addScoreRecord(Score s) {
		scores.add(s);
	}
	
	private void sortScores() {
		scores.sort(new Comparator<Score>() {
			@Override
			public int compare(Score o1, Score o2) {
				// TODO Auto-generated method stub
				return -(o1.getScore() - o2.getScore());
			}
		});
	}
	
	public Score[] getTopThreeScore() {
		ArrayList<Score> result = new ArrayList<Score>();
		sortScores();
		
		for(int i = 0; i < scores.size() && i < 3; i++) {
			result.add(scores.get(i));
		}

		return result.toArray(new Score[result.size()]);
	}
}
