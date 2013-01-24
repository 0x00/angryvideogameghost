package level;

import java.util.Collections;
import java.util.Comparator;

import map.Landscape;
import map.Point;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Ghost extends PathAI {

	public Pacman pacman;

	public Ghost(Bitmap gfx, Landscape landscape) {
		super(landscape);
		
		this.gfx = gfx;
		border = 3;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());
	}

	boolean autoplay = true;

	@Override
	public void action(double delta) {
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
			Collections.sort(landscape.blocks, new Comparator<map.Block>() {

				@Override
				public int compare(map.Block lhs, map.Block rhs) {

					Point p1 = new Point((int) lhs.x, (int) lhs.y);
					Point p2 = new Point((int) rhs.x, (int) rhs.y);

					Point avoider = new Point((int) pacman.x, (int) pacman.y);
					double d1 = p1.distance(avoider);
					double d2 = p2.distance(avoider);

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
