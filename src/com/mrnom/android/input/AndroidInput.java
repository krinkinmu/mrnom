package com.mrnom.android.input;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import com.mrnom.input.Input;
import com.mrnom.input.KeyEvent;
import com.mrnom.input.TouchEvent;

public class AndroidInput implements Input {
	private final AccelerometerHandler accelerometer;
	private final KeyboardHandler keyboard;
	private final TouchHandler touch;
	
	@SuppressWarnings("deprecation")
	public AndroidInput(Context context, View view, float scaleX, float scaleY) {
		accelerometer = new AccelerometerHandler(context);
		keyboard = new KeyboardHandler(view);
		if (Integer.parseInt(VERSION.SDK) > 5) {
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
