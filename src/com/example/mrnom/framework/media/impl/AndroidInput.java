package com.example.mrnom.framework.media.impl;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.example.mrnom.framework.common.KeyEvent;
import com.example.mrnom.framework.common.TouchEvent;
import com.example.mrnom.framework.handlers.AccelerometerHandler;
import com.example.mrnom.framework.handlers.KeyboardHandler;
import com.example.mrnom.framework.handlers.TouchHandler;
import com.example.mrnom.framework.handlers.impl.MultiTouchHandler;
import com.example.mrnom.framework.handlers.impl.SingleTouchHandler;
import com.example.mrnom.framework.media.Input;

public class AndroidInput implements Input {
	private final AccelerometerHandler accelerometer;
	private final KeyboardHandler keyboard;
	private final TouchHandler touch;
	
	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		accelerometer = new AccelerometerHandler(context);
		keyboard = new KeyboardHandler(view);
		if (VERSION.SDK_INT > 5) {
			touch = new MultiTouchHandler(view, scaleX, scaleY);
		} else {
			touch = new SingleTouchHandler(view, scaleX, scaleY);
		}
	}

	@Override
	public boolean isKeyPressed(int keyCode) {
		return keyboard.isKeyPressed(keyCode);
	}

	@Override
	public boolean isTouchDown(int pointer) {
		return touch.isTouchDown(pointer);
	}

	@Override
	public int getTouchX(int pointer) {
		return touch.getTouchX(pointer);
	}

	@Override
	public int getTouchY(int pointer) {
		return touch.getTouchY(pointer);
	}

	@Override
	public float getAccelX() {
		return accelerometer.getAccelX();
	}

	@Override
	public float getAccelY() {
		return accelerometer.getAccelY();
	}

	@Override
	public float getAccelZ() {
		return accelerometer.getAccelZ();
	}

	@Override
	public List<TouchEvent> getTouchEvents() {
		return touch.getTouchEvents();
	}

	@Override
	public List<KeyEvent> getKeyEvents() {
		return keyboard.getKeyEvents();
	}

}
