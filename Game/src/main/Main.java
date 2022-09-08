package main;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Pokemon Game");
		window.setMinimumSize(new Dimension(360, 264 + 28)); // Normal size should be 360, 264 + 28
		
		GamePanel gamePanel = new GamePanel(window);
		window.add(gamePanel);

		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

		gamePanel.startGameThread();

	}

}