package city.felix.angryvideogameghost.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Drawable extends Entity {

	public float frame = 0;

	public float sizeW;
	public float sizeH;

	Bitmap gfx;
	public Rect rect;
	public RectF target;

	public float border = 0;

	public void draw(Canvas c) {

		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + border, Y + border, X + sizeW - border, Y
				+ sizeH - border);

		c.drawBitmap(gfx, rect, target, null);
	}
}
