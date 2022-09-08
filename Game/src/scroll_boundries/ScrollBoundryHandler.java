package scroll_boundries;

import java.util.ArrayList;

import main.GamePanel;

public class ScrollBoundryHandler {

	public ArrayList<ScrollBoundry> scrollBoundries = new ArrayList<ScrollBoundry>();

	GamePanel gp;

	boolean lockLeft, lockRight, lockUp, lockDown = false;

	public ScrollBoundryHandler(GamePanel gp) {

		this.gp = gp;

	}

	public void createBoundry(String direction, int position, int top, int bottom) {

		ScrollBoundry boundry = new ScrollBoundry(direction, position, top, bottom);

		scrollBoundries.add(boundry);

	}

	public void update() {

		for (int i = 0; i < scrollBoundries.size(); i++) {

			int newX = gp.scrollX;
			int newY = gp.scrollY;

			ScrollBoundry curBoundry = scrollBoundries.get(i);

			boolean betweenX = gp.scrollX > curBoundry.xTop && gp.scrollX < curBoundry.xBottom;
			boolean betweenY = gp.scrollY > curBoundry.yTop && gp.scrollY < curBoundry.yBottom;

			boolean pastLeft = gp.scrollX < curBoundry.x && gp.oldScrollX >= curBoundry.x;
			boolean pastRight = gp.scrollX > curBoundry.x && gp.oldScrollX <= curBoundry.x;
			boolean pastUp = gp.scrollY < curBoundry.y && gp.oldScrollY >= curBoundry.y;
			boolean pastDown = gp.scrollY > curBoundry.y && gp.oldScrollY <= curBoundry.y;

			if (curBoundry.direction == "left") {

				if (pastLeft && betweenY || lockLeft) {

					newX = curBoundry.x;
					lockLeft = true;

				}

				if (gp.scrollX >= curBoundry.x) {

					lockLeft = false;

				}

			}

			if (curBoundry.direction == "right") {

				if (pastRight && betweenY || lockRight) {

					newX = curBoundry.x;
					lockRight = true;

				}

				if (gp.scrollX <= curBoundry.x) {

					lockRight = false;

				}

			}
			
			if (curBoundry.direction == "up") {

				if (pastUp && betweenX || lockUp) {

					newY = curBoundry.y;
					lockUp = true;

				}

				if (gp.scrollY >= curBoundry.y) {

					lockUp = false;

				}

			}
			
			if (curBoundry.direction == "down") {

				if (pastDown && betweenX || lockDown) {

					newY = curBoundry.y;
					lockDown = true;

				}

				if (gp.scrollY <= curBoundry.y) {

					lockDown = false;

				}

			}

			if (newX != gp.scrollX || newY != gp.scrollY) {

				gp.scrollX = newX;
				gp.scrollY = newY;
				gp.oldScrollX = newX;
				gp.oldScrollY = newY;

			}

		}

	}

}