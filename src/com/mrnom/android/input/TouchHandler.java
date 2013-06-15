package com.mrnom.android.input;

import java.util.List;

import com.mrnom.input.TouchEvent;

public interface TouchHandler {

	public boolean isTouchDown(int pointer);
	
	public int getTouchX(int pointer);
	
	public int getTouchY(int pointer);
	
	public List<TouchEvent> getTouchEvents();
	
}
