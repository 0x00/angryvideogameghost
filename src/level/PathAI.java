package level;

import java.util.List;

import android.util.Log;

import map.Landscape;
import map.Navi;
import map.Point;

public class PathAI extends Drawable {

	public Entity avoid;
	public boolean avoidDanger = true;

	public float toX = -1;
	public float toY = -1;

	Landscape landscape;
	Navi nav;

	public PathAI(Landscape landscape) {
		nav = new Navi(landscape);
		this.landscape = landscape;
	}

	public boolean moving = false;
	Point localDirection;
	public double speed = 0.1;
	public List<Point> path;

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
			move(delta);

		} else if (!moving) {
			Log.d("pathplan", "pathplan " + tx + " " + ty + ":=> " + ttoX + " "
					+ ttoY);
			if (avoid == null || !avoidDanger) {
				path = nav.findPath(new Point(tx, ty), new Point(ttoX, ttoY),
						null);
			} else {
				nav.avoider = 90;
				path = nav.findPath(new Point(tx, ty), new Point(ttoX, ttoY),
						new Point((int) avoid.x, (int) avoid.y));
			}

			if (path.size() > 1) {
				Point direction = path.get(1);
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

		x += speed * delta * dx;
		y += speed * delta * dy;

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
