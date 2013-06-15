package com.mrnom.graphics;


public interface Graphics {
	
	public Pixmap newPixmap(String fileName, PixmapFormat format);
	
	public void clear(int color);
	
	public void drawPixel(int x, int y, int color);
	
	public void drawLine(int srcX, int srcY, int dstX, int dstY, int color);
	
	public void drawRect(int left, int top, int width, int height, int color);
	
	public void drawPixmap(Pixmap pixmap, int left, int top, int srcX, int srcY, int width, int height);
	
	public void drawPixmap(Pixmap pixmap, int left, int top);
	
	public int getWidth();
	
	public int getHeight();

}
