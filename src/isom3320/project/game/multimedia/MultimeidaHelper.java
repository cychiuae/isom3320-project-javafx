package isom3320.project.game.multimedia;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;

public class MultimeidaHelper {
	public static final String resourcesDir = "Resources/";
	
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
	
	public static Media getMusicByName(String musicName) {
		Media music = null;
		
		File musicFile = new File(resourcesDir + musicName);
		
		if(musicFile.isFile()) {
			music = new Media(musicFile.toURI().toString());
		}
		
		return music;
	}
	
	public static WritableImage getSubImage(Image image, int x, int y, int width, int height) {
		return new WritableImage(image.getPixelReader(), x, y, width, height);
	}
}
