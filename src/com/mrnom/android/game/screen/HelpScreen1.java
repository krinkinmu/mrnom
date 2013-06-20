package com.mrnom.android.game.screen;

import java.util.List;

import android.graphics.Rect;

import com.mrnom.android.game.Assets;
import com.mrnom.android.game.Settings;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.input.TouchEvent;
import com.mrnom.ui.Screen;

public class HelpScreen1 extends Screen {
	private static final Rect BUTTON = new Rect(256, 416, 320, 480);
	private static final Rect HELP = new Rect(64, 100, 256, 356);
	
	public HelpScreen1(Game game) {
		super(game);
	}

	@Override
	public void dispose() {}

	@Override
	public void update(float delta) {
		List<TouchEvent> touches = getGame().getInput().getTouchEvents();
		getGame().getInput().getKeyEvents();
		
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (BUTTON.contains(event.x, event.y)) {
					getGame().setScreen(new HelpScreen2(getGame()));
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
		graphics.drawPixmap(Assets.help1, HELP.left, HELP.top);
		graphics.drawPixmap(Assets.buttons, BUTTON.left, BUTTON.top, 0, 64, 64, 64);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

}
