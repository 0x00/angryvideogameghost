package level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ghost {
	public float x = .5f;
	public float y = .5f;
	
	public float toX = x;
	public float toY = y;
	
	public float sizeW;
	public float sizeH;
	
	
	Bitmap gfx;
	public Rect rect;
	public RectF target;
	
	public Ghost(Bitmap gfx){
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

	}
	
	public void draw(Canvas c){
		
		float X = x*c.getWidth();
		float Y = y*c.getHeight();
		target = new RectF(X-sizeW/2,Y-sizeH/2, X+sizeW/2, Y+sizeH/2);
		
		c.drawBitmap(gfx,rect,target,null);
	}
}
