package com.mrnom.android.graphics;

import android.graphics.Bitmap;

import com.mrnom.graphics.Pixmap;
import com.mrnom.graphics.PixmapFormat;

public class AndroidPixmap implements Pixmap {
	private final Bitmap bitmap;
	private final PixmapFormat format;
	
	public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
		this.bitmap = bitmap;
		this.format = format;
	}

	@Override
	public void dispose() {
		bitmap.recycle();
	}

	@Override
	public int getWidth() {
		return bitmap.getWidth();
	}

	@Override
	public int getHeight() {
		return bitmap.getHeight();
	}

	@Override
	public PixmapFormat getFormat() {
		return format;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
}
