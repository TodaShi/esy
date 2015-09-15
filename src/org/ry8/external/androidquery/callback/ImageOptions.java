package org.ry8.external.androidquery.callback;

import android.graphics.Bitmap;
import org.ry8.external.androidquery.AQuery;

public class ImageOptions {

	public boolean memCache = true;
	public boolean fileCache = true;
	public Bitmap preset;
	public int policy;
	
	public int targetWidth;
	public int fallback;
	public int animation;
	public float ratio;
	public int round;
	public float anchor = AQuery.ANCHOR_DYNAMIC;

	
}
