package isom3320.project.game.scoresystem;

/**
 * Class Score manage score of the player in every game play.
 * @author kevingok
 *
 */
public class Score {
	
	/**Defines name of the player.*/
	private String playerName;
	/**Defines score in each game play*/
	private int score;
	
	/**
	 * Class default constructor to initiate player name and score.
	 */
	public Score() {
		playerName = "Player";
		score = 0;
	}

	/**
	 * Class constructor specifying player name and score.
	 * @param playerName		Name of the player
	 * @param score				Score of the player
	 */
	public Score(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}
	
	/**
	 * Handling add score function. 
	 * @param score		Amount of score to be added
	 */
	public void addScore(int score) {
		this.score += score;
	}
	
	/**
	 * Set the total score.
	 * @param score		Total score to be set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Retrieve name of the player.
	 * @return		Name of the player
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	/**
	 * Retrieve score of the current game play.
	 * @return		Score of the game
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Reveal Score on the screen.
	 */
	@Override
	public String toString() {
		return "Score: " + score;
	}
}
