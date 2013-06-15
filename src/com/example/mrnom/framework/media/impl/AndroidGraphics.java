package com.example.mrnom.framework.media.impl;

import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

import com.example.mrnom.framework.common.PixmapFormat;
import com.example.mrnom.framework.media.Graphics;
import com.example.mrnom.framework.media.Pixmap;

public class AndroidGraphics implements Graphics {
	private final AssetManager assets;
	private final Bitmap buffer;
	private final Canvas canvas;
	private final Paint paint;
	
	private final Rect src = new Rect();
	private final Rect dst = new Rect();
	
	public AndroidGraphics(AssetManager assets, Bitmap buffer) {
		this.assets = assets;
		this.buffer = buffer;
		this.canvas = new Canvas(buffer);
		this.paint = new Paint();
	}
	
	@Override
	public Pixmap newPixmap(String fileName, PixmapFormat format) {
		Config config = null;
		if (format == PixmapFormat.RGB565) {
			config = Config.RGB_565;
		} else if (format == PixmapFormat.ARGB4444) {
			config = Config.ARGB_4444;
		} else {
			config = Config.ARGB_8888;
		}
		Options options = new Options();
		options.inPreferredConfig = config;
		
		InputStream in = null;
		Bitmap bitmap = null;
		try {
			in = assets.open(fileName);
			bitmap = BitmapFactory.decodeStream(in);
			if (bitmap == null) {
				throw new RuntimeException("Couldn't load bitmap file " + fileName + "!");
			}
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load bitmap file " + fileName + "!");
		} finally {
			if (in != null) {
				try { in.close(); } catch (IOException e) {}
			}
		}
		if (bitmap.getConfig() == Config.RGB_565) {
			format = PixmapFormat.RGB565;
		} else if (bitmap.getConfig() == Config.ARGB_4444) {
			format = PixmapFormat.ARGB4444;
		} else {
			format = PixmapFormat.ARGB8888;
		}
		return new AndroidPixmap(bitmap, format);
	}

	@Override
	public void clear(int color) {
		canvas.drawRGB(color & 0xff0000 >> 16, color & 0x00ff00 >> 8, color & 0x0000ff);
	}

	@Override
	public void drawPixel(int x, int y, int color) {
		paint.setColor(color);
		canvas.drawPoint(x, y, paint);
	}

	@Override
	public void drawLine(int srcX, int srcY, int dstX, int dstY, int color) {
		paint.setColor(color);
		canvas.drawLine(srcX, srcY, dstX, dstY, paint);
	}

	@Override
	public void drawRect(int left, int top, int width, int height, int color) {
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawRect(left, top, left + width - 1, top + height - 1, paint);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int left, int top, int srcX,
			int srcY, int width, int height) {
		src.set(srcX, srcY, srcX + width - 1, srcY + height - 1);
		dst.set(left, top, left + width - 1, top + height - 1);
		canvas.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), src, dst, null);
	}

	@Override
	public void drawPixmap(Pixmap pixmap, int left, int top) {
		canvas.drawBitmap(((AndroidPixmap)pixmap).getBitmap(), left, top, null);
	}

	@Override
	public int getWidth() {
		return buffer.getWidth();
	}

	@Override
	public int getHeight() {
		return buffer.getHeight();
	}
}
