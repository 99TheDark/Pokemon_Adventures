package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import main.GamePanel;
import special.Image;
import special.Structure;

public class TileManager {

	public ArrayList<Tile> tileArray = new ArrayList<Tile>();
	public ArrayList<Entity> entityArray = new ArrayList<Entity>();
	public HashMap<String, String> collisionMap = new HashMap<String, String>();
	public HashMap<String, Integer> zIndexMap = new HashMap<String, Integer>();
	public ArrayList<String> interactableTiles = new ArrayList<String>();

	GamePanel gp;

	public TileManager(GamePanel gp) {

		this.gp = gp;
		setupCollisionMap();
		setupInteractabilityMap();
		setupZIndexMap();

	}

	public void createTile(int x, int y, String name, String text) {

		String directory = "/tiles/" + name + ".png";

		if (name.contains("/")) {

			name = name.substring(name.indexOf("/") + 1, name.length());

		}

		Tile thisTile = new Tile(x, y, name, null, directory, gp);
		addCollisionType(thisTile);
		thisTile.text = text;

		tileArray.add(thisTile);

	}

	public void createEntity(int x, int y, String name, String text) {

		Entity npc = new Entity(gp, this, x, y, name);
		npc.interactable = true;
		npc.text = text;
		entityArray.add(npc);

	}

	public void fillTile(int x, int y, int width, int height, String name) {

		for (int iy = 0; iy < height; iy++) {

			for (int ix = 0; ix < width; ix++) {

				createTile(x + ix * gp.tileSize, y + iy * gp.tileSize, name, null);

			}

		}

	}

	public void createStructure(int x, int y, String name) {

		Structure struct = new Structure(name);

		for (int iy = 0; iy < struct.height; iy++) {

			for (int ix = 0; ix < struct.width; ix++) {

				createTile(x + ix * gp.tileSize, y + iy * gp.tileSize, name + "/" + struct.data[iy][ix], null);

			}

		}

	}

	public BufferedImage getSprite(String name) {

		return gp.files.getImage(name).image;

	}

	private void addCollisionType(Tile tile) {

		tile.collisionType = collisionMap.get(tile.tileName);

	}

	private void setupCollisionMap() {

		collisionMap.put("sign", "solid");

		for (int i = 4; i < 23; i++) {

			collisionMap.put("house_left_" + i, "solid");
			collisionMap.put("house_right_" + i, "solid");

		}

		for (int i = 6; i < 23; i++) {

			collisionMap.put("prof_house_" + i, "solid");

		}

		// Eventually make so it doesn't work when tree is above or something like that
		collisionMap.put("tree_3", "solid");
		collisionMap.put("tree_4", "solid");

		collisionMap.put("tree_5", "solid");
		collisionMap.put("tree_6", "solid");

	}

	public void setupInteractabilityMap() {

		interactableTiles.add("sign");

	}

	private void setupZIndexMap() {

		for (int i = 1; i < 5; i++) {

			zIndexMap.put("house_left_" + i, 1);
			zIndexMap.put("house_right_" + i, 1);

		}

		for (int i = 1; i < 7; i++) {

			zIndexMap.put("prof_house_" + i, 1);

		}

		for (int i = 1; i < 5; i++) {

			zIndexMap.put("tree_" + i, 1);

		}

	}

	public void update() {

		for (int i = 0; i < entityArray.size(); i++) {
			
			entityArray.get(i).update();

		}

	}

	private void drawEntities(Graphics2D g, int layer, String drawPart) {

		for (int i = 0; i < entityArray.size(); i++) {

			Entity curEntity = entityArray.get(i);

			int entityHeightTile = (int) (gp.tileSize * 1.5);
			int curImgX = curEntity.x - gp.scrollX;
			int curImgY = curEntity.y - entityHeightTile + gp.tileSize - gp.scrollY;

			if (gp.player.y < curEntity.y && drawPart == "top") {

				g.drawImage(curEntity.getAnimationImage(), curImgX, curImgY, gp.tileSize, entityHeightTile, null);

			} else if (gp.player.y >= curEntity.y && drawPart == "bottom") {

				g.drawImage(curEntity.getAnimationImage(), curImgX, curImgY, gp.tileSize, entityHeightTile, null);

			}

		}

	}

	public void draw(Graphics2D g, int layer) {

		// Draw entities (on top of player)
		if (layer == 1) {

			drawEntities(g, layer, "top");

		}

		// Draw layer
		for (int i = 0; i < tileArray.size(); i++) {

			Tile curTile = tileArray.get(i);

			int curLayer = 0;

			if (zIndexMap.containsKey(curTile.tileName)) {

				curLayer = zIndexMap.get(curTile.tileName);

			}

			if (curLayer == layer && gp.screen.colliding(curTile.hitbox)) {

				if (curTile.sprite != null) {

					g.drawImage(curTile.sprite, curTile.x - gp.scrollX, curTile.y - gp.scrollY, gp.tileSize, gp.tileSize, null);

				} else {

					Image tileImage = gp.files.getImage(curTile.spriteDirectory);

					curTile.sprite = tileImage.image;

				}

			}

		}

		if (layer == 0) {

			drawEntities(g, layer, "bottom");

		}

	}

}