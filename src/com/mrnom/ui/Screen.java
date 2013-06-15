package com.mrnom.ui;

import com.mrnom.common.Disposable;
import com.mrnom.game.Game;

public abstract class Screen implements Disposable {
	private final Game game;
	
	public Screen(Game game) {
		this.game = game;
	}
	
	public abstract void update(float delta);
	public abstract void present(float delta);
	public abstract void pause();
	public abstract void resume();
	
	protected Game getGame() {
		return game;
	}
}
