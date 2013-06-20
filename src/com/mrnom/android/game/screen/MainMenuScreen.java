package com.mrnom.android.game.screen;

import java.util.List;

import android.graphics.Rect;

import com.mrnom.android.game.Assets;
import com.mrnom.android.game.Settings;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.input.TouchEvent;
import com.mrnom.ui.Screen;

public class MainMenuScreen extends Screen {
	private static final Rect BACKGROUND_BOUNDS = new Rect(0, 0, 320, 480);
	private static final Rect LOGO_BOUNDS = new Rect(32, 20, 288, 180);
	private static final Rect SOUND_BUTTON_BOUNDS = new Rect(0, 416, 64, 480);
	private static final Rect PLAY_BUTTON_BOUNDS = new Rect(64, 220, 256, 262);
	private static final Rect HIGHSCORE_BUTTON_BOUNDS = new Rect(64, 262, 256, 304);
	private static final Rect HELP_BUTTON_BOUNDS = new Rect(64, 304, 256, 346);
	
	public MainMenuScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		List<TouchEvent> touches = getGame().getInput().getTouchEvents();
		getGame().getInput().getKeyEvents(); //drop key events
		
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (SOUND_BUTTON_BOUNDS.contains(event.x, event.y)) {
					Settings.sound = !Settings.sound;
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
				}
				if (PLAY_BUTTON_BOUNDS.contains(event.x, event.y)) {
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
					//getGame().setScreen(new GameScreen(getGame()));
					return;
				}
				if (HIGHSCORE_BUTTON_BOUNDS.contains(event.x, event.y)) {
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
					//getGame().setScreen(new HighscoreScreen(getGame()));
					return;
				}
				if (HELP_BUTTON_BOUNDS.contains(event.x, event.y)) {
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
					getGame().setScreen(new HelpScreen1(getGame()));
					return;
				}
			}
		}
	}

	@Override
	public void present(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.background, BACKGROUND_BOUNDS.left, BACKGROUND_BOUNDS.top);
		graphics.drawPixmap(Assets.logo, LOGO_BOUNDS.left, LOGO_BOUNDS.top);
		graphics.drawPixmap(Assets.mainMenu, PLAY_BUTTON_BOUNDS.left, PLAY_BUTTON_BOUNDS.top);
		if (Settings.sound) {
			graphics.drawPixmap(Assets.buttons, SOUND_BUTTON_BOUNDS.left, SOUND_BUTTON_BOUNDS.top, 0, 0, 64, 64);
		} else {
			graphics.drawPixmap(Assets.buttons, SOUND_BUTTON_BOUNDS.left, SOUND_BUTTON_BOUNDS.top, 64, 0, 64, 64);
		}
	}

	@Override
	public void pause() {
		Settings.store(getGame().getFileIO());
	}

	@Override
	public void resume() {}
	
	@Override
	public void dispose() {}

}
