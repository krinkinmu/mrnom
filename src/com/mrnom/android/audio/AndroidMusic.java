package com.mrnom.android.audio;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.mrnom.audio.Music;

public class AndroidMusic implements Music, OnCompletionListener {
	private final MediaPlayer player;
	private boolean isPrepared = false;
	
	public AndroidMusic(AssetFileDescriptor descriptor) {
		player = new MediaPlayer();
		try {
			player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getDeclaredLength());
			player.prepare();
			isPrepared = true;
			player.setOnCompletionListener(this);
		} catch (Exception e) {
			throw new RuntimeException("Could not load music!");
		}
	}

	@Override
	public void dispose() {
		if (isPlaying()) {
			stop();
		}
		player.release();
	}

	@Override
	public boolean isLooping() {
		return player.isLooping();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void setLooping(boolean looping) {
		player.setLooping(looping);
	}

	@Override
	public void setVolume(float volume) {
		player.setVolume(volume, volume);
	}

	@Override
	public synchronized boolean isStopped() {
		return !isPrepared;
	}
	
	@Override
	public synchronized void play() {
		if (isPlaying()) {
			return;
		}
		try {
			if (isStopped()) {
				player.prepare();
			}
			player.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void pause() {
		if (isPlaying()) {
			player.pause();
		}
	}

	@Override
	public synchronized void stop() {
		player.stop();
		isPrepared = false;
	}

	@Override
	public synchronized void onCompletion(MediaPlayer mp) {
		isPrepared = false;
	}

}
