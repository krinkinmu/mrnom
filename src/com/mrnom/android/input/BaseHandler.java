package com.mrnom.android.input;

import java.util.HashSet;
import java.util.Set;

public class BaseHandler {
	private final Set<HandlerListener> listeners = new HashSet<HandlerListener>();
	
	public void registerListener(HandlerListener listener) {
		listeners.add(listener);
	}
	
	public void unregisterListener(HandlerListener listener) {
		listeners.remove(listener);
	}
	
	protected void notifyListeners() {
		for (HandlerListener listener : listeners) {
			listener.dataChanged(this);
		}
	}
}
