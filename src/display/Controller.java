package display;

import data.Infos;
import map.Navi;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

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

			
			
			int x = scr.map.transferX(Infos.player.x);
			int y = scr.map.transferY(Infos.player.y);

			
			int toX = scr.map.transferX(Infos.player.toX);
			int toY = scr.map.transferY(Infos.player.toY);
			
			String path = new Navi(scr.map.map).findPath(x,y,toX,toY);
			
			
			
			
			if(path.length()>0){
				
				Log.d("path", x+","+y+" "+toX+","+toY+" "+path);
				
				char direction = path.charAt(0);
				switch (direction) {
				case 'n':
					Infos.player.y -= 0.01*delta;
					break;
				case 's':
					Infos.player.y += 0.01*delta;
					break;
				case 'w':
					Infos.player.x -=0.01*delta;
					break;
				case 'e':
					Infos.player.x += 0.01*delta;
					break;

				default:
					break;
				}
			}
			
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
