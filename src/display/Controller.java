package display;

import android.graphics.Canvas;
import android.graphics.Color;

public class Controller extends Thread {

	boolean alive = true;

	private double delta;
	private long lastUpdateTime = System.currentTimeMillis();
	private long currentTime = System.currentTimeMillis();

	private Screen scr;

	public Controller(Screen scr) {
		this.scr = scr;
	}

	public void run() {

		while (alive) {

			delta();
			scr.map.ghost.action(delta);
			scr.map.pacman.action(delta);
			
			Canvas c = null;
			try {
				c = scr.getHolder().lockCanvas();
				synchronized (scr.getHolder()) {
					if (c != null) {
						/* insert voodoo background calculations here */
						draw(c);
					}
				}
			} finally {
				if (c != null) {
					scr.getHolder().unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void draw(Canvas c) {
		c.drawColor(Color.BLACK);
		scr.map.draw(c);
	}

	private void delta() {
		currentTime = System.currentTimeMillis();
		delta = (currentTime - lastUpdateTime) / 40.;
		lastUpdateTime = currentTime;
	}

}
