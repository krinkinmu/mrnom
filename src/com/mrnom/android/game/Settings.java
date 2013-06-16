package com.mrnom.android.game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.mrnom.io.FileIO;

public class Settings {
	private static final String SETTINGS_FILE_NAME = ".settings";
	
	public static boolean sound = true;
	public static int[] highscores = new int[] { 100, 80, 50, 30, 10 };
	
	public static void load(FileIO files) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(files.readFile(SETTINGS_FILE_NAME)));
			sound = Boolean.parseBoolean(reader.readLine());
			for (int i = 0; i < highscores.length; ++i) {
				highscores[i] = Integer.parseInt(reader.readLine());
			}
		} catch (IOException e) {
		} catch (NumberFormatException e) {
		} finally {
			if (reader != null) {
				try { reader.close(); } catch (IOException e) {}
			}
		}
	}
	
	public static void store(FileIO files) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(files.writeFile(SETTINGS_FILE_NAME)));
			writer.write(Boolean.toString(sound));
			for (int i = 0; i < highscores.length; ++i) {
				writer.write(Integer.toString(highscores[i]));
			}
		} catch (IOException e) {
		} finally {
			if (writer != null) {
				try { writer.close(); } catch (IOException e) {}
			}
		}
	}
	
	public static void newScore(int score) {
		for (int i = 0; i < highscores.length; ++i) {
			if (highscores[i] < score) {
				for (int j = highscores.length - 1; j > i; --j) {
					highscores[j] = highscores[j - 1];
				}
				highscores[i] = score;
				break;
			}
		}
	}
}
