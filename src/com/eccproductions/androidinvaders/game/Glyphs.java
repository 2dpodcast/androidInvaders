package com.eccproductions.androidinvaders.game;

import java.util.HashMap;
import java.util.Map;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

public class Glyphs
{
	private static final String LOG = Glyphs.class.getSimpleName();

	// Bitmap for our glyphs sheet
	private Bitmap bitmap;

	// make a map to associate bitmap with each character
	private Map<Character, Bitmap> glyphs = new HashMap<Character, Bitmap>(80);

	// width and height of each individual character
	private int width;
	private int height;

	// Character list
	private char[] charactersLower = new char[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private char[] charactersUpper = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private char[] numbers = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	private char[] symbols = new char[] { '!', '@', '£', '$', '%', '^', '&', '*', '(', ')', '-', '+', '=', '"', '.', ',', ':', ';' };

	public Glyphs(Bitmap bitmap)
	{
		super();
		this.bitmap = bitmap;
		this.width = 8;
		this.height = 12;

		// cut up the bitmap into our glyphs
		// top row - charactersLower
		for (int i = 0; i < 26; i++)
		{
			glyphs.put(charactersLower[i], Bitmap.createBitmap(bitmap, 0 + (i * width), 0, width, height));
		}
		Log.d(LOG, "Lowercase initialized");

		// next row - charactersUpper
		for (int i = 0; i < 26; i++)
		{
			glyphs.put(charactersUpper[i], Bitmap.createBitmap(bitmap, 0 + (i * width), 15, width, height));
		}
		Log.d(LOG, "Uppercase initialized");

		// next row - numbers
		for (int i = 0; i < 10; i++)
		{
			glyphs.put(numbers[i], Bitmap.createBitmap(bitmap, 0 + (i * width), 30, width, height));
		}
		Log.d(LOG, "Numbers initialized");

		// next row - symbols
		for (int i = 0; i < 18; i++)
		{
			glyphs.put(symbols[i], Bitmap.createBitmap(bitmap, 0 + (i * width), 45, width, height));
		}
		Log.d(LOG, "Symbols initialized");
	}

	public Bitmap getBitmap()
	{
		return bitmap;
	}

	public void drawString(Canvas canvas, String text, int x, int y)
	{
		if (canvas == null)
		{
			Log.d(LOG, "Canvas is NULL.");
		}
		for (int i = 0; i < text.length(); i++)
		{
			Character ch = text.charAt(i);
			if (glyphs.get(ch) != null)
			{
				canvas.drawBitmap(glyphs.get(ch), (x + (i * width)), y, null);
			}
		}
		Log.d(LOG, "String '" + text + "' drawn at: " + x + "," + y);
	}
}
