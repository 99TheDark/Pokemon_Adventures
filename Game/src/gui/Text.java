package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.GamePanel;
import special.Image;
import special.Timer;

public class Text {

	int x, y;
	String message;
	GamePanel gp;
	public int charsRendered = 0;
	int maxLength;
	double timePerChar = 1.0 / 60;
	Timer charTimer;
	Timer arrowClock;
	double arrowTime;
	int arrowOffset;
	public int wordLineCount = 0;
	public int lineNumber;
	public int maxLineLength;
	int wordX, wordY;
	public int finalIndex;
	boolean nextPageCharInRange = false;

	public Text(int x, int y, String message, int maxWordLength, int maxLineLength, GamePanel gp) {

		this.x = x;
		this.y = y;
		this.message = message;
		this.maxLength = maxWordLength;
		this.gp = gp;
		this.maxLineLength = maxLineLength;

		lineNumber = 0;
		arrowTime = 1.0 / 6;
		charTimer = new Timer(timePerChar, gp);
		arrowClock = new Timer(arrowTime, gp);
		arrowOffset = 0;

	}

	public void draw(Graphics g) {

		wordLineCount = 1;

		String[] words = message.split(" ");

		wordX = 0;
		wordY = - lineNumber * 15 * gp.scale;

		// finalWordX and finalWordY for wordX and wordY at the end of the fitted page.

		int finalWordX = 0;
		int finalWordY = 0;

		int index = 0;
		finalIndex = 0;

		// Loop through all words (split between spaces as seen in String[] words =
		// message.split…)

		nextPageCharInRange = false;

		for (int i = 0; i < words.length; i++) {

			int wordLength = calculateWidth(words[i]);
			boolean splitWord = false;

			// Include red arrow in width

			int nextWordLength = 0;

			if (i != words.length - 1) {

				nextWordLength = calculateWidth(words[i + 1]);

			}

			if (wordX + wordLength + nextWordLength + 3 * gp.scale > maxLength) {

				wordLength += 8 * gp.scale;

			}

			// Calculate word length and if it should be split (aka longer than a whole
			// line)

			if (wordX + wordLength > maxLength) {

				if (wordLength > maxLength) {

					splitWord = true;

				} else {

					nextLine();

				}

			}

			for (int j = 0; j < words[i].length(); j++) {

				String curChar = String.valueOf(words[i].charAt(j));

				// Skip | characters as that means next line and doesn't have a sprite
				// Skip & characters as that means next page and doesn't have a sprite

				if (curChar.equals("|")) {

					nextLine();

				} else if (curChar.equals("&")) {

					if (wordY <= 15 * gp.scale && wordY >= 0) {

						nextPageCharInRange = true;

					}

					nextLine();

				} else if (gp.chars.containsKey(curChar)) {

					Image imageClass = gp.chars.get(curChar);
					BufferedImage image = imageClass.image;
					int imgWidth = imageClass.width * gp.scale;
					int imgHeight = imageClass.height * gp.scale;

					if (index <= charsRendered && wordY <= 15 * gp.scale && wordY >= 0) {

						g.drawImage(image, x + wordX, y + wordY, imgWidth, imgHeight, null);

					}

					wordX += imgWidth;

					index++;

				}

				if (wordY <= 15 * gp.scale) {

					finalWordX = wordX;
					finalWordY = wordY;
					finalIndex = index;

				}

				if (splitWord && wordX > maxLength) {

					nextLine();

				}

			}

			if (i != words.length - 1) {

				// Space width
				wordX += 3 * gp.scale;

			}

			index++;

		}

		Image redArrow = gp.files.getImage("/chars/other/arrow.png");

		int imgWidth = redArrow.width * gp.scale;
		int imgHeight = redArrow.height * gp.scale;

		if (finalIndex <= charsRendered - 1) {

			// Red arrow moving animation

			arrowClock.update();

			if (arrowClock.timerComplete) {

				arrowClock.time = arrowTime;
				arrowOffset = (arrowOffset + 1) % 4;

			}

			// Draw arrow

			if (wordY - finalWordY != 0) {

				g.drawImage(redArrow.image, x + finalWordX + gp.scale, y + finalWordY + arrowOffset * gp.scale, imgWidth, imgHeight, null);

			}

		} else {

			arrowOffset = 0;

			charTimer.update();

			if (charTimer.timerComplete) {

				charsRendered++;
				charTimer.time = timePerChar;

			}

		}

	}

	private void nextLine() {

		wordX = 0;
		wordY += 15 * gp.scale;
		wordLineCount++;

	}

	public void goToNextLine() {

		lineNumber++;

		if (nextPageCharInRange) {

			lineNumber++;

		}

	}

	public int calculateWidth(String word) {

		int totalWidth = 0;

		for (int i = 0; i < word.length(); i++) {

			String curChar = String.valueOf(word.charAt(i));

			if (gp.chars.containsKey(curChar)) {

				int imgWidth = gp.chars.get(curChar).width * gp.scale;

				totalWidth += imgWidth;

			}

		}

		return totalWidth;

	}

}