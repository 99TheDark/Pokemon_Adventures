package special;

import main.GamePanel;

public class Timer {
	
	GamePanel gp;
	public double time;
	public boolean timerComplete;
	
	public Timer(double time, GamePanel gp) {
		
		this.time = time;
		this.gp = gp;
		this.timerComplete = false;
				
	}
	
	public void update() {
		
		time -= gp.dt;
						
		if(time <= 0) {
			
			timerComplete = true;
						
		} else {
			
			timerComplete = false;
			
		}
		
	}

}
