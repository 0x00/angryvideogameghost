package city.felix.angryvideogameghost.game.pachunter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;

import avgg.games.felix.city.angryvideogameghost.R;

import city.felix.angryvideogameghost.audio.Sounds;
import city.felix.angryvideogameghost.data.Infos;
import city.felix.angryvideogameghost.data.Score;
import city.felix.angryvideogameghost.display.Screen;

public class FullscreenActivity extends Activity {

	Screen screen;
	Button start;
	private Button tutorial;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Sounds.continueBackground();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Sounds.pauseBackground();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Infos.audio = new Sounds();
		Sounds.init(this);

		Infos.blockBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);

		Infos.ghostBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosts);

		//Infos.ghostBitmap = BitmapHelper.changeHue(Infos.ghostBitmap, -0.3);

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

				if (!screen.map.busy) {
					screen.map.ghost.toX = screen.map.transferX(x);
					screen.map.ghost.toY = screen.map.transferY(y);
					Log.d("goto", "goto: " + x + ", " + y);
				}

				return false;
			}

		};

		
		setContentView(R.layout.activity_fullscreen);

		screen = (Screen) findViewById(R.id.screen1);

		start = (Button) findViewById(R.id.dummy_button);
		tutorial = (Button) findViewById(R.id.tutorial);

		tutorial.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				showAlert();
				return false;
			}

		});

		start.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					hideUI();
				return false;
			}

		});

		screen.setOnTouchListener(touch);
		screen.map.map.dump();
		screen.map.activity = this;
		System.out.println();
	}

	boolean alert = false;
	private void showAlert() {
		if(alert) return;
		
		alert = true;
		Builder b = new AlertDialog.Builder(this);
		final AlertDialog a = b.create();
		
		LayoutInflater factory = LayoutInflater.from(FullscreenActivity.this);
		View tutor = factory.inflate(R.layout.tutorial,null);
		a.setView(tutor);
		
		Button dis = (Button) tutor.findViewById(R.id.button1);
		dis.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				a.cancel();
				alert = false;
				return false;
			}
		});

		
		a.show();
		
		
	}
	public void hideUI() {
	//	hideActionBar();
		start.setVisibility(View.GONE);
		tutorial.setVisibility(View.GONE);
		screen.map.startGame();
	}

	public void showUI() {
		//showActionBar();
		Infos.level = 1;
		start.setVisibility(View.VISIBLE);
		tutorial.setVisibility(View.VISIBLE);
		screen.map.initGame();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void hideActionBar() {
		if (Build.VERSION.SDK_INT < 16)
			return;
		getActionBar().hide();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	void showActionBar() {
		if (Build.VERSION.SDK_INT < 16)
			return;
		getActionBar().show();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
