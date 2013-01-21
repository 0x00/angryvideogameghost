package level;

import java.util.List;

import map.Landscape;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class Pacman extends PathAI {
	List<Food> food;

	public Pacman(Bitmap gfx, Landscape landscape, List<Food> food) {
		this.gfx = gfx;
		border = 3;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

		this.landscape = landscape;
		this.food = food;
	}
	
	float frame = 1;

	@Override
	public void draw(Canvas c) {
		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + border, Y + border, X + sizeW - border, Y
				+ sizeH - border);

		Paint paint = new Paint();
		paint.setColor(Color.rgb(255, 255, 0));
		c.drawCircle(X + sizeW / 2, Y + sizeH / 2, sizeW / 2 - border, paint);

		paint.setColor(Color.BLACK);

		double fr = Math.abs(Math.sin(frame));
		
		int a = (int) (-45 + 45*fr);
		if (localDirection == null)
			return;
		
		if (localDirection.x < x) {
			a = (int) (135+45*fr);
		}
		if (localDirection.y < y) {
			a = (int) (-135+45*fr);
		}
		if (localDirection.y > y) {
			a = (int) (45+45*fr);
		}

		
		c.drawArc(target, a, (float) (90-90*fr) , true, paint);
	}

	@Override
	public void action(double delta) {
		super.action(delta * 1.02);
		frame+=delta*0.7;
		collisionHandling();
		assignTarget();
	}

	private void collisionHandling() {
		for (Food f : food) {

			if (f.active && f.target != null && target != null
					&& f.target.intersect(target)) {
				f.active = false;
			}
		}
	}

	private void assignTarget() {
		for (Food f : food) {
			if (f.active) {
				toX = f.x;
				toY = f.y;
				break;
			}
		}
	}
}
