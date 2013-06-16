package com.mrnom.android.game;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.mrnom.android.audio.AndroidAudio;
import com.mrnom.android.graphics.AndroidGraphics;
import com.mrnom.android.input.AndroidInput;
import com.mrnom.android.io.AndroidFileIO;
import com.mrnom.android.ui.FastRenderView;
import com.mrnom.audio.Audio;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.input.Input;
import com.mrnom.io.FileIO;
import com.mrnom.ui.Screen;

public abstract class AndroidGame extends Activity implements Game {
	private static final String TAG = "mrnom";
	private static final int LONG = 480;
	private static final int SHORT = 320;
	
	private FastRenderView view;
	private Graphics graphics;
	private Audio audio;
	private Input input;
	private FileIO files;
	
	private Screen screen;
	private WakeLock lock = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		if (Integer.parseInt(VERSION.SDK) < 17) {
			PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
			lock = manager.newWakeLock(PowerManager.FULL_WAKE_LOCK, TAG);
		} else {
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

		boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		int width = isLandscape ? LONG : SHORT;
		int height = isLandscape ? SHORT : LONG;
		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);

		int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
		int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
		float scaleX = (float) width  / displayWidth;
		float scaleY = (float) height / displayHeight;
		
		view = new FastRenderView(this, bitmap);
		graphics = new AndroidGraphics(getAssets(), bitmap);
		audio = new AndroidAudio(this);
		input = new AndroidInput(this, view, scaleX, scaleY);
		files = new AndroidFileIO(this);
		screen = getStartScreen();
		setContentView(view);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if (lock != null) {
			lock.acquire();
		}
		screen.resume();
		view.resume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (lock != null) {
			lock.release();
		}
		screen.pause();
		view.pause();
		if (isFinishing()) {
			screen.dispose();
		}
	}
	
	@Override
	public Input getInput() {
		return input;
	}
	
	@Override
	public FileIO getFileIO() {
		return files;
	}
	
	@Override
	public Graphics getGraphics() {
		return graphics;
	}
	
	@Override
	public Audio getAudio() {
		return audio;
	}
	
	@Override
	public void setScreen(Screen screen) {
		if (screen == null) {
			throw new NullPointerException("screen must not be null");
		}
		this.screen.pause();
		this.screen.dispose();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	@Override
	public Screen getCurrentScreen() {
		return screen;
	}
}
