package com.mrnom.android.game;

import com.mrnom.android.game.screen.LoadingScreen;
import com.mrnom.ui.Screen;

public class MrNomGame extends AndroidGame {
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}
