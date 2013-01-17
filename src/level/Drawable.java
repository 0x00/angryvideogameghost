package level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Drawable extends Entity {
	
	public float sizeW;
	public float sizeH;

	Bitmap gfx;
	public Rect rect;
	public RectF target;
	
	public void draw(Canvas c) {

		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + 3, Y + 3, X + sizeW - 3, Y + sizeH - 3);

		c.drawBitmap(gfx, rect, target, null);
	}
}
