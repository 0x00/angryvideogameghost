package data;

import map.Landscape;
import map.ValidMapGenerator;
import android.graphics.Bitmap;

public class Infos {
	public static Bitmap block;
	public static Bitmap ghost;
	public static Landscape level1 = new ValidMapGenerator(14, 24).landscape;
}
