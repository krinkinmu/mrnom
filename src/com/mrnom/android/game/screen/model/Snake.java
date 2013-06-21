package com.mrnom.android.game.screen.model;

import java.util.ArrayList;
import java.util.List;

public class Snake {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	private static final int DIRECTIONS = 4;
	
	public List<SnakePart> parts = new ArrayList<SnakePart>();
	public int direction;
	
	public Snake(int direction, int x, int y) {
		this.direction = direction;
		this.parts.add(new SnakePart(x, y));
	}
	
	public void turnLeft() {
		direction = (direction + 1) % DIRECTIONS;
	}
	
	public void turnRight() {
		direction = (direction + DIRECTIONS - 1) % DIRECTIONS;
	}
	
	public void eat() {
		SnakePart end = parts.get(parts.size() - 1);
		parts.add(new SnakePart(end.x, end.y));
	}
	
	public void advance() {
		SnakePart head = parts.get(0);
		int len = parts.size() - 1;
		for (int i = len; i > 0; --i) {
			SnakePart before = parts.get(i - 1);
			SnakePart part = parts.get(i);
			part.x = before.x;
			part.y = before.y;
		}
		switch (direction) {
		case UP:
			head.y -= 1;
			break;
		case LEFT:
			head.x -= 1;
			break;
		case DOWN:
			head.y += 1;
			break;
		case RIGHT:
			head.x += 1;
			break;
		default:
			break;
		}
		if (head.x >= World.WORLD_WIDTH) {
			head.x = 0;
		}
		if (head.x < 0) {
			head.x = World.WORLD_WIDTH - 1;
		}
		if (head.y >= World.WORLD_HEIGHT) {
			head.y = 0;
		}
		if (head.y < 0) {
			head.y = World.WORLD_HEIGHT - 1;
		}
	}
	
	public boolean checkBitten() {
		SnakePart head = parts.get(0);
		for (int i = 1; i < parts.size(); ++i) {
			SnakePart part = parts.get(i);
			if (head.x == part.x && head.y == part.y) {
				return true;
			}
		}
		return false;
	}
}
