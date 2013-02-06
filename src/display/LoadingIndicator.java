package display;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class LoadingIndicator extends View{

	public LoadingIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawColor(Color.RED);
	}
}
