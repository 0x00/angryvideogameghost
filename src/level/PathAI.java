package level;

import java.util.List;

import map.Landscape;
import map.Navi;
import map.Navi.Point;
import android.os.Debug;
import android.util.Log;

public class PathAI extends Drawable {

	public Entity avoid;

	public float toX = -1;
	public float toY = -1;

	Landscape landscape;

	public boolean moving = false;
	private Point localDirection;

	public void action(double delta) {

		if (toX == -1)
			return;

		int tx = (int) (x + 0.5);
		int ty = (int) (y + 0.5);

		int ttoX = (int) (toX + 0.5);
		int ttoY = (int) (toY + 0.5);

		if (Math.abs(x - toX) < 0.1 && Math.abs(y - toY) < 0.1) {
			/* Log.d("path", "stop"); */
		} else if (moving) {
			Log.d("path", "direct movement");
			move(delta);

		} else if (!moving) {
			Log.d("path", "path planning");
			List<Point> path;
			if (avoid == null) {
				path = new Navi(landscape).findPath(tx, ty, ttoX, ttoY);
			} else {
				
				Log.d("avoid",avoid.x+" "+avoid.y);
				path = new Navi(landscape, (int) avoid.x, (int) avoid.y)
						.findPath(tx, ty, ttoX, ttoY);
			}

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

		x += 0.1 * delta * dx;
		y += 0.1 * delta * dy;

		if (Math.abs(x - localDirection.x) <= 0.1)
			x = localDirection.x;

		if (Math.abs(y - localDirection.y) <= 0.1)
			y = localDirection.y;

		if (Math.abs(x - localDirection.x) <= 0.1
				&& Math.abs(y - localDirection.y) <= 0.1) {
			moving = false;
		}
	}

	private void moveTo(Point direction, double delta) {
		this.localDirection = direction;
		moving = true;
		move(delta);
	}
}
