package com.mrnom.game;

import com.mrnom.audio.Audio;
import com.mrnom.graphics.Graphics;
import com.mrnom.input.Input;
import com.mrnom.io.FileIO;
import com.mrnom.ui.Screen;

public interface Game {

	public Input getInput();
	
	public FileIO getFileIO();
	
	public Graphics getGraphics();
	
	public Audio getAudio();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getStartScreen();
	
}
