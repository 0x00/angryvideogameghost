package game.pachunter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import data.Infos;
import display.Screen;

public class FullscreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Infos.block = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);
		
		Infos.ghost = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosts);
		
		
		OnTouchListener touch = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX()/v.getWidth();
				float y = event.getY()/v.getHeight();
				
				Screen screen = (Screen) findViewById(R.id.screen1);
				
				Infos.player.toX = screen.map.transferX(x);
				Infos.player.toY = screen.map.transferY(y);
				
				Log.d("goto", "goto: "+x+", "+y);
				
				return false;
			}
			
		};
		
		setContentView(R.layout.activity_fullscreen);

		Screen screen = (Screen) findViewById(R.id.screen1);
		screen.setOnTouchListener(touch);
		screen.map.map.dump();
		System.out.println();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
