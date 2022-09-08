package object;

import java.awt.image.BufferedImage;

import main.GamePanel;
import special.Rectangle;
import special.Timer;

public class Entity {

	public int x, y, speed, distToNextTile, animationLength, animationFrame;
	public String name, direction;

	public BufferedImage[] up = new BufferedImage[3];
	public BufferedImage[] down = new BufferedImage[3];
	public BufferedImage[] left = new BufferedImage[3];
	public BufferedImage[] right = new BufferedImage[3];

	public boolean interactable = false;
	public String text = null;

	public boolean justMoved = false;

	public Rectangle hitbox;

	public Timer movementTimer;
	public double movementDelay = 2.0;

	GamePanel gp;
	TileManager tm;

	public Entity(GamePanel gp, TileManager tm, int x, int y, String name) {

		this.x = x;
		this.y = y;
		this.speed = gp.scale;
		this.distToNextTile = 0;
		this.animationFrame = 0;
		this.animationLength = 3;
		this.name = name;
		this.gp = gp;
		this.tm = tm;
		this.direction = "down";
		this.hitbox = new Rectangle(x, y, gp.tileSize, gp.tileSize);
		this.movementTimer = new Timer(movementDelay, gp);

		try {

			String dir_start = "/entity/" + name + "/" + name + "_";
			up[0] = gp.files.getImage(dir_start + "up_1.png").image;
			up[1] = gp.files.getImage(dir_start + "up_2.png").image;
			up[2] = gp.files.getImage(dir_start + "up_3.png").image;
			down[0] = gp.files.getImage(dir_start + "down_1.png").image;
			down[1] = gp.files.getImage(dir_start + "down_2.png").image;
			down[2] = gp.files.getImage(dir_start + "down_3.png").image;
			left[0] = gp.files.getImage(dir_start + "left_1.png").image;
			left[1] = gp.files.getImage(dir_start + "left_2.png").image;
			left[2] = gp.files.getImage(dir_start + "left_3.png").image;
			right[0] = gp.files.getImage(dir_start + "right_1.png").image;
			right[1] = gp.files.getImage(dir_start + "right_2.png").image;
			right[2] = gp.files.getImage(dir_start + "right_3.png").image;

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public void animate() {

		double animationSpacing = gp.tileSize / animationLength;

		for (int i = 0; i < animationLength; i++) {

			if (animationSpacing * (i + 1) >= distToNextTile && distToNextTile >= animationSpacing * i) {

				animationFrame = i % animationLength;

				break;

			}

		}

	}

	public BufferedImage getAnimationImage() {

		BufferedImage image = null;

		switch (direction) {

		case "up":
			image = up[animationFrame];
			break;

		case "down":
			image = down[animationFrame];
			break;

		case "left":
			image = left[animationFrame];
			break;

		case "right":
			image = right[animationFrame];
			break;

		}

		return image;

	}

	public Tile tileInFrontOfEntity() {

		int incX = getIncrement("x", direction);
		int incY = getIncrement("y", direction);

		int index = - 1;

		for (int i = 0; i < tm.tileArray.size(); i++) {

			boolean atX = hitbox.x + incX == tm.tileArray.get(i).hitbox.x;
			boolean atY = hitbox.y + incY == tm.tileArray.get(i).hitbox.y;

			if (atX && atY && tm.tileArray.get(i).collisionType == "solid") {

				index = i;
				break;

			}

		}

		if (index >= 0) {

			return tm.tileArray.get(index);

		} else {

			return null;

		}

	}

	public Entity entityInFrontOfEntity() {

		int incX = getIncrement("x", direction);
		int incY = getIncrement("y", direction);

		int index = - 1;

		for (int i = 0; i < tm.entityArray.size(); i++) {

			boolean atX = hitbox.x + incX == tm.entityArray.get(i).hitbox.x;
			boolean atY = hitbox.y + incY == tm.entityArray.get(i).hitbox.y;

			if (atX && atY) {

				index = i;
				break;

			}

		}

		if (index >= 0) {

			return tm.entityArray.get(index);

		} else {

			return null;

		}

	}

	public boolean canMove(String dir) {

		// Increases in x and y for direction
		int incX = getIncrement("x", dir);
		int incY = getIncrement("y", dir);

		Rectangle movedHitbox = new Rectangle(hitbox.x + incX, hitbox.y + incY, hitbox.width, hitbox.height);

		boolean isColliding = false;
		boolean outOfBounds = true;

		for (int i = 0; i < tm.tileArray.size(); i++) {

			if (movedHitbox.colliding(tm.tileArray.get(i).hitbox)) {

				if (tm.tileArray.get(i).collisionType == "solid") {

					isColliding = true;

					break;

				}

				outOfBounds = false;

			}

		}

		int myIndex = - 1;

		if (tm.entityArray.contains(this)) {

			myIndex = tm.entityArray.indexOf(this);

		}

		for (int i = 0; i < tm.entityArray.size(); i++) {

			if (i != myIndex) {

				if (movedHitbox.colliding(tm.entityArray.get(i).hitbox)) {

					isColliding = true;

					break;

				}

			}

		}

		return !isColliding && !outOfBounds;

	}

	public int getIncrement(String xory, String dir) {

		int incX = 0;
		int incY = 0;

		if (dir == "up") {

			incY = - gp.tileSize;

		} else if (dir == "down") {

			incY = gp.tileSize;

		} else if (dir == "left") {

			incX = - gp.tileSize;

		} else if (dir == "right") {

			incX = gp.tileSize;

		}

		if (xory == "x") {

			return incX;

		} else if (xory == "y") {

			return incY;

		} else {

			return 0;

		}

	}

	public String flipDirection(String dir) {

		if (dir == "up") {

			return "down";

		}

		if (dir == "down") {

			return "up";

		}

		if (dir == "left") {

			return "right";

		}

		if (dir == "right") {

			return "left";

		}

		return null;

	}

	public void updateMovement() {

		distToNextTile -= speed;

		if (direction == "up") {

			y -= speed;

		}

		if (direction == "down") {

			y += speed;

		}

		if (direction == "left") {

			x -= speed;

		}

		if (direction == "right") {

			x += speed;

		}

	}

	public void update() {
		
		hitbox.x = x;
		hitbox.y = y;

		if (!gp.paused) {

			movementTimer.update();

			if (movementTimer.timerComplete) {

				movementTimer.time = movementDelay;

				int incX = getIncrement("x", direction);
				int incY = getIncrement("y", direction);

				Rectangle movedHitbox = new Rectangle(hitbox.x + incX, hitbox.y + incY, hitbox.width, hitbox.height);

				if (justMoved) {

					direction = flipDirection(direction);
					justMoved = false;

				} else if (canMove(direction) && !movedHitbox.colliding(gp.player.hitbox)) {

					distToNextTile = gp.tileSize;
					justMoved = true;

				}

			}

			if (distToNextTile != 0) {
				
				updateMovement();
				animate();

			}

		}

	}

}
