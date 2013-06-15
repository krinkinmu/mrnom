package com.mrnom.android.ui;

import com.mrnom.android.game.AndroidGame;
import com.mrnom.game.Game;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class FastRenderView extends SurfaceView implements Runnable {
	private final Game game;
	private final Bitmap buffer;
	private final SurfaceHolder holder;
	private final Rect rect = new Rect();
	private volatile boolean isRunning = false;
	private Thread thread;

	public FastRenderView(AndroidGame game, Bitmap buffer) {
		super(game);
		this.game = game;
		this.buffer = buffer;
		this.holder = getHolder();
	}
	
	public void pause() {
		isRunning = false;
		while (true) {
			try {
				thread.join();
				return;
			} catch (InterruptedException e) {}
		}
	}
	
	public void resume() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	public void run() {
		long startTime = System.nanoTime();
		while (isRunning) {
			if (!holder.getSurface().isValid()) {
				continue;
			}
			float delta = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			game.getCurrentScreen().update(delta);
			game.getCurrentScreen().present(delta);
			
			Canvas canvas = holder.lockCanvas();
			canvas.getClipBounds(rect);
			canvas.drawBitmap(buffer, null, rect, null);
			holder.unlockCanvasAndPost(canvas);
		}
	}

}
