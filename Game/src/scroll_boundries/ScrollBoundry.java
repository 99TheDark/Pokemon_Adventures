package scroll_boundries;

public class ScrollBoundry {
	
	public int x, y, xTop, xBottom, yTop, yBottom;
	public String direction; 

	public ScrollBoundry(String direction, int position, int posTop, int posBottom) {
		
		if(direction == "left" || direction == "right") {
			
			this.direction = direction;
			this.x = position;
			this.yTop = posTop;
			this.yBottom = posBottom;
			
		} else if(direction == "up" || direction == "down") {
			
			this.direction = direction;
			this.y = position;
			this.xTop = posTop;
			this.xBottom = posBottom;
			
		}
		
	}
	
}
