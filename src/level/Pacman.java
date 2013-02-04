package level;

import game.pachunter.R;

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
import audio.Sounds;

public class Pacman extends PathAI {
	List<Food> food;

	public Pacman(Bitmap gfx, Landscape landscape, List<Food> food) {
		super(landscape);

		this.gfx = gfx;
		border = 3;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

		this.food = food;
	}

	float frame = 1;
	boolean powerUp = false;
	float powerClock = 0;
	private boolean debug = false;

	int a;

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

		if (localDirection == null)
			return;

		if (localDirection.x > x) {
			a = (int) (-45 + 45 * fr);
		}

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

		if (debug) {
			paint.setColor(Color.rgb(255, 0, 0));
			RectF re = new RectF(toX * sizeW, toY * sizeH, toX * sizeW + sizeW,
					toY * sizeH + sizeH);
			c.drawRect(re, paint);

			if (path == null)
				return;
			for (Point p : path) {
				paint.setColor(Color.rgb(0, 255, 0));
				re = new RectF(p.x * sizeW + 10, p.y * sizeH + 10, p.x * sizeW
						+ sizeW - 10, p.y * sizeH + sizeH - 10);
				c.drawRect(re, paint);
			}
		}
	}

	@Override
	public void action(double delta) {
		super.action(delta * 1.02);

		powerClock -= delta * 0.1;
		if (powerClock < 0) {
			powerUp = false;
			avoidDanger = true;
			Sounds.startBackground(R.raw.pacman_background1);
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
				Sounds.playMus(R.raw.waka);
				if (f.powerUp) {
					Sounds.playMus(R.raw.pacman_power1);
					powerClock = 8;
					Sounds.startBackground(R.raw.pacman_background2);
					powerUp = true;
					avoidDanger = false;
					nav.path.clear();
				}
			}
		}
	}

	Food targetFood;

	private void assignTarget() {

		if (powerUp) {

			if (avoid.x == landscape.maze.length / 2
					&& avoid.y == landscape.maze[0].length / 2) {

			} else {
				toX = avoid.x;
				toY = avoid.y;
				targetFood = null;
				return;
			}

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
