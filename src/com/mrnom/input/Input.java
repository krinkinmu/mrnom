package com.mrnom.input;

import java.util.List;


public interface Input {

	public boolean isKeyPressed(int keyCode);
	
	public boolean isTouchDown(int pointer);
	
	public int getTouchX(int pointer);
	
	public int getTouchY(int pointer);
	
	public float getAccelX();
	
	public float getAccelY();
	
	public float getAccelZ();
	
	public List<TouchEvent> getTouchEvents();
	
	public List<KeyEvent> getKeyEvents();
	
}
