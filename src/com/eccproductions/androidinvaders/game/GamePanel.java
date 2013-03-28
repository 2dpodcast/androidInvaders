package com.eccproductions.androidinvaders.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.eccproductions.androidinvaders.R;
import com.eccproductions.androidinvaders.pawn.Droid;
import com.eccproductions.androidinvaders.pawn.component.Explosion;
import com.eccproductions.androidinvaders.pawn.component.Speed;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
	private static final String LOG = GamePanel.class.getSimpleName();

	private static final int EXPLOSION_SIZE = 200;
	private GameThread thread;
	private String avgFps;
	private Explosion[] explosionArray;
	private Explosion explosion;
	private Droid droid1;

	public GamePanel(Context context)
	{
		super(context);

		// add a callback (this) to the surface handler to intercept events
		getHolder().addCallback(this);

		// create Droid and load bitmap // bitmap, x, y
		droid1 = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid), 50, 50);

		// create the game loop thread
		thread = new GameThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		// surface is created, start game loop
		thread.setRunning(true);
		thread.start();

		// Set up explosions
		explosionArray = new Explosion[10];
		for (int i = 0; i < explosionArray.length; i++)
		{
			explosionArray[i] = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		// surface is about to be destroyed, stop game loop
		boolean retry = true;
		while (retry)
		{
			try
			{
				thread.printStats();
				thread.setRunning(false);
				thread.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
				// try again shutting down the thread
			}
		}
		Log.d(LOG, "Thread was shut down cleanly.");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			// delegate event handling to droid
			droid1.handleActionDown((int) event.getX(), (int) event.getY());

			// check if in the lower part of the screen - if true we exit
			if (event.getY() > getHeight() - 50)
			{
				thread.setRunning(false);
				((Activity) getContext()).finish();
			}
			else
			{
				Log.d(LOG, "Coords: x=" + event.getX() + ", y=" + event.getY());
				int currentExplosion = 0;
				explosion = explosionArray[currentExplosion];

				if (explosion == null || explosion.isDead())
				{
					explosion = new Explosion(EXPLOSION_SIZE, (int) event.getX(), (int) event.getY());
					explosionArray[currentExplosion] = explosion;
				}

				if ((explosion != null) && (explosion.isAlive()) && (currentExplosion < explosionArray.length))
				{
					currentExplosion++;
					if (currentExplosion >= explosionArray.length)
					{
						currentExplosion = 0;
					}
					explosionArray[currentExplosion] = explosion;
				}
			}
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			// the gestures
			if (droid1.isTouched())
			{
				// the droid was picked up and is being dragged
				droid1.setX((int) event.getX());
				droid1.setY((int) event.getY());
			}
		}
		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			// touch was released
			if (droid1.isTouched())
			{
				droid1.setTouched(false);
			}
		}
		return true;
	}

	public void setAvgFps(String avgFps)
	{
		this.avgFps = avgFps;
	}

	protected void render(Canvas canvas)
	{
		// fill canvas with black
		canvas.drawColor(Color.BLACK);

		// Render explosions
		if (explosion != null)
		{
			explosion.draw(canvas);
		}

		// Render our droid
		droid1.draw(canvas);

		// Display FPS
		displayFps(canvas, avgFps);
	}

	private void displayFps(Canvas canvas, String fps)
	{
		if (canvas != null && fps != null)
		{
			Paint paint = new Paint();
			paint.setARGB(255, 0, 255, 0);
			canvas.drawText(fps, (this.getWidth() - 60), 20, paint);
		}
	}

	public void update()
	{
		// check collision with right wall if heading right
		if (droid1.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT && droid1.getX() + droid1.getBitmap().getWidth() / 2 >= getWidth())
		{
			droid1.getSpeed().toggleXDirection();
		}
		// check collision with left wall if heading left
		if (droid1.getSpeed().getxDirection() == Speed.DIRECTION_LEFT && droid1.getX() - droid1.getBitmap().getWidth() / 2 <= 0)
		{
			droid1.getSpeed().toggleXDirection();
		}
		// check collision with bottom wall if heading down
		if (droid1.getSpeed().getyDirection() == Speed.DIRECTION_DOWN && droid1.getY() + droid1.getBitmap().getHeight() / 2 >= getHeight())
		{
			droid1.getSpeed().toggleYDirection();
		}
		// check collision with bottom wall if heading down
		if (droid1.getSpeed().getyDirection() == Speed.DIRECTION_UP && droid1.getY() - droid1.getBitmap().getHeight() / 2 <= 0)
		{
			droid1.getSpeed().toggleYDirection();
		}

		// Update explosions
		if (explosion != null)
		{
			explosion.update();
		}

		// update Droid
		droid1.update();
	}
}