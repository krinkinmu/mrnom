package com.mrnom.audio;

import com.mrnom.common.Disposable;

public interface Music extends Disposable {

	public boolean isLooping();
	public boolean isPlaying();
	public boolean isStopped();
	
	public void setLooping(boolean isLooping);
	public void setVolume(float volume);
	
	public void play();
	public void pause();
	public void stop();
	
}
