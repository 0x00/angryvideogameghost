package display;

import data.Infos;
import level.Block;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Screen extends SurfaceView implements SurfaceHolder.Callback {
	
	Controller controller;
	
	public Block block = new Block(Infos.block);
	
	public Screen(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		getHolder().addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		controller = new Controller(this);
		controller.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		controller.alive = false;
		boolean end = false;

		while (!end) {
			try {
				controller.join();
				end = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
