package level;

import java.util.List;

import map.Landscape;
import map.Navi;
import map.Point;

public class PathAI extends Drawable {

	public Entity avoid;
	public boolean avoidDanger = true;

	public float toX = -1;
	public float toY = -1;

	Landscape landscape;

	public boolean moving = false;
	Point localDirection;

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
			List<Point> path;
			if (avoid == null || !avoidDanger) {
				path = new Navi(landscape).findPath(tx, ty, ttoX, ttoY);
			} else {

				Navi nav = new Navi(landscape, (int) avoid.x, (int) avoid.y);
				nav.avoider = 90;
				path = nav.findPath(tx, ty, ttoX, ttoY);
			}

			if (path.size() > 0) {
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
