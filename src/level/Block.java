package level;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Block {

	Bitmap gfx;
	public Rect rect;
	public Rect target;
	
	public Block(Bitmap gfx){
		this.gfx = gfx;
		if (this.gfx != null)
			rect = new Rect(0, 0, gfx.getWidth(), gfx.getHeight());

	}
	
	public void draw(Canvas c){
		
		target = new Rect(40, 40, c.getWidth()-40, c.getHeight()-40);
		c.drawBitmap(gfx,rect,target,null);
	}
}
