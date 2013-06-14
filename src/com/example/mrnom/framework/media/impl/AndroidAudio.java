package com.example.mrnom.framework.media.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.mrnom.framework.media.Audio;
import com.example.mrnom.framework.media.Music;
import com.example.mrnom.framework.media.Sound;

public class AndroidAudio implements Audio {
	private static final int MAX_STREAMS = 20;
	private final AssetManager assets;
	private final SoundPool pool;

	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.pool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
	}
	
	@Override
	public Music newMusic(String fileName) {
		try {
			AssetFileDescriptor fd = assets.openFd(fileName);
			return new AndroidMusic(fd);
		} catch (IOException e) {
			throw new RuntimeException("could not load music " + fileName + "!");
		}
	}

	@Override
	public Sound newSound(String fileName) {
		try {
			AssetFileDescriptor fd = assets.openFd(fileName);
			int id = pool.load(fd, 0);
			return new AndroidSound(pool, id);
		} catch (IOException e) {
			throw new RuntimeException("could not load sound " + fileName + "!");
		}
	}

}
