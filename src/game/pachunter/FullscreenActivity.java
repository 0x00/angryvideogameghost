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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Infos.blockBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);

		Infos.ghostBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosts);

		Infos.blockWhiteBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.blockwhite);

		Infos.score = new Score();

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

				hideActionBar();
				v.setVisibility(View.GONE);
				screen.map.startGame();
				return false;
			}
		});

		screen.setOnTouchListener(touch);
		screen.map.map.dump();
		System.out.println();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void hideActionBar() {
		getActionBar().hide();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void showActionBar() {
		getActionBar().show();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
