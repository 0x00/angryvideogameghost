package data;

import level.Ghost;
import map.Landscape;
import map.ValidMapGenerator;
import android.graphics.Bitmap;

public class Infos {
	public static Bitmap block;
	public static Bitmap ghost;
	public static Landscape level1 = new ValidMapGenerator(10, 16).landscape;
	public static Ghost player;
}
