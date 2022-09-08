package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class GUI {

	GamePanel gp;
	public int x, y, imageWidth, imageHeight;
	public BufferedImage image;
	public boolean display = true;

	public GUI(GamePanel gp, int x, int y, BufferedImage image, int imageWidth, int imageHeight) {

		this.gp = gp;
		this.x = x;
		this.y = y;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.image = image;

	}
	
	public void draw(Graphics g) {

		if (display) {

			g.drawImage(image, x - gp.offsetX, y - gp.offsetY, imageWidth, imageHeight, null);

		}

	}

	public void show() {

		display = true;

	}

	public void hide() {

		display = false;

	}

}
