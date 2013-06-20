package com.mrnom.android.game.screen.model;

public class Stain {
	public static final int TYPE_0 = 0;
	public static final int TYPE_1 = 1;
	public static final int TYPE_2 = 2;
	
	public int type;
	public int x;
	public int y;
	
	public Stain(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
}
