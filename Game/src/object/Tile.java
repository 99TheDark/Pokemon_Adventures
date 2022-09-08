package object;

import java.awt.image.BufferedImage;
import java.util.Arrays;

import main.GamePanel;
import special.Rectangle;

public class Tile {

	int x, y;
	public BufferedImage sprite;
	public String tileName;
	public String collisionType = null;
	public Rectangle hitbox;
	public String spriteDirectory;
	public boolean interactable;
	GamePanel gp;
	public String text = null;

	public Tile(int x, int y, String name, BufferedImage image, String imageDirectory, GamePanel gp) {

		this.x = x;
		this.y = y;
		this.tileName = name;
		this.sprite = image;
		this.spriteDirectory = imageDirectory;
		this.interactable = isInteractable(name);
		this.gp = gp;
		this.hitbox = new Rectangle(x, y, gp.tileSize, gp.tileSize);

	}

	private boolean isInteractable(String name) {

		String[] interactableTiles = {"sign"};

		return Arrays.asList(interactableTiles).contains(name);

	}

}
