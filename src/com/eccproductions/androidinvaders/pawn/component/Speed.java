package com.eccproductions.androidinvaders.pawn.component;

import android.util.Log;

public class Speed
{
	private static final String LOG = Speed.class.getSimpleName();

	// Variables
	public static final int DIRECTION_UP = -1;
	public static final int DIRECTION_DOWN = 1;
	public static final int DIRECTION_LEFT = -1;
	public static final int DIRECTION_RIGHT = 1;
	private float xv = 1; // velocity on X-axis
	private float yv = 1; // velocity on Y-axis
	private int xDirection = DIRECTION_RIGHT;
	private int yDirection = DIRECTION_DOWN;

	// Constructor
	public Speed()
	{
		Log.d(LOG, "Constructor called");

		this.xv = 1;
		this.yv = 1;
	}

	public Speed(float xv, float yv)
	{
		Log.d(LOG, "Fancy Constructor called");
		this.xv = xv;
		this.yv = yv;
	}

	// swap direction on x-axis
	public void toggleXDirection()
	{
		xDirection = (xDirection * -1);
	}

	// swap direction on y-axis
	public void toggleYDirection()
	{
		yDirection = (yDirection * -1);
	}

	// Getters & Setters
	public float getXv()
	{
		return xv;
	}

	public void setXv(float xv)
	{
		this.xv = xv;
	}

	public float getYv()
	{
		return yv;
	}

	public void setYv(float yv)
	{
		this.yv = yv;
	}

	public int getxDirection()
	{
		return xDirection;
	}

	public void setxDirection(int xDirection)
	{
		this.xDirection = xDirection;
	}

	public int getyDirection()
	{
		return yDirection;
	}

	public void setyDirection(int yDirection)
	{
		this.yDirection = yDirection;
	}
}
