package level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

public class Block {

	public float x;
	public float y;
	public float sizeW;
	public float sizeH;
	
	Bitmap gfx;
	public Rect rect;
	public RectF target;
	
	public Block(Bitmap gfx){
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

	}
	
	public void draw(Canvas c){
		
		float X = x*c.getWidth();
		float Y = y*c.getHeight();
		target = new RectF(X,Y, X+sizeW, Y+sizeH);
		
		c.drawBitmap(gfx,rect,target,null);
	}
}
