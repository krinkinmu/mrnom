package com.example.mrnom.framework.handlers.impl;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.mrnom.framework.common.Pool;
import com.example.mrnom.framework.common.PoolObjectFactory;
import com.example.mrnom.framework.common.TouchEvent;
import com.example.mrnom.framework.handlers.TouchHandler;

public class SingleTouchHandler implements TouchHandler, OnTouchListener {
	private final List<TouchEvent> events = new ArrayList<TouchEvent>();
	private final List<TouchEvent> buffer = new ArrayList<TouchEvent>();
	private final Pool<TouchEvent> pool;
	
	private final float scaleX;
	private final float scaleY;
	
	private boolean isTouched;
	private int touchX;
	private int touchY;
	
	public SingleTouchHandler(View view, float scaleX, float scaleY) {
		PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
			@Override
			public TouchEvent createObject() {
				return new TouchEvent();
			}
		};
		pool = new Pool<TouchEvent>(factory);
		view.setOnTouchListener(this);
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}

	@Override
	public synchronized boolean isTouchDown(int pointer) {
		if (pointer != 0) {
			return false;
		}
		return isTouched;
	}

	@Override
	public synchronized int getTouchX(int pointer) {
		return touchX;
	}

	@Override
	public synchronized int getTouchY(int pointer) {
		return touchY;
	}

	@Override
	public synchronized List<TouchEvent> getTouchEvents() {
		for (TouchEvent event : events) {
			pool.freeObject(event);
		}
		events.clear();
		events.addAll(buffer);
		buffer.clear();
		return events;
	}

	@Override
	public synchronized boolean onTouch(View v, MotionEvent event) {
		TouchEvent touchEvent = pool.newObject();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchEvent.type = TouchEvent.TOUCH_DOWN;
			isTouched = true;
			break;
		case MotionEvent.ACTION_MOVE:
			touchEvent.type = TouchEvent.TOUCH_DRAG;
			isTouched = true;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			touchEvent.type = TouchEvent.TOUCH_UP;
			isTouched = false;
			break;
		}
		touchEvent.x = (int) (event.getX() * scaleX);
		touchEvent.y = (int) (event.getY() * scaleY);
		buffer.add(touchEvent);
		return true;
	}

}
