package isom3320.project.game.multimedia;

import java.io.File;

import javafx.scene.image.Image;

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
}
