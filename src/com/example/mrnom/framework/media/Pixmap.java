package com.example.mrnom.framework.media;

import com.example.mrnom.framework.common.PixmapFormat;

public interface Pixmap extends Disposable {

	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
}
