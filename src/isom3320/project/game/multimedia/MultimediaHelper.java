package isom3320.project.game.multimedia;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;

/**
 * Class MultimediaHelper assists for getting image or music file.
 * @author kevingok
 *
 */
public class MultimediaHelper {
	
	/**Defines directory of media resources*/
	public static final String resourcesDir = "Resources/";
	
	/**
	 * Returns an Image object that can then be painted on the screen.
	 * @param imageName		the filename of the image
	 * @return				image at specified path
	 */
	public static Image getImageByName(String imageName) {
		Image image = null;
		File imageFile = new File(resourcesDir + imageName);
		
		if(imageFile.isFile()) {
			image = new Image(imageFile.toURI().toString());
		}
		else {
			System.out.println("no file");
		}
		
		return image;
	}
	
	/**
	 * Returns a music object that can then be played during gameplay.
	 * @param musicName		the filename of the music
	 * @return				music(mp3) at specified path
	 */
	public static Media getMusicByName(String musicName) {
		Media music = null;
		
		File musicFile = new File(resourcesDir + musicName);
		
		if(musicFile.isFile()) {
			music = new Media(musicFile.toURI().toString());
		}
		
		return music;
	}
	
	/**
	 * Returns writable image object that can then be played during gameplay.
	 * @param image			the image object
	 * @param x				x-coordinate
	 * @param y				y-coordinate
	 * @param width			Width of the window
	 * @param height		Height of the window
	 * @return				Sub image of the image object			
	 */
	public static WritableImage getSubImage(Image image, int x, int y, int width, int height) {
		return new WritableImage(image.getPixelReader(), x, y, width, height);
	}
}
