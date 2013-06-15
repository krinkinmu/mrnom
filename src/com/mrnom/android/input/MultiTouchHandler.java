package com.mrnom.android.input;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mrnom.common.Pool;
import com.mrnom.common.PoolObjectFactory;
import com.mrnom.input.TouchEvent;

@TargetApi(5)
public class MultiTouchHandler implements TouchHandler, OnTouchListener {
	private static final int FINGERS = 10;
	
	private final List<TouchEvent> events = new ArrayList<TouchEvent>();
	private final List<TouchEvent> buffer = new ArrayList<TouchEvent>();
	private final Pool<TouchEvent> pool;
	
	private final boolean[] pressed = new boolean[FINGERS];
	private final int[] touchX = new int[FINGERS];
	private final int[] touchY = new int[FINGERS];
	private final int[] id = new int[FINGERS];
	
	private final float scaleX;
	private final float scaleY;
	
	public MultiTouchHandler(View view, float scaleX, float scaleY) {
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
	public synchronized boolean onTouch(View v, MotionEvent event) {
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		int index = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		int count = event.getPointerCount();
		TouchEvent touchEvent;
		for (int i = 0; i < FINGERS; ++i) {
			if (i >= count) {
				pressed[i] = false;
				id[i] = -1;
				continue;
			}
			int pointerId = event.getPointerId(i);
			if (event.getAction() != MotionEvent.ACTION_MOVE && i != index) {
				continue;
			}
			switch (action) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_POINTER_DOWN:
				touchEvent = pool.newObject();
				touchEvent.type = TouchEvent.TOUCH_DOWN;
				touchEvent.pointer = pointerId;
				touchEvent.x = (int) (event.getX(i) * scaleX);
				touchEvent.y = (int) (event.getY(i) * scaleY);
				pressed[i] = true;
				id[i] = pointerId;
				buffer.add(touchEvent);
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
			case MotionEvent.ACTION_CANCEL:
				touchEvent = pool.newObject();
				touchEvent.type = TouchEvent.TOUCH_UP;
				touchEvent.pointer = pointerId;
				touchEvent.x = (int) (event.getX(i) * scaleX);
				touchEvent.y = (int) (event.getY(i) * scaleY);
				pressed[i] = false;
				id[i] = pointerId;
				buffer.add(touchEvent);
				break;
			case MotionEvent.ACTION_MOVE:
				touchEvent = pool.newObject();
				touchEvent.type = TouchEvent.TOUCH_DRAG;
				touchEvent.pointer = pointerId;
				touchEvent.x = (int) (event.getX(i) * scaleX);
				touchEvent.y = (int) (event.getY(i) * scaleY);
				pressed[i] = true;
				id[i] = pointerId;
				buffer.add(touchEvent);
				break;
			}
		}
		return true;
	}

	@Override
	public synchronized boolean isTouchDown(int pointer) {
		int index = getIndex(pointer);
		if (index < 0 || index >= FINGERS) {
			return false;
		}
		return pressed[index];
	}

	@Override
	public synchronized int getTouchX(int pointer) {
		int index = getIndex(pointer);
		if (index >= FINGERS) {
			return 0;
		}
		return touchX[index];
	}

	@Override
	public synchronized int getTouchY(int pointer) {
		int index = getIndex(pointer);
		if (index >= FINGERS) {
			return 0;
		}
		return touchY[index];
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
	
	private int getIndex(int pointer) {
		int index = 0;
		while (index < FINGERS && id[index] != pointer) {
			++index;
		}
		return index;
	}

}
