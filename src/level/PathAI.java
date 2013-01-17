package level;

import java.util.List;

import map.Landscape;
import map.Navi;
import map.Navi.Point;
import android.util.Log;

public class PathAI extends Drawable {

	public float toX = x;
	public float toY = y;

	Landscape landscape;

	public boolean moving = false;
	private Point localDirection;

	public void action(double delta) {
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
			List<Point> path = new Navi(landscape).findPath(tx, ty, ttoX, ttoY);

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

		if (Math.abs(x - localDirection.x) < 0.5
				&& Math.abs(y - localDirection.y) < 0.5) {
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
