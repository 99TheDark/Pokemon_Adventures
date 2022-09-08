package main;

import java.awt.Graphics2D;

import gui.Text;
import gui.TextBox;
import object.Entity;
import object.Tile;
import object.TileManager;
import special.Rectangle;
import special.Sound;

public class Player extends Entity {

	public String lastHeldDirection;

	GamePanel gp;
	KeyHandler keyH;
	TileManager tm;
	Sound sfx;

	boolean canMove = true;
	boolean wantsToMove = false;

	public TextBox textBox = null;

	boolean interactPressedLastFrame = false;

	public Player(GamePanel gp, KeyHandler keyH, TileManager tileManager) {

		super(gp, tileManager, 0, 0, "player");

		this.gp = gp;
		this.keyH = keyH;
		this.tm = tileManager;
		this.sfx = new Sound(gp);

		setDefaultValues();

	}

	public void setDefaultValues() {

		x = gp.tileSize * 12;
		y = gp.tileSize * 12;
		speed = gp.scale;
		direction = "down";
		lastHeldDirection = null;
		distToNextTile = 0;
		animationLength = 3;
		animationFrame = 0;
		hitbox = new Rectangle(0, 0, gp.tileSize, gp.tileSize);

	}

	public void update() {

		boolean interactKeyJustPressed = keyH.interactPressed && !interactPressedLastFrame;

		if (distToNextTile <= 0) {

			if (!gp.paused) {

				updateDirection();

				if (wantsToMove && !canMove) {

					updateDirection();

				}

				if (wantsToMove && canMove) {

					distToNextTile = gp.tileSize;

				}

				// Add text box
				boolean rightDirection = true;

				Tile frontTile = tileInFrontOfEntity();
				Entity frontEntity = entityInFrontOfEntity();

				boolean containsFrontTile = false;

				if (frontTile != null) {

					containsFrontTile = gp.tileManager.interactableTiles.contains(frontTile.tileName);

				}

				if (tileInFrontOfEntity() != null && frontTile.interactable && direction == "down") {

					rightDirection = false;

				}

				if ((frontEntity != null || (containsFrontTile && rightDirection && textBox == null)) && interactKeyJustPressed) {

					if (frontEntity != null) {

						textBox = new TextBox(gp, frontEntity.text);

						// Make NPC face you
						frontEntity.direction = flipDirection(direction);

					} else if (frontTile != null) {

						textBox = new TextBox(gp, frontTile.text);

					}

					if (textBox != null) {

						gp.paused = true;

						sfx.setFile(1);
						sfx.play();

					}

				}

			} else {

				Text text = textBox.text;

				if (interactKeyJustPressed && text.finalIndex <= text.charsRendered - 1) {

					if (text.wordLineCount > 2 && text.lineNumber + 3 <= text.wordLineCount) {

						textBox.text.goToNextLine();

					} else {

						textBox = null;
						gp.paused = false;

					}

					sfx.setFile(1);
					sfx.play();

				}

			}

		} else if (canMove) {

			updateMovement();

			animate();

		}

		interactPressedLastFrame = keyH.interactPressed;

		hitbox.x = x;
		hitbox.y = y;

	}

	private void updateDirection() {

		String lastHeldDirectionPrev = lastHeldDirection;

		lastHeldDirection = null;

		wantsToMove = false;

		if (keyH.upPressed && lastHeldDirectionPrev != "up") {

			direction = "up";
			lastHeldDirection = "up";
			wantsToMove = true;

		}

		if (keyH.downPressed && lastHeldDirectionPrev != "down") {

			direction = "down";
			lastHeldDirection = "down";
			wantsToMove = true;

		}

		if (keyH.leftPressed && lastHeldDirectionPrev != "left") {

			direction = "left";
			lastHeldDirection = "left";
			wantsToMove = true;

		}

		if (keyH.rightPressed && lastHeldDirectionPrev != "right") {

			direction = "right";
			lastHeldDirection = "right";
			wantsToMove = true;

		}

		canMove = canMove(direction);

	}

	public void draw(Graphics2D g) {

		int playerHeightTile = (int) (gp.tileSize * 1.5);

		g.drawImage(getAnimationImage(), x - gp.scrollX, y + playerHeightTile - gp.tileSize * 2 - gp.scrollY, gp.tileSize, playerHeightTile, null);

	}

}
