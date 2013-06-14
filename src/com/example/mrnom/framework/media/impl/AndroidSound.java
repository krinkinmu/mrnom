package com.example.mrnom.framework.media.impl;

import android.media.SoundPool;

import com.example.mrnom.framework.media.Sound;

public class AndroidSound implements Sound {
	private final int id;
	private final SoundPool pool;
	
	public AndroidSound(SoundPool pool, int id) {
		this.id = id;
		this.pool = pool;
	}

	@Override
	public void dispose() {
		pool.unload(id);
	}

	@Override
	public void play(float volume) {
		pool.play(id, volume, volume, 0, 0, 1);
	}

}
