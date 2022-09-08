package data;

import main.GamePanel;
import object.TileManager;

public class Data {

	GamePanel gp;

	public Data(GamePanel gp) {

		this.gp = gp;

	}

	public void loadWorldData(TileManager tm) {
		
		gp.sbh.createBoundry("left", 448, 128, 1408);
		gp.sbh.createBoundry("right", 1028, 128, 1408);
		gp.sbh.createBoundry("up", 256, 448, 1028);
		gp.sbh.createBoundry("down", 1152, 448, 1028);
		
		fillTileScaled(-2, -3, 28, 2, "grass", tm);
		fillTileScaled(-2, -1, 28, 27, "grass", tm);
		
		for(int i = -1; i < 12; i++) {
			
			createStructureScaled(0, i * 2, "tree", tm);
			createStructureScaled(22, i * 2, "tree", tm);
			
		}
		
		for(int i = 0; i < 10; i++) {
			
			createStructureScaled(2 + i * 2, 22, "tree", tm);
			
			if(i != 5) {
				
				createStructureScaled(2 + i * 2, -2, "tree", tm);
				createStructureScaled(2 + i * 2, 0, "tree", tm);
				createStructureScaled(2 + i * 2, 2, "tree", tm);
				
			}
			
		}
		
		createStructureScaled(2, 4, "tree", tm);
		createStructureScaled(20, 4, "tree", tm);
		createStructureScaled(2, 20, "tree", tm);
		createStructureScaled(18, 20, "tree", tm);
		createStructureScaled(20, 20, "tree", tm);
		createStructureScaled(20, 18, "tree", tm);
		
		createStructureScaled(4, 7, "house_left", tm);
		createStructureScaled(15, 7, "house_right", tm);
		createStructureScaled(5, 15, "prof_house", tm);
		
		createTileScaled(9, 11, "sign", tm, "Hey, I'm a sign! Check me out!");
		createTileScaled(14, 11, "sign", tm, "Hello! This is a text box. It contains lots of useful information.");
		createTileScaled(8, 20, "sign", tm, "My name is sign.");
		createTileScaled(17, 16, "sign", tm, "What is the purpose of life? Why do I even exist?!|Oh hi there! Have a great day!");
		createTileScaled(4, 5, "sign", tm, "Nice job finding me. It's lonely back here.");
		createTileScaled(21, 7, "sign", tm, "Hey there.|I'm a sign. I was once young like you. Happy like you. But not for long. They took everything from me. My friends, my family, my happiness and faith.|They took it all until I had nothing left. Yet here I am, powerless, unable to move in the corner of some small town where nobody will find me.|They took everything from me, and I'll make them&Hey wait, are you even listening?|You aren't?|You think I'm annoying? How dare you?!");
		createTileScaled(12, 1, "sign", tm, "It's dangerous to go into the wild without any POKéMON!");
		createTileScaled(13, 1, "sign", tm, "It's dangerous to go into the wild without any POKéMON!");
						
		createTileScaled(7, 12, "path", tm, null);
		createTileScaled(16, 12, "path", tm, null);
		createTileScaled(9, 20, "path", tm, null);
		
		createTileScaled(5, 13, "flower", tm, null);
		createTileScaled(3, 12, "flower", tm, null);
		createTileScaled(2, 11, "flower", tm, null);
		createTileScaled(2, 13, "flower", tm, null);
		createTileScaled(18, 13, "flower", tm, null);
		createTileScaled(20, 13, "flower", tm, null);
		createTileScaled(21, 12, "flower", tm, null);
		createTileScaled(20, 11, "flower", tm, null);
		createTileScaled(21, 10, "flower", tm, null);
		createTileScaled(7, 20, "flower", tm, null);
		createTileScaled(6, 20, "flower", tm, null);
		createTileScaled(5, 20, "flower", tm, null);
		createTileScaled(7, 21, "flower", tm, null);
		createTileScaled(6, 21, "flower", tm, null);
		createTileScaled(5, 21, "flower", tm, null);
		
		createEntityScaled(14, 16, "tech", "If you use a PC, you can store items and POKéMON.&The power of science is staggering!", tm);

	}

	private void fillTileScaled(int x, int y, int width, int height, String tile, TileManager tm) {

		tm.fillTile(x * gp.tileSize, y * gp.tileSize, width, height, tile);

	}

	private void createTileScaled(int x, int y, String tile, TileManager tm, String text) {

		tm.createTile(x * gp.tileSize, y * gp.tileSize, tile, text);

	}
	
	private void createEntityScaled(int x, int y, String name, String text, TileManager tm) {
		
		tm.createEntity(x * gp.tileSize, y * gp.tileSize, name, text);
		
	}
	
	private void createStructureScaled(int x, int y, String name, TileManager tm) {
		
		tm.createStructure(x * gp.tileSize, y * gp.tileSize, name);
		
	}

}