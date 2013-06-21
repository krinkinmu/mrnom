package com.mrnom.ui;

import com.mrnom.android.game.Assets;
import com.mrnom.common.Disposable;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;

public abstract class Screen implements Disposable {
	private static final int CHAR_WIDTH = 20;
	private static final int CHAR_HEIGHT = 32;
	private final Game game;
	
	public Screen(Game game) {
		this.game = game;
	}
	
	public abstract void update(float delta);
	public abstract void present(float delta);
	public abstract void pause();
	public abstract void resume();
	
	protected void drawText(String line, int x, int y) {
		Graphics graphics = getGame().getGraphics();
		for (int i = 0; i < line.length(); ++i) {
			char c = line.charAt(i);
			if (c == '.') {
				graphics.drawPixmap(Assets.numbers, x, y, CHAR_WIDTH * 10, 0, CHAR_WIDTH, CHAR_HEIGHT);
			}
			if (c >= '0' && c <= '9') {
				graphics.drawPixmap(Assets.numbers, x, y, CHAR_WIDTH * (c - '0'), 0, CHAR_WIDTH, CHAR_HEIGHT);
			}
			x += CHAR_WIDTH;
		}
	}
	
	protected Game getGame() {
		return game;
	}
}
