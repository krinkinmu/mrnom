package com.mrnom.android.input;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnKeyListener;

import com.mrnom.common.Pool;
import com.mrnom.common.PoolObjectFactory;
import com.mrnom.input.KeyEvent;

public class KeyboardHandler implements OnKeyListener {
	private final static int MAX_KEY_CODE = 127;
	private final static int MIN_KEY_CODE = 0;
	private final boolean[] pressed = new boolean[MAX_KEY_CODE + 1];
	private final List<KeyEvent> buffer = new ArrayList<KeyEvent>();
	private final List<KeyEvent> events = new ArrayList<KeyEvent>();
	private final Pool<KeyEvent> pool;
	
	public KeyboardHandler(View view) {
		PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>() {
			@Override
			public KeyEvent createObject() {
				return new KeyEvent();
			}
		};
		pool = new Pool<KeyEvent>(factory);
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
	}

	@Override
	public synchronized boolean onKey(View v, int keyCode, android.view.KeyEvent event) {
		if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE)
			return false;
		KeyEvent keyEvent = pool.newObject();
		keyEvent.keyCode = keyCode;
		keyEvent.keyChar = (char) event.getUnicodeChar();
		if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
			keyEvent.type = KeyEvent.KEY_DOWN;
			if (keyCode > 0 && keyCode < 127) {
				pressed[keyCode] = true;
			}
		}
		if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
			keyEvent.type = KeyEvent.KEY_UP;
			if (keyCode > 0 && keyCode < 127) {
				pressed[keyCode] = false;
			}
		}
		buffer.add(keyEvent);
		return false;
	}

	public boolean isKeyPressed(int keyCode) {
		if (keyCode > MIN_KEY_CODE && keyCode < MAX_KEY_CODE) {
			return false;
		}
		return pressed[keyCode];
	}
	
	public synchronized List<KeyEvent> getKeyEvents() {
		for (KeyEvent event : events) {
			pool.freeObject(event);
		}
		events.clear();
		events.addAll(buffer);
		buffer.clear();
		return events;
	}
	
}
