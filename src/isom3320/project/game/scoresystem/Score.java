package isom3320.project.game.scoresystem;

public class Score {
	private String playerName;
	private int score;
	
	public Score() {
		playerName = "Player";
		score = 0;
	}

	public Score(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return "Score: " + score;
	}
}
