package com.eccproductions.androidinvaders.pawn;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;
import com.eccproductions.androidinvaders.pawn.component.Speed;

public class Droid
{
	// LOG for debugging
	private static final String LOG = Droid.class.getSimpleName();

	// Variables
	private Bitmap bitmap;		// The Bitmap
	private int x;				// X Co-ordinate
	private int y;				// Y Co-ordinate
	private boolean touched;	// If droid is touched (t/f)
	private Speed speed;		// Droid's speed / direction

	// Constructor
	public Droid(Bitmap bitmap, int x, int y)
	{
		Log.d(LOG, "Constructor called");

		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
		this.speed = new Speed();
	}

	// Update method
	public void update()
	{
		Log.d(LOG, "update() called");

		if (!touched)
		{
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}
	}

	// Draw method
	public void draw(Canvas canvas)
	{
		Log.d(LOG, "draw() called");
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	// handleActionDown method (manage touch events)
	public void handleActionDown(int eventX, int eventY)
	{
		Log.d(LOG, "handleActionDown() called");

		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2)))
		{
			if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2)))
			{
				// droid touched
				setTouched(true);
			}
			else
			{
				setTouched(false);
			}
		}
		else
		{
			setTouched(false);
		}
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

	public boolean isTouched()
	{
		return touched;
	}

	public void setTouched(boolean touched)
	{
		this.touched = touched;
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
