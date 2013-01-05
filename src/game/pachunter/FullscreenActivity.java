package game.pachunter;

import data.Infos;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class FullscreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Infos.block = BitmapFactory.decodeResource(getResources(),
				R.drawable.block);
		
		Infos.ghost = BitmapFactory.decodeResource(getResources(),
				R.drawable.ghosts);

		setContentView(R.layout.activity_fullscreen);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
}
