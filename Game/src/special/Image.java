package special;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Image {

	public BufferedImage image = null;
	public int width = 0;
	public int height = 0;
	public String name = null;

	public Image(String fileLocation) {

		try {

			this.image = ImageIO.read(getClass().getResourceAsStream(fileLocation));

			String[] splitLocation = fileLocation.split("/");
			String imageFileName = splitLocation[splitLocation.length - 1];
			String imageName = imageFileName.substring(0, imageFileName.length() - 4);
			this.name = imageName;

		} catch (Exception e) {

			e.printStackTrace();

		}

		if (image != null) {

			this.width = image.getWidth();
			this.height = image.getHeight();

		}

	}

}
