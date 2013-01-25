package helper;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapHelper {
	public static Bitmap changeHue( Bitmap source, double hue ) {
	    Bitmap result = Bitmap.createBitmap( source.getWidth(), source.getHeight(), source.getConfig() );

	    float[] hsv = new float[3];
	    for( int x = 0; x < source.getWidth(); x++ ) {
	        for( int y = 0; y < source.getHeight(); y++ ) {
	            int c = source.getPixel( x, y );
	            Color.colorToHSV( c, hsv );
	            hsv[0] = (float) ((hsv[0] + 360 * hue) % 360);
	            c = (Color.HSVToColor( hsv ) & 0x00ffffff) | (c & 0xff000000);
	            result.setPixel( x, y, c );
	        }
	    }

	    return result;
	}
}
