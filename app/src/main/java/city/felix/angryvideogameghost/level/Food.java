package city.felix.angryvideogameghost.level;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class Food extends Drawable {

	public boolean powerUp = Math.random() > 0.95 ? true : false;

	public Food(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void draw(Canvas c) {

		if (!active)
			return;

		float X = x * sizeW;
		float Y = y * sizeH;
		target = new RectF(X + border, Y + border, X + sizeW - border, Y
				+ sizeH - border);

		Paint paint = new Paint();
		paint.setColor(Color.rgb(155, 105, 60));
		int rad = powerUp? 7 : 2;
		c.drawCircle(X + sizeW / 2, Y + sizeH / 2, rad, paint);
	}

}
