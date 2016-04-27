package city.felix.angryvideogameghost.level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Block extends Drawable {

	public Block(Bitmap gfx) {
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());
	}

	public void draw(Canvas c, int color) {
		float X = x * c.getWidth();
		float Y = y * c.getHeight();
		target = new RectF(X, Y, X + sizeW, Y + sizeH);

		Paint paint = new Paint();
		paint.setColor(color);

		c.drawBitmap(gfx, rect, target, paint);
		
	}
}
