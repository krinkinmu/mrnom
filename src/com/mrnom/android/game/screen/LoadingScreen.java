package com.mrnom.android.game.screen;

import com.mrnom.android.game.Assets;
import com.mrnom.android.game.Settings;
import com.mrnom.audio.Audio;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.graphics.PixmapFormat;
import com.mrnom.ui.Screen;

public class LoadingScreen extends Screen {
	
	public LoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float delta) {
		Graphics graphics = getGame().getGraphics();
		Assets.background = graphics.newPixmap("images/background.png", PixmapFormat.RGB565);
		Assets.logo = graphics.newPixmap("images/logo.png", PixmapFormat.ARGB4444);
		Assets.mainMenu = graphics.newPixmap("images/mainmenu.png", PixmapFormat.ARGB4444);
		Assets.buttons = graphics.newPixmap("images/buttons.png", PixmapFormat.ARGB4444);
		Assets.help1 = graphics.newPixmap("images/help1.png", PixmapFormat.ARGB4444);
		Assets.help2 = graphics.newPixmap("images/help2.png", PixmapFormat.ARGB4444);
		Assets.help3 = graphics.newPixmap("images/help3.png", PixmapFormat.ARGB4444);
		Assets.numbers = graphics.newPixmap("images/numbers.png", PixmapFormat.ARGB4444);
		Assets.ready = graphics.newPixmap("images/ready.png", PixmapFormat.ARGB4444);
		Assets.pause = graphics.newPixmap("images/pause.png", PixmapFormat.ARGB4444);
		Assets.gameOver = graphics.newPixmap("images/gameover.png", PixmapFormat.ARGB4444);
		Assets.headUp = graphics.newPixmap("images/headup.png", PixmapFormat.ARGB4444);
		Assets.headDown = graphics.newPixmap("images/headdown.png", PixmapFormat.ARGB4444);
		Assets.headLeft = graphics.newPixmap("images/headleft.png", PixmapFormat.ARGB4444);
		Assets.headRight = graphics.newPixmap("images/headright.png", PixmapFormat.ARGB4444);
		Assets.tail = graphics.newPixmap("images/tail.png", PixmapFormat.ARGB4444);
		Assets.stain1 = graphics.newPixmap("images/stain1.png", PixmapFormat.ARGB4444);
		Assets.stain2 = graphics.newPixmap("images/stain2.png", PixmapFormat.ARGB4444);
		Assets.stain3 = graphics.newPixmap("images/stain3.png", PixmapFormat.ARGB4444);
		
		Audio audio = getGame().getAudio();
		Assets.click = audio.newSound("sounds/click.ogg");
		Assets.eat = audio.newSound("sounds/eat.ogg");
		Assets.bitten = audio.newSound("sounds/bitten.ogg");
		
		Settings.load(getGame().getFileIO());
		getGame().setScreen(new MainMenuScreen(getGame()));
	}

	@Override
	public void present(float delta) {
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

}
