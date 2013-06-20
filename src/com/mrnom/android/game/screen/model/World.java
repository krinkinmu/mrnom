package com.mrnom.android.game.screen.model;

import java.util.Random;

public class World {
	private static final int WORLD_WIDTH = 10;
	private static final int WORLD_HEIGHT = 13;
	private static final int SCORE_INCREMENT = 10;
	private static final float TICK_INITIAL = 0.5f;
	private static final float TICK_DECREMENT = 0.05f;

	public Snake snake = new Snake(Snake.UP, 6, 6);
	public Stain stain;
	
	public boolean gameOver = false;
	public int score = 0;
	
	private boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	private Random random = new Random();
	private float tickTime = 0.0f;
	private float tick = TICK_INITIAL;
	
	public World() {
		placeStain();
	}
	
	private void fillFields(boolean field) {
		for (int x = 0; x < WORLD_WIDTH; ++x) {
			for (int y = 0; y < WORLD_HEIGHT; ++y) {
				fields[x][y] = field;
			}
		}
	}
	
	private void placeStain() {
		fillFields(false);
		for (SnakePart part : snake.parts) {
			fields[part.x][part.y] = true;
		}
		int stainX = random.nextInt(WORLD_WIDTH);
		int stainY = random.nextInt(WORLD_HEIGHT);
		while (fields[stainX][stainY]) {
			stainX = random.nextInt(WORLD_WIDTH);
			stainY = random.nextInt(WORLD_HEIGHT);
		}
		stain = new Stain(random.nextInt(Stain.TYPE_2 + 1), stainX, stainY);
	}
	
	public void update(float delta) {
		if (gameOver) {
			return;
		}
		tickTime += delta;
		while (tickTime > tick) {
			tickTime -= tick;
			snake.advance();
			if (snake.checkBitten()) {
				gameOver = true;
				return;
			}
			SnakePart head = snake.parts.get(0);
			if (head.x == stain.x && head.y == stain.y) {
				score += SCORE_INCREMENT;
				snake.eat();
				if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
					gameOver = true;
					return;
				} else {
					placeStain();
				}
			}
			if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
				tick -= TICK_DECREMENT;
			}
		}
	}
}
