package com.eccproductions.androidinvaders;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class SplashActivity extends Activity
{
	MediaPlayer splashSound;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		splashSound = MediaPlayer.create(SplashActivity.this, R.raw.splash_electro);
		splashSound.start();
		
		Thread timer = new Thread()
		{
			public void run()
			{
				try
				{
					sleep(5000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					Intent intent = new Intent("com.eccproductions.androidinvaders.action.MAINACTIVITY");
					startActivity(intent);
				}
			}
		};
		timer.start();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		splashSound.release();
		finish();
	}

}
