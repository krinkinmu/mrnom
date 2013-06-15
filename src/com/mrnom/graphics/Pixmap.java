package com.mrnom.graphics;

import com.mrnom.common.Disposable;

public interface Pixmap extends Disposable {

	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
}
