package level;

import map.Navi.Point;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

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

	public Ghost(Bitmap gfx) {
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

	}

	public void draw(Canvas c) {

		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + 3, Y + 3, X + sizeW - 3, Y + sizeH - 3);

		c.drawBitmap(gfx, rect, target, null);
	}

	public boolean moving = false;
	private Point direction;

	public void move(double delta) {
		float dx = 0;
		float dy = 0;

		if (direction.x < x) {
			dx = -1;
		}
		if (direction.x > x) {
			dx = +1;
		}
		if (direction.y < y) {
			dy = -1;
		}
		if (direction.y > y) {
			dy = +1;
		}

		x += 0.2 * delta * dx;
		y += 0.2 * delta * dy;
		
		if(Math.abs(x-direction.x)<0.5 && Math.abs(y-direction.y)<0.5){
			moving = false;
		}
	}

	public void moveTo(Point direction, double delta) {
		this.direction = direction;
		moving = true;
		move(delta);
	}
}
