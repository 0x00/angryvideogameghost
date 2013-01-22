package level;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import map.Landscape;
import map.Point;
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
	boolean powerUp = false;
	float powerClock = 0;

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

		int a = (int) (-45 + 45 * fr);
		if (localDirection == null)
			return;

		if (localDirection.x < x) {
			a = (int) (135 + 45 * fr);
		}
		if (localDirection.y < y) {
			a = (int) (-135 + 45 * fr);
		}
		if (localDirection.y > y) {
			a = (int) (45 + 45 * fr);
		}

		c.drawArc(target, a, (float) (90 - 90 * fr), true, paint);
	}

	@Override
	public void action(double delta) {
		super.action(delta * 1.02);

		powerClock -= delta * 0.1;
		if (powerClock < 0){
			powerUp = false;
			avoidDanger = true;
		}

		frame += delta * 0.7;
		collisionHandling();
		assignTarget();
	}

	private void collisionHandling() {
		for (Food f : food) {

			if (f.active && f.target != null && target != null
					&& f.target.intersect(target)) {
				f.active = false;
				if(f.powerUp){
					powerClock = 5;
					powerUp = true;
					avoidDanger = false;
				}
			}
		}
	}

	Food targetFood;

	private void assignTarget() {

		if (powerUp) {
			toX = avoid.x;
			toY = avoid.y;
			return;
		}

		if (distance(avoid) < 3)
			targetFood = null;

		if (targetFood != null && targetFood.active)
			return;

		Collections.sort(food, new Comparator<Food>() {

			@Override
			public int compare(Food lhs, Food rhs) {

				Point p1 = new Point((int) lhs.x, (int) lhs.y);
				Point p2 = new Point((int) rhs.x, (int) rhs.y);

				Point avoider = new Point((int) avoid.x, (int) avoid.y);
				double d1 = p1.distance(avoider);
				double d2 = p2.distance(avoider);

				return (int) ((d2 - d1) * 1000);
			}
		});

		for (Food f : food) {
			if (f.active) {
				Log.d("new target", f.x + " " + f.y);
				toX = f.x;
				toY = f.y;
				targetFood = f;
				break;
			}
		}
	}
}
