package com.example.mrnom.framework.handlers;

import java.util.List;

import com.example.mrnom.framework.common.TouchEvent;

public interface TouchHandler {

	public boolean isTouchDown(int pointer);
	
	public int getTouchX(int pointer);
	
	public int getTouchY(int pointer);
	
	public List<TouchEvent> getTouchEvents();
	
}
