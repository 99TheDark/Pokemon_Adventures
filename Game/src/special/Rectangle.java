package special;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle {

	public int x, y, width, height;

	public Rectangle(int x, int y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}

	public void draw(Graphics g, Color color) {

		g.setColor(color);
		g.fillRect(x, y, width, height);

	}

	public boolean colliding(Rectangle rect) {

		boolean xColliding = this.x < rect.x + rect.width && this.x + this.width > rect.x;
		boolean yColliding = this.y < rect.y + rect.height && this.height + this.y > rect.y;

		return xColliding && yColliding;

	}

}