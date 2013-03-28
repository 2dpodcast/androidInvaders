package com.eccproductions.androidinvaders.game;

import java.text.DecimalFormat;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread
{
	// TODO: Tidy up GameThread
	// TODO: Tidy up statistics

	// LOG for debugging
	private static final String LOG = GameThread.class.getSimpleName();

	// FPS
	private final static int MAX_FPS = 50;
	private final static int MAX_FRAME_SKIPS = 5;
	private final static int FRAME_PERIOD = (1000 / MAX_FPS);

	// Statistics
	private DecimalFormat df = new DecimalFormat("0.##");
	// time between checks
	private final static int STAT_INTERVAL = 1000;
	// num of stat checks to keep
	private final static int FPS_HISTORY_NR = 10;
	// last time a status was checked
	private long lastStatusStore = 0;
	// num of status checks
	private long statusIntervalTimer = 0l;
	// total num of frames skipped
	private long totalFramesSkipped = 0l;
	// num frames skipped in cycle
	private long framesSkippedPerStatCycle = 0l;
	// Num rendered frames in interval
	private int frameCountPerStatCycle = 0;
	// total num of frames rendered
	private long totalFrameCount = 0l;
	// the last (FPS_HISTORY_NR) FPS values
	private double fpsStore[];
	// num times the stats are read
	private long statsCount = 0;
	// average FPS since game started
	private double averageFps = 0.0;

	private SurfaceHolder surfaceHolder;
	private GamePanel gamePanel;
	private boolean running;

	public void setRunning(boolean running)
	{
		this.running = running;
	}

	public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
	{
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;

		Log.d(LOG, "GameThread constructor called");
	}

	@Override
	public void run()
	{
		Log.d(LOG, "Starting game loop");

		// initialize timing elements for stat gathering
		initTimingElements();

		Canvas canvas;			// Our game's canvas
		long beginTime;			// time when the cycle began
		long timeDiff;			// time it took the cycle to complete
		int sleepTime;			// ms to sleep (if the cycle is behind)
		int framesSkipped;		// number of frames skipped

		sleepTime = 0;

		while (running)
		{
			canvas = null;
			try
			{
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder)
				{
					// log the time cycle began
					beginTime = System.currentTimeMillis();
					// reset skipped frames counter
					framesSkipped = 0;

					// Update game state
					this.gamePanel.update();
					// render state to screen
					this.gamePanel.render(canvas);

					// calculate how long the cycle took
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int) (FRAME_PERIOD - timeDiff);

					if (sleepTime > 0)
					{
						// Optimium result - wait until update is due
						try
						{
							Thread.sleep(sleepTime);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS)
					{
						// Bad - we need to catch up!
						// Update without rendering
						this.gamePanel.update();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}

					if (framesSkipped > 0)
					{
						Log.d(LOG, "Skipped: " + framesSkipped);
					}
					// increment skips (if they happened)
					framesSkippedPerStatCycle += framesSkipped;
					// call routine to store gathered stats
					storeStats();
				}
			}
			finally
			{
				// in case of an exception the surface is not left in an
				// inconsistent state
				if (canvas != null)
				{
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		// end run()
	}

	private void storeStats()
	{
		frameCountPerStatCycle++;
		totalFrameCount++;

		// check actual time
		statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

		if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL)
		{
			// calculate the actual frames per status check interval
			double actualFps = (double) (frameCountPerStatCycle / (STAT_INTERVAL / 1000));

			// store the latest fps in the array
			fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;

			// increase the number of times stats were calculated
			statsCount++;

			double totalFps = 0.0;
			// sum up stored fps values
			for (int i = 0; i < FPS_HISTORY_NR; i++)
			{
				totalFps += fpsStore[i];
			}

			// obtain the average
			if (statsCount < FPS_HISTORY_NR)
			{
				// in the case of first N triggers
				averageFps = totalFps / statsCount;
			}
			else
			{
				averageFps = totalFps / FPS_HISTORY_NR;
			}

			// saving the num of total frames skipped
			totalFramesSkipped += framesSkippedPerStatCycle;

			// resetting counters after a status record (1sec)
			framesSkippedPerStatCycle = 0;
			statusIntervalTimer = 0;
			frameCountPerStatCycle = 0;

			statusIntervalTimer = System.currentTimeMillis();
			lastStatusStore = statusIntervalTimer;
			// Log.d(TAG, "Average FPS: " + df.format(averageFps));
			gamePanel.setAvgFps("FPS: " + df.format(averageFps));
		}

	}

	public void printStats()
	{
		Log.d(LOG, "Average FPS: " + df.format(averageFps));
		Log.d(LOG, "Total Frame Count: " + df.format(totalFrameCount));
		Log.d(LOG, "Total Frames Skipped: " + df.format(totalFramesSkipped));
	}

	private void initTimingElements()
	{
		// Initialize timing elements
		fpsStore = new double[FPS_HISTORY_NR];
		for (int i = 0; i < FPS_HISTORY_NR; i++)
		{
			fpsStore[i] = 0.0;
		}
		Log.d(LOG + ".initTimingElements()", "Timing elements for stats initialized");
	}
}
