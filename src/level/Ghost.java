package level;

import map.Landscape;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Ghost extends PathAI {

	public Ghost(Bitmap gfx, Landscape landscape) {
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

		this.landscape = landscape;
	}

}
