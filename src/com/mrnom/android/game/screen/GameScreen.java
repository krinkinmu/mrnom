package com.mrnom.android.game.screen;

import java.util.List;

import android.graphics.Color;
import android.graphics.Rect;

import com.mrnom.android.game.Assets;
import com.mrnom.android.game.Settings;
import com.mrnom.android.game.screen.model.Snake;
import com.mrnom.android.game.screen.model.SnakePart;
import com.mrnom.android.game.screen.model.Stain;
import com.mrnom.android.game.screen.model.World;
import com.mrnom.game.Game;
import com.mrnom.graphics.Graphics;
import com.mrnom.graphics.Pixmap;
import com.mrnom.input.TouchEvent;
import com.mrnom.ui.Screen;

public class GameScreen extends Screen {

	private enum GameState {
		Ready,
		Running,
		Paused,
		GameOver
	}
	
	private static final int CHAR_WIDTH = 20;
	private static final int SCORE_HEIGHT = 42;
	private static final int CELL_WIDTH = 32;
	private static final int CELL_HEIGHT = 32;
	private static final int WORLD_LINE_HEIGHT = 416;
	
	private static final Rect RESUME_BUTTON = new Rect(80, 100, 240, 148);
	private static final Rect QUIT_BUTTON = new Rect(80, 148, 240, 196);
	private static final Rect PAUSE_BUTTON = new Rect(0, 0, 64, 64);
	private static final Rect LEFT_BUTTON = new Rect(0, 416, 64, 480);
	private static final Rect RIGHT_BUTTON = new Rect(256, 416, 320, 480);
	private static final Rect CLOSE_BUTTON = new Rect(128, 200, 192, 264);
	private static final Rect GAME_OVER_TITLE = new Rect(62, 100, 158, 150);
	private static final Rect READY_TITLE = new Rect(47, 100, 272, 196);
	
	private GameState state = GameState.Ready;
	private World world = new World();
	private int oldScore = 0;
	private String scoreString = "0";
	
	public GameScreen(Game game) {
		super(game);
	}
	
	@Override
	public void update(float delta) {
		List<TouchEvent> touches = getGame().getInput().getTouchEvents();
		getGame().getInput().getKeyEvents();
		
		if (state == GameState.Ready) {
			readyUpdate(touches, delta);
		} else if (state == GameState.Paused) {
			pausedUpdate(touches, delta);
		} else if (state == GameState.Running) {
			runningUpdate(touches, delta);
		} else if (state == GameState.GameOver) {
			gameOverUpdate(touches, delta);
		}
	}
	
	private void readyUpdate(List<TouchEvent> touches, float delta) {
		if (!touches.isEmpty()) {
			state = GameState.Running;
		}
	}
	
	private void pausedUpdate(List<TouchEvent> touches, float delta) {
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (RESUME_BUTTON.contains(event.x, event.y)) {
					state = GameState.Running;
				} else if (QUIT_BUTTON.contains(event.x, event.y)) {
					getGame().setScreen(new MainMenuScreen(getGame()));
					return;
				}
			}
		}
	}
	
	private void runningUpdate(List<TouchEvent> touches, float delta) {
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (PAUSE_BUTTON.contains(event.x, event.y)) {
					state = GameState.Paused;
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
					return;
				}
			}
			if (event.type == TouchEvent.TOUCH_DOWN) {
				if (LEFT_BUTTON.contains(event.x, event.y)) {
					world.snake.turnLeft();
				} else if (RIGHT_BUTTON.contains(event.x, event.y)) {
					world.snake.turnRight();
				}
			}
		}
		world.update(delta);
		if (world.gameOver) {
			state = GameState.GameOver;
			if (Settings.sound) {
				Assets.bitten.play(1.0f);
			}
		}
		if (oldScore != world.score) {
			oldScore = world.score;
			scoreString = "" + oldScore;
			if (Settings.sound) {
				Assets.eat.play(1.0f);
			}
		}
	}
	
	private void gameOverUpdate(List<TouchEvent> touches, float delta) {
		for (TouchEvent event : touches) {
			if (event.type == TouchEvent.TOUCH_UP) {
				if (CLOSE_BUTTON.contains(event.x, event.y)) {
					getGame().setScreen(new MainMenuScreen(getGame()));
					if (Settings.sound) {
						Assets.click.play(1.0f);
					}
					return;
				}
			}
		}
	}

	@Override
	public void present(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.background, 0, 0);
		drawWorld();
		
		if (state == GameState.Ready) {
			readyPresent(delta);
		} else if (state == GameState.Paused) {
			pausedPresent(delta);
		} else if (state == GameState.Running) {
			runningPresent(delta);
		} else if (state == GameState.GameOver) {
			gameOverPresent(delta);
		}
		drawText(scoreString, graphics.getWidth() / 2 - scoreString.length() * CHAR_WIDTH, graphics.getHeight() - SCORE_HEIGHT);
	}
	
	private void drawWorld() {
		Graphics graphics = getGame().getGraphics();
		Snake snake = world.snake;
		SnakePart head = snake.parts.get(0);
		Stain stain = world.stain;
		
		Pixmap stainPixmap = null;
		if (stain.type == Stain.TYPE_0) {
			stainPixmap = Assets.stain1;
		} else if (stain.type == Stain.TYPE_1) {
			stainPixmap = Assets.stain2;
		} else if (stain.type == Stain.TYPE_2) {
			stainPixmap = Assets.stain3;
		}
		int stainX = getCellX(stain.x);
		int stainY = getCellY(stain.y);
		graphics.drawPixmap(stainPixmap, stainX, stainY);
		
		for (int i = 1; i < snake.parts.size(); ++i) {
			SnakePart part = snake.parts.get(i);
			int partX = getCellX(part.x);
			int partY = getCellY(part.y);
			graphics.drawPixmap(Assets.tail, partX, partY);
		}
		
		Pixmap headPixmap = null;
		if (snake.direction == Snake.UP) {
			headPixmap = Assets.headUp;
		} else if (snake.direction == Snake.DOWN) {
			headPixmap = Assets.headDown;
		} else if (snake.direction == Snake.LEFT) {
			headPixmap = Assets.headLeft;
		} else if (snake.direction == Snake.RIGHT) {
			headPixmap = Assets.headRight;
		}
		int headX = getCellX(head.x) + (CELL_WIDTH - headPixmap.getWidth()) / 2;
		int headY = getCellY(head.y) + (CELL_WIDTH - headPixmap.getHeight()) / 2;
		graphics.drawPixmap(headPixmap, headX, headY);
		
		graphics.drawLine(0, WORLD_LINE_HEIGHT, graphics.getWidth(), WORLD_LINE_HEIGHT, Color.BLACK);
	}
	
	private void readyPresent(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.ready, READY_TITLE.left, READY_TITLE.top);
	}
	
	private void pausedPresent(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.pause, READY_TITLE.left, READY_TITLE.top);
	}
	
	private void runningPresent(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.buttons, PAUSE_BUTTON.left, PAUSE_BUTTON.top, 64, 128, 64, 64);
		graphics.drawPixmap(Assets.buttons, LEFT_BUTTON.left, LEFT_BUTTON.top, 64, 64, 64, 64);
		graphics.drawPixmap(Assets.buttons, RIGHT_BUTTON.left, RIGHT_BUTTON.top, 0, 64, 64, 64);
	}
	
	private void gameOverPresent(float delta) {
		Graphics graphics = getGame().getGraphics();
		graphics.drawPixmap(Assets.gameOver, GAME_OVER_TITLE.left, GAME_OVER_TITLE.top);
		graphics.drawPixmap(Assets.buttons, CLOSE_BUTTON.left, CLOSE_BUTTON.top, 0, 128, 64, 64);
	}
	
	private int getCellX(int x) {
		return x * CELL_WIDTH;
	}
	
	private int getCellY(int y) {
		return y * CELL_HEIGHT;
	}

	@Override
	public void pause() {
		if (state == GameState.Running) {
			state = GameState.Paused;
		}
		if (state == GameState.GameOver) {
			Settings.newScore(oldScore);
			Settings.store(getGame().getFileIO());
		}
	}

	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {}

}
