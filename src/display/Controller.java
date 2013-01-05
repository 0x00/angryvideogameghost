package display;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Controller extends Thread{
	
	boolean alive = true;

	private double delta;
	private long lastUpdateTime = System.currentTimeMillis();
	private long currentTime = System.currentTimeMillis();

	private Screen scr;
	
	public Controller(Screen scr){
		this.scr = scr;
	}
	

	public void run() {

		while (alive) {

			delta();

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
		Paint paint = new Paint();
		RectF r = new RectF();
		r.top = 0;
		r.left = 0;
		r.bottom = c.getHeight();
		r.right = c.getWidth();
		paint.setColor(Color.rgb(0, 0, (int) (200*Math.random())));
		
		c.drawRect(r, paint);
		
		scr.block.draw(c);
		
	}


	private void delta() {
		currentTime = System.currentTimeMillis();
		delta = (currentTime - lastUpdateTime) / 40.;
		lastUpdateTime = currentTime;
	}

}
