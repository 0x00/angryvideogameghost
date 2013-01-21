package level;

import java.util.List;

import map.Landscape;
import android.graphics.Bitmap;
import android.graphics.Rect;

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

	@Override
	public void action(double delta) {

		super.action(delta);

		for (Food f : food) {

			if (f.active && f.target != null && target != null
					&& f.target.intersect(target)) {
				f.active = false;
			}
		}
		assignTarget();

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
