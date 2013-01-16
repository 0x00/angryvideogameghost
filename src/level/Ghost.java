package level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ghost {
	public float x = 5;
	public float y = 8;
	
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
		
		float X = x*sizeW;
		float Y = y*sizeH;
		target = new RectF(X+3,Y+3, X+sizeW-3, Y+sizeH-3);
		
		c.drawBitmap(gfx,rect,target,null);
	}
}
