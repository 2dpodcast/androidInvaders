package com.eccproductions.androidinvaders.pawn.component;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Explosion
{
	private static final String LOG = Explosion.class.getSimpleName();

	// Varibles
	public static final int STATE_ALIVE = 0;	// at least 1 particle is alive
	public static final int STATE_DEAD = 1;		// all particles are dead
	private Particle[] particles;           	// particles in the explosion
	private int x, y;                       	// the explosion's origin
	private float gravity;						// the gravity of explosion (+ up, - down)
	private float wind;							// speed of wind on horizontal
	private int size;                       	// number of particles
	private int state;                      	// whether it's still active or not

	// Constructor
	public Explosion(int numOfParticles, int x, int y)
	{
		Log.d(LOG, "Explosion created at: (" + x + ", " + y + ")");
		this.state = Explosion.STATE_ALIVE;
		this.particles = new Particle[numOfParticles];
		this.size = numOfParticles;

		// Create particles
		for (int i = 0; i < this.particles.length; i++)
		{
			Particle p = new Particle(x, y);
			this.particles[i] = p;
		}

	}

	// Update
	public void update()
	{
		if (this.isAlive())
		{
			boolean isDead = true;
			for (int i = 0; i < this.particles.length; i++)
			{
				if (this.particles[i].isAlive())
				{
					this.particles[i].update();
					isDead = false;
				}
			}
			if (isDead)
			{
				this.state = STATE_DEAD;
			}
		}
	}

	public void update(Rect container)
	{
		if (this.isAlive())
		{
			boolean isDead = true;
			for (int i = 0; i < this.particles.length; i++)
			{
				if (this.particles[i].isAlive())
				{
					this.particles[i].update(container);
					isDead = false;
				}
			}
			if (isDead)
			{
				this.state = STATE_DEAD;
			}
		}

	}

	// Draw
	public void draw(Canvas canvas)
	{
		for(int i = 0; i < this.particles.length; i++)
		{
			if (this.particles[i].isAlive())
			{
				this.particles[i].draw(canvas);
			}
		}
	}

	// Helper methods
	public boolean isAlive()
	{
		boolean result = (this.state == Explosion.STATE_ALIVE);
		return result;
	}

	public boolean isDead()
	{
		boolean result = (this.state == Explosion.STATE_DEAD);
		return result;
	}

	// Getters & Setters
	public Particle[] getParticles()
	{
		return particles;
	}

	public void setParticles(Particle[] particles)
	{
		this.particles = particles;
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

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public float getGravity()
	{
		return gravity;
	}

	public void setGravity(float gravity)
	{
		this.gravity = gravity;
	}

	public float getWind()
	{
		return wind;
	}

	public void setWind(float wind)
	{
		this.wind = wind;
	}

}
