package com.mrnom.android.game.screen;

import java.util.List;

import android.graphics.Rect;

import com.mrnom.android.game.Assets;
import com.mrnom.android.game.Settings;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.input.TouchEvent;
import com.mrnom.ui.Screen;

public class HighscoreScreen extends Screen {
	private static final Rect BUTTON = new Rect(0, 416, 64, 480);
	private static final Rect TITLE = new Rect(64, 20, 256, 84);
	
	private final StringBuilder builder = new StringBuilder();
	
	public HighscoreScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		List<TouchEvent> touches = getGame().getInput().getTouchEvents();
		getGame().getInput().getKeyEvents();
		
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (BUTTON.contains(event.x, event.y)) {
					getGame().setScreen(new MainMenuScreen(getGame()));
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
				}
			}
		}
	}

	@Override
	public void present(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.background, 0, 0);
		graphics.drawPixmap(Assets.mainMenu, TITLE.left,  TITLE.top, 0, 42, 192, 42);
		graphics.drawPixmap(Assets.buttons, BUTTON.left, BUTTON.top, 0, 128, 64, 64);
		
		int x = 20, y = 100;
		for (int i = 0; i < Settings.highscores.length; ++i) {
			builder.setLength(0);
			builder.append(i).append(". ").append(Settings.highscores[i]);
			drawText(builder.toString(), x, y);
			y += 50;
		}
	}

	@Override
	public void dispose() {}
	
	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
