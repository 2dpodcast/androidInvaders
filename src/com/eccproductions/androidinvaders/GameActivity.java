package com.eccproductions.androidinvaders;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.eccproductions.androidinvaders.game.GamePanel;

public class GameActivity extends Activity
{
	// LOG for debugging
	private static final String LOG = GameActivity.class.getSimpleName();

	//Variables

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG, "onCreate called");

		// turn title off
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// make full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Set activity content to GamePanel
		super.onCreate(savedInstanceState);
		setContentView(new GamePanel(this));
		Log.d(LOG, "GamePanel set as ContentView");

	}

}
