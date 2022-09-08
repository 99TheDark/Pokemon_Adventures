package gui;

import java.awt.Graphics;

import main.GamePanel;

public class TextBox extends GUI {
	
	public Text text;

	public TextBox(GamePanel gp, String message) {
		
		super(gp, 0, 0, gp.files.getImage("/gui/text_box.png").image, 0, 0);
		
		// 234 x 42 image
		
		int scaler = (int) Math.floor(gp.screenWidth / 234);
				
		super.imageWidth = 234 * scaler;
		super.imageHeight = 42 * scaler;
		super.x = (gp.screenWidth - 234 * scaler) / 2;
		super.y = gp.screenHeight - super.x - super.imageHeight;
		
		int textPadding = scaler * 16;
		int maxLength = gp.screenWidth - textPadding * 2;
				
		text = new Text(textPadding - gp.offsetX, gp.offsetY + scaler * 10, message, maxLength, 2, gp);

	}
	
	public void draw(Graphics g) {
		
		super.draw(g);
		text.draw(g);
				
	}

}
