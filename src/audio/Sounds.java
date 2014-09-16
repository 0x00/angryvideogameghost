package audio;

import game.pachunter.R;

import java.util.HashMap;

import data.Infos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Sounds {

	static Context context;
	static SoundPool pool;
	static HashMap<Integer, Integer> soundsMap;

	public static MediaPlayer mediaPlayer;
	private static int backgroundId;
	private static int last = -1;

	@SuppressLint("UseSparseArrays")
	public static void init(Context context) {

		pool = new SoundPool(5, AudioManager.STREAM_MUSIC, 100);
		Sounds.context = context;

		soundsMap = new HashMap<Integer, Integer>();

		soundsMap.put(R.raw.pacman_background1,
				pool.load(context, R.raw.pacman_background1, 1));
		soundsMap.put(R.raw.pacman_background2,
				pool.load(context, R.raw.pacman_background2, 1));
		soundsMap.put(R.raw.pacman_getghost,
				pool.load(context, R.raw.pacman_getghost, 1));
		soundsMap.put(R.raw.pacman_power1,
				pool.load(context, R.raw.pacman_power1, 1));
		soundsMap.put(R.raw.pacman_alarm1,
				pool.load(context, R.raw.pacman_alarm1, 1));
		soundsMap.put(R.raw.pacman_death,
				pool.load(context, R.raw.pacman_death, 1));

		soundsMap
				.put(R.raw.background, pool.load(context, R.raw.background, 1));

		soundsMap.put(R.raw.creepybackground,
				pool.load(context, R.raw.creepybackground, 1));

		soundsMap.put(R.raw.pacman_death,
				pool.load(context, R.raw.pacman_death, 1));

		soundsMap.put(R.raw.waka, pool.load(context, R.raw.waka, 1));

	}

	public static void playMus(int r) {

		if (Infos.silent)
			return;

		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		if (!soundsMap.containsKey(r)) {
			return;
		}

		if (r == R.raw.waka)
			volume /= 10;

		Integer sound = soundsMap.get(r);
		pool.play(sound, volume, volume, 1, 0, 1.0f);

	}

	public static void pauseBackground() {
		pool.autoPause();
	}

	public static void continueBackground() {
		pool.autoResume();
	}

	public static void startBackground(int r) {

		if (last == r)
			return;

		if (Infos.silent)
			return;

		stopBackground();
		last = r;

		if (!soundsMap.containsKey(r)) {
			return;
		}

		Integer sound = soundsMap.get(r);

		AudioManager mgr = (AudioManager) context
				.getSystemService(Context.AUDIO_SERVICE);
		float streamVolumeCurrent = mgr
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		float streamVolumeMax = mgr
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		float volume = streamVolumeCurrent / streamVolumeMax;

		backgroundId = pool.play(sound, volume, volume, 1, -1, 1f);
	}

	public static void stopBackground() {
		last = -1;
		pool.stop(backgroundId);
	}
}
