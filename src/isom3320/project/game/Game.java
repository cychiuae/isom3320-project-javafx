/** Class Game is for game initialization. */

package isom3320.project.game;

import isom3320.project.game.multimedia.MultimediaHelper;
import isom3320.project.game.scene.Level1;
import isom3320.project.game.scene.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

@SuppressWarnings("deprecation")
public class Game extends Application implements EventHandler<KeyEvent>{
 
	/** Defines the title of the Stage.*/
	public static final String GAMETITLE = "MARISOM3320";
	/** Defines height of this stage*/
	public static final int WIDTH = 640;
	/** Defines width of this stage*/
	public static final int HEIGHT = 480;
	/** Defines ..?*/
	public static final int SCALE = 2;
	/** Defines frame per second*/
	public static final int FPS = 60;
	
	/** Defines customized class GameWindow*/
	private GameWindow gameWindow;
	/** Defines Timeline*/
	private Timeline gameTimeLine;
	/** Defines customized class SceneManager*/
	private SceneManager sceneManager;
	/** Defines MediaPlayer*/
	private MediaPlayer mediaPlayer;
	
	/** Defines playing indicator*/
	private boolean playing;
	
	/** Initialize scene, timeline, window, media files before main game loop
	 * @throws Exception 		what exception???*/
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		super.init();
		
		gameWindow = new GameWindow(WIDTH, HEIGHT);
		sceneManager = SceneManager.getInstance();
		
		mediaPlayer = new MediaPlayer(MultimediaHelper.getMusicByName("bg.mp3"));
		
		Duration secondPerFrame = Duration.millis(1000 / (float) FPS);
		KeyFrame keyFrame = new KeyFrame(secondPerFrame, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				if(SceneManager.getInstance().getCurrentScene() instanceof Level1) {
					if(!playing) {
						mediaPlayer.play();
						playing = true;
					}
				}
				else {
					mediaPlayer.stop();
					playing = false;
				}
				
				gameWindow.update();
				gameWindow.render();
			}
		});

		gameTimeLine = TimelineBuilder.create().cycleCount(Animation.INDEFINITE).keyFrames(keyFrame).build();
		
	}

	/** Runs the program
	 * @throws Exception 		what exception???*/
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Pane root = new Pane();
		root.getChildren().add(gameWindow);
		Scene scene = new Scene(root, WIDTH, HEIGHT);
		stage.setTitle(GAMETITLE);
		stage.setScene(scene);
		
		scene.setOnKeyPressed(this);
		scene.setOnKeyReleased(this);
		
		gameTimeLine.play();
		
		stage.setResizable(false);
		stage.show();
		
	}

	/** stop(??)*/
	@Override
	public synchronized void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
	}

	/** main method*/
	public static void main(String[] args) {
		launch(args);
	}

	/** Handle KeyPressed, KeyReleased event*/
	@Override
	public void handle(KeyEvent event) {
		// TODO Auto-generated method stub
		if(event.getEventType() == KeyEvent.KEY_PRESSED) {
			sceneManager.keyPressed(event.getCode());
		}
		if(event.getEventType() == KeyEvent.KEY_RELEASED) {
			sceneManager.keyReleased(event.getCode());
		}
	}

}
