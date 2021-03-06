package city.felix.angryvideogameghost.level;

import java.util.Collections;
import java.util.Comparator;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import city.felix.angryvideogameghost.map.Landscape;
import city.felix.angryvideogameghost.map.Point;

public class Ghost extends PathAI {

	public Pacman pacman;
	int offset = 0;

	public Ghost(Bitmap gfx, Landscape landscape) {
		super(landscape);
		this.gfx = gfx;

		border = 3;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth() / 6, gfx.getHeight());
	}

	boolean autoplay = true;
	public boolean showpath = false;
	public boolean freeze;

	@Override
	public void draw(Canvas c) {

		offset = 0;

		if (localDirection != null) {
			if (localDirection.x < x)
				offset = 0;

			if (localDirection.x > x)
				offset = 2;

			if (localDirection.y < y)
				offset = 1;

			if (localDirection.y > y)
				offset = 3;

			if (pacman.powerUp) {
				offset = 4;
			}

			if (freeze)
				offset = 5;
		}

		this.rect = new Rect(offset * gfx.getWidth() / 6, 0, offset
				* gfx.getWidth() / 6 + gfx.getWidth() / 6, gfx.getHeight());

		if (showpath && ((int) frame) % 2 == 0) {

			if (path != null) {
				for (Point p : path) {
					Paint paint = new Paint();

					if (!freeze) {
						paint.setColor(Color.rgb(0, 155, 0));
						c.drawCircle(p.x * sizeW + sizeW / 2, p.y * sizeH
								+ sizeH / 2, 6, paint);
					} else {

						paint.setColor(Color.rgb(255, 255, 255));
						c.drawCircle(p.x * sizeW + sizeW / 2, p.y * sizeH
								+ sizeH / 2, 6, paint);
					}
				}
			}
		}

		super.draw(c);
	}

	@Override
	public void action(double delta) {

		frame += delta * 0.9;

		if (freeze) {
			speed = 0.20;
		} else {
			speed = 0.14;
		}

		super.action(delta);

		if (!autoplay)
			return;

		if (!pacman.powerUp) {
			avoid = null;
			avoidDanger = false;

			if (distance(pacman) > 5) {
				toX = pacman.toX;
				toY = pacman.toY;
			} else {
				toX = pacman.x;
				toY = pacman.y;
			}
		}

		if (pacman.powerUp) {
			Collections.sort(landscape.blocks, new Comparator<city.felix.angryvideogameghost.map.Block>() {

				@Override
				public int compare(city.felix.angryvideogameghost.map.Block lhs, city.felix.angryvideogameghost.map.Block rhs) {

					Point p1 = new Point((int) lhs.x, (int) lhs.y);
					Point p2 = new Point((int) rhs.x, (int) rhs.y);

					Point avoider = new Point((int) pacman.x, (int) pacman.y);
					double d1 = p1.distance(avoider);
					double d2 = p2.distance(avoider);
					if (lhs.type != 0) {
						d1 = 0;
					}
					if (rhs.type != 0) {
						d2 = 0;
					}

					return (int) ((d2 - d1) * 1000);
				}
			});

			avoid = pacman;
			avoidDanger = true;
			toX = landscape.blocks.get(0).x;
			toY = landscape.blocks.get(0).y;
		}
	}
}
