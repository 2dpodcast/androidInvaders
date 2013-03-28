package com.eccproductions.androidinvaders.pawn.component;

import java.util.Random;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Particle
{
	//private static final String LOG = Particle.class.getSimpleName();

	// Variables
	public static final int STATE_ALIVE = 0;
	public static final int STATE_DEAD = 1;
	public static final int DEFAULT_LIFETIME = 200;
	public static final int MAX_DIMENSION = 5;
	public static final int MAX_SPEED = 10;
	private int state;		// Particle alive or dead
	private float width;	// Particle width & height
	private float height;
	private float x, y;		// Particles horizontal and vertical position
	private double xv, yv;	// Particles horizontal and vertical velocity
	private int age;		// Current age of the Particle
	private int lifetime;	// Particle dies at age:
	private int color;		// Particle color
	private Paint paint;	// An internal Paint name to avoid instantiation

	// Constructor
	public Particle(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.state = Particle.STATE_ALIVE;
		this.width = randInt(1, MAX_DIMENSION);
		this.height = this.width;
		this.lifetime = Particle.DEFAULT_LIFETIME;
		this.age = 0;
		this.xv = (randDouble(0.0, (double) (MAX_SPEED * 2)) - MAX_SPEED);
		this.yv = (randDouble(0.0, (double) (MAX_SPEED * 2)) - MAX_SPEED);
		this.color = Color.argb(255, randInt(0, 255), randInt(0, 255), randInt(0, 255));
		this.paint = new Paint(this.color);

		// smooth out diagonal velocity
		if (xv * xv + yv * yv > MAX_SPEED * MAX_SPEED)
		{
			xv *= 0.7;
			yv *= 0.7;
		}
	}

	// Update() method
	public void update()
	{
		if(this.isAlive())
		{
			//move our particle
			this.x += this.xv;
			this.y += this.yv;
			
			//fade our particle color
			int alpha = this.color >>> 24;
			alpha -= 2;
			
			//If our particle is transparent we're dead
			if(alpha <= 0)
			{
				this.state = STATE_DEAD;
			}
			//Otherwise, set our new color and increase age
			else
			{
				this.color = (this.color & 0x00ffffff) + (alpha << 24);
				this.paint.setAlpha(alpha);
				this.age++;
			}
			//If our particle reaches it lifetime we're dead
			if(this.age >= this.lifetime)
			{
				this.state = STATE_DEAD;
			}
		}
	}

	public void update(Rect container)
	{
		//Check collisions with container
		if(this.isAlive())
		{
			//Horizontal (Left is 0)
			if(this.x <= container.left || this.x >= container.right - this.width)
			{
				this.xv *= -1; 
			}
			//Vertical (Top is 0)
			if(this.y <= container.top || this.y >= container.bottom - this.height)
			{
				this.yv *= -1;
			}
		}
		this.update();
	}
	
	// Draw() method
	public void draw(Canvas canvas)
	{
		paint.setColor(this.color);
		canvas.drawRect(this.x, this.y, (this.x + this.width), (this.y + this.height), paint);
	}

	// Helper methods
	public int randInt(int min, int max)
	{
		Random random = new Random();
		int result = (random.nextInt(max - min) + min);
		return result;
	}

	public Double randDouble(Double min, Double max)
	{
		Double result = (Math.random() * (max - min) + min);
		return result;
	}

	public boolean isAlive()
	{
		boolean result = (this.state == Particle.STATE_ALIVE);
		return result;
	}

	public boolean isDead()
	{
		boolean result = (this.state == Particle.STATE_DEAD);
		return result;
	}

	public void reset(float x, float y)
	{
		this.state = Particle.STATE_ALIVE;
		this.x = x;
		this.y = y;
		this.age = 0;
	}
	// Getters & Setters
	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public float getWidth()
	{
		return width;
	}

	public void setWidth(float width)
	{
		this.width = width;
	}

	public float getHeight()
	{
		return height;
	}

	public void setHeight(float height)
	{
		this.height = height;
	}

	public float getX()
	{
		return x;
	}

	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public double getXv()
	{
		return xv;
	}

	public void setXv(double xv)
	{
		this.xv = xv;
	}

	public double getYv()
	{
		return yv;
	}

	public void setYv(double yv)
	{
		this.yv = yv;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public int getLifetime()
	{
		return lifetime;
	}

	public void setLifetime(int lifetime)
	{
		this.lifetime = lifetime;
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

}
