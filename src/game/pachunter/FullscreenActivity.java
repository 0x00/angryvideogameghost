package game.pachunter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import data.Infos;
import data.Score;
import display.Screen;

public class FullscreenActivity extends Activity {

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Infos.blockBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);

		Infos.ghostBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosts);

		Infos.score = new Score();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		getActionBar().hide();
		
		
		OnTouchListener touch = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float x = event.getX() / v.getWidth();
				float y = event.getY() / v.getHeight();

				Screen screen = (Screen) findViewById(R.id.screen1);

				screen.map.ghost.toX = screen.map.transferX(x);
				screen.map.ghost.toY = screen.map.transferY(y);

				Log.d("goto", "goto: " + x + ", " + y);

				return false;
			}

		};

		setContentView(R.layout.activity_fullscreen);

		final Screen screen = (Screen) findViewById(R.id.screen1);
		
		Button start = (Button) findViewById(R.id.dummy_button);
		start.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				screen.map.startGame();
				return false;
			}
		});
		
		screen.setOnTouchListener(touch);
		screen.map.map.dump();
		System.out.println();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
