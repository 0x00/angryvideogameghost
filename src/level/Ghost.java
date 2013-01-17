package level;

import java.util.List;

import map.Landscape;
import map.Navi;
import map.Navi.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Ghost {
	public float x = 10;
	public float y = 15;

	public float toX = x;
	public float toY = y;

	public float sizeW;
	public float sizeH;

	Bitmap gfx;
	public Rect rect;
	public RectF target;
	
	Landscape landscape;

	public Ghost(Bitmap gfx, Landscape landscape) {
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());
		
		this.landscape = landscape;

	}

	public void draw(Canvas c) {

		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + 3, Y + 3, X + sizeW - 3, Y + sizeH - 3);

		c.drawBitmap(gfx, rect, target, null);
	}

	public boolean moving = false;
	private Point localDirection;
	
	public void action(double delta){
		int tx = (int) (x + 0.5);
		int ty = (int) (y + 0.5);

		int ttoX = (int) (toX + 0.5);
		int ttoY = (int) (toY + 0.5);

		if (Math.abs(tx - ttoX) < 1 && Math.abs(ty - ttoY) < 1) {
			/* Log.d("path", "stop"); */
		} else if (moving) {
			Log.d("path", "direct movement");
			move(delta);

		} else if (!moving) {
			Log.d("path", "path planning");
			List<Point> path = new Navi(landscape).findPath(tx, ty, ttoX,
					ttoY);

			if (path.size() > 0) {
				Log.d("block path", tx + "," + ty + " " + ttoX + "," + ttoY
						+ " " + path);
				Point direction = path.get(0);
				moveTo(direction, delta);
			}
		}
	}

	private void move(double delta) {
		float dx = 0;
		float dy = 0;

		if (localDirection.x < x) {
			dx = -1;
		}
		if (localDirection.x > x) {
			dx = +1;
		}
		if (localDirection.y < y) {
			dy = -1;
		}
		if (localDirection.y > y) {
			dy = +1;
		}

		x += 0.2 * delta * dx;
		y += 0.2 * delta * dy;
		
		if(Math.abs(x-localDirection.x)<0.5 && Math.abs(y-localDirection.y)<0.5){
			x = localDirection.x;
			y = localDirection.y;
			moving = false;
		}
	}

	private void moveTo(Point direction, double delta) {
		this.localDirection = direction;
		moving = true;
		move(delta);
	}
}
