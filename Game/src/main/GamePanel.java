package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Data;
import data.Settings;
import object.TileManager;
import scroll_boundries.ScrollBoundryHandler;
import special.Image;
import special.Rectangle;
import special.Sound;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	public JFrame window;

	// Window options
	final int originaltileSize = 16; // Sprite size, 16x16
	public final int scale = 4;

	// Tile sizing and screen size
	public final int tileSize = originaltileSize * scale;
	final int maxScreenCol = 15;
	final int maxScreenRow = 11;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	public final int offsetX = screenWidth / 2 - tileSize / 2;
	public final int offsetY = screenHeight / 2 - (int) (tileSize * 1.5);
	public int windowWidth, windowHeight;
	public int scaledWidth, scaledHeight;
	public double widthRatio, heightRatio;
	public double scaleQuantity;
	public int translateX, translateY;
	
	public boolean paused = false;

	public int scrollX, scrollY, oldScrollX, oldScrollY = 0;

	// Increased size of screen from center so that images have time to render before being loaded on screen
	int screenSizeTileIncrease = 1;

	// Wanted FPS
	int FPS = 60;

	public double dt;

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	TileManager tileManager = new TileManager(this);
	Data worldData = new Data(this);
	public FileLoader files = new FileLoader();
	public Settings settings = new Settings();
	public ScrollBoundryHandler sbh = new ScrollBoundryHandler(this);
	public Player player = new Player(this, keyH, tileManager);
	Sound bgm = new Sound(this);

	public HashMap<String, Image> chars = new HashMap<String, Image>();
	public Rectangle screen = new Rectangle(0, 0, 0, 0); // changed every update

	public GamePanel(JFrame window) {

		this.window = window;
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.setBackground(Color.black);

	}

	public void startGameThread() {

		loadCharImages();
		worldData.loadWorldData(tileManager);

		scrollX = player.x;
		scrollY = player.y - tileSize;

		bgm.setFile(0);
		bgm.play();
		bgm.loop();

		gameThread = new Thread(this);
		gameThread.start();

	}

	@Override
	public void run() {

		int billion = 1000000000;
		long lastNanoTime = System.nanoTime();

		while (gameThread != null) {

			if ((System.nanoTime() - lastNanoTime) >= billion / FPS) {

				dt = (double) (System.nanoTime() - lastNanoTime) / 1000000000;
				// int calculatedFPS = (int) Math.round(1 / dt);

				update();
				repaint();

				lastNanoTime = System.nanoTime();

			}

		}

	}

	public void update() {

		windowWidth = window.getContentPane().getWidth();
		windowHeight = window.getContentPane().getHeight();

		widthRatio = (double) windowWidth / screenWidth;
		heightRatio = (double) windowHeight / screenHeight;

		scaleQuantity = Math.min(widthRatio, heightRatio);

		scaledWidth = (int) (screenWidth * scaleQuantity);
		scaledHeight = (int) (screenHeight * scaleQuantity);

		screen.width = screenWidth + screenSizeTileIncrease * 2 * tileSize;
		screen.height = screenHeight + screenSizeTileIncrease * 2 * tileSize;
		
		translateX = (int) (windowWidth - scaledWidth) / 2;
		translateY = (int) (windowHeight - scaledHeight) / 2;

		player.update();
		
		tileManager.update();

		if (oldScrollX != scrollX) {

			oldScrollX = scrollX;
			oldScrollY = scrollY;

		}

		int boundsX = 3 * tileSize;
		int boundsY = 2 * tileSize;

		// Scrolling box
		if (scrollY - player.y + tileSize >= boundsY) {

			scrollY = player.y - tileSize + boundsY;

		}

		if (player.y - scrollY - tileSize >= boundsY) {

			scrollY = player.y - tileSize - boundsY;

		}

		if (scrollX - player.x >= boundsX) {

			scrollX = player.x + boundsX;

		}

		if (player.x - scrollX >= boundsX) {

			scrollX = player.x - boundsX;

		}
		
		sbh.update();

		screen.x = scrollX - (screenWidth - tileSize) / 2 - screenSizeTileIncrease * tileSize;
		screen.y = scrollY - (screenHeight - tileSize * 3) / 2 - screenSizeTileIncrease * tileSize;

	}

	private void loadCharImages() {

		String availableChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		for (int i = 0; i < availableChars.length(); i++) {

			String currentChar = String.valueOf(availableChars.charAt(i));

			Image currentImage = files.getImage("/chars/upper/" + currentChar.toLowerCase() + ".png");

			chars.put(currentChar, currentImage);

		}

		availableChars = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < availableChars.length(); i++) {

			String currentChar = String.valueOf(availableChars.charAt(i));

			Image currentImage = files.getImage("/chars/lower/" + currentChar + ".png");

			chars.put(currentChar, currentImage);

		}

		availableChars = "0123456789!,'";

		for (int i = 0; i < availableChars.length(); i++) {

			String currentChar = String.valueOf(availableChars.charAt(i));

			Image currentImage = files.getImage("/chars/other/" + currentChar + ".png");

			chars.put(currentChar, currentImage);

		}

		Image currentImage = files.getImage("/chars/other/period.png");
		chars.put(".", currentImage);

		currentImage = files.getImage("/chars/other/slash.png");
		chars.put("/", currentImage);

		currentImage = files.getImage("/chars/other/colon.png");
		chars.put(":", currentImage);
		
		currentImage = files.getImage("/chars/other/e_acute.png");
		chars.put("é", currentImage);
		
		currentImage = files.getImage("/chars/other/single_quote_left.png");
		chars.put("‘", currentImage);
		
		currentImage = files.getImage("/chars/other/single_quote_right.png");
		chars.put("’", currentImage);
		
		currentImage = files.getImage("/chars/other/male.png");
		chars.put("♂", currentImage);
		
		currentImage = files.getImage("/chars/other/female.png");
		chars.put("♀", currentImage);
		
		currentImage = files.getImage("/chars/other/male.png");
		chars.put("♂", currentImage);
		
		currentImage = files.getImage("/chars/other/female.png");
		chars.put("♀", currentImage);
		
		currentImage = files.getImage("/chars/other/question_mark.png");
		chars.put("?", currentImage);
		
		currentImage = files.getImage("/chars/other/double_quote_left.png");
		chars.put("“", currentImage);
		
		currentImage = files.getImage("/chars/other/double_quote_right.png");
		chars.put("”", currentImage);

	}

	public void paintComponent(Graphics graphics) {

		Graphics2D g = (Graphics2D) graphics;

		g.translate(translateX, translateY);
		g.scale(scaleQuantity, scaleQuantity);
		g.translate(offsetX, offsetY);

		tileManager.draw(g, 0);

		player.draw(g);

		tileManager.draw(g, 1);

		if (player.textBox != null) {

			player.textBox.draw(g);

		}

		g.translate(- offsetX, - offsetY);
		g.scale(1 / scaleQuantity, 1 / scaleQuantity);
		g.translate(- translateX, - translateY);

		g.setColor(Color.black);

		if (widthRatio >= heightRatio) {

			g.fillRect(0, 0, translateX, windowHeight);
			g.fillRect(windowWidth - translateX, 0, translateX, windowHeight);

		} else {

			g.fillRect(0, 0, windowWidth, translateY);
			g.fillRect(0, windowHeight - translateY, windowWidth, translateY);

		}

	}

}