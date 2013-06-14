package com.example.mrnom.framework.media;

import java.util.List;

import com.example.mrnom.framework.common.KeyEvent;
import com.example.mrnom.framework.common.TouchEvent;

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
