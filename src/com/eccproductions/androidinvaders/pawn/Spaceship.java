package com.eccproductions.androidinvaders.pawn;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import com.eccproductions.androidinvaders.pawn.component.Speed;

public class Spaceship
{
	// LOG for debugging
	private static final String LOG = Spaceship.class.getSimpleName();

	// Variables
	private Bitmap bitmap;		// The Bitmap
	private int x;				// X Co-ordinate
	private int y;				// Y Co-ordinate
	private Speed speed;		// Aliens's speed / direction

	// Constructor
	public Spaceship(Bitmap bitmap, int x, int y)
	{
		Log.d(LOG, "Constructor called");

		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed();
	}

	// Move method
	public void move()
	{
		Log.d(LOG, "move() called");
		x += (speed.getXv() * speed.getxDirection());

	}

	// Update method
	public void update()
	{
		Log.d(LOG, "update() called");
		move();
	}

	// Draw method
	public void draw(Canvas canvas)
	{
		Log.d(LOG, "draw() called");
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	// Getters & Setters
	public Bitmap getBitmap()
	{
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Speed getSpeed()
	{
		return speed;
	}

	public void setSpeed(Speed speed)
	{
		this.speed = speed;
	}

}
