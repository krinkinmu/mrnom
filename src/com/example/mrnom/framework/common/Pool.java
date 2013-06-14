package com.example.mrnom.framework.common;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
	private static final int INITIAL_SIZE = 100;
	private final List<T> freeObjects = new ArrayList<T>(INITIAL_SIZE);
	private final PoolObjectFactory<T> factory;
	
	public Pool(PoolObjectFactory<T> factory) {
		this.factory = factory;
	}
	
	public T newObject() {
		T object = null;
		if (freeObjects.isEmpty()) {
			object = factory.createObject();
		} else {
			object = freeObjects.remove(freeObjects.size() - 1);
		}
		return object;
	}
	
	public void freeObject(T object) {
		freeObjects.add(object);
	}
}
