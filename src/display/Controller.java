package display;

import java.util.List;

import map.Navi;
import map.Navi.Point;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import data.Infos;

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

			int x = (int) (Infos.player.x + 0.5);
			int y = (int) (Infos.player.y + 0.5);

			int toX = (int) (Infos.player.toX + 0.5);
			int toY = (int) (Infos.player.toY + 0.5);

			if (Math.abs(x - toX) < 1 && Math.abs(y - toY) < 1) {
				Log.d("path", "stop");
				Infos.player.x = toX;
				Infos.player.y = toY;
			} else if (Infos.player.moving) {
				Log.d("path", "direct movement");
				Infos.player.move(delta);

			} else if (!Infos.player.moving) {
				Log.d("path", "path planning");
				List<Point> path = new Navi(scr.map.map).findPath(x, y, toX,
						toY);

				if (path.size() > 0) {
					Log.d("block path", x + "," + y + " " + toX + "," + toY
							+ " " + path);
					Point direction = path.get(0);
					Infos.player.moveTo(direction, delta);
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
