package com.eccproductions.androidinvaders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{
	// LOG for debugging
	private static final String LOG = MainActivity.class.getSimpleName();

	// Variables
	Button btnMainStart, btnMainOptions, btnMainExit;
	TextView tvMainTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG, "onCreate called");

		// turn title off
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Set content to activity_main
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(LOG, "ContentView set to activity_main");

		// initialize links to activity_main
		initialize();
	}

	private void initialize()
	{
		Log.d(LOG, "initialize() called");

		// Link to ID
		tvMainTitle = (TextView) findViewById(R.id.tvMainTitle);
		btnMainStart = (Button) findViewById(R.id.btnMainStart);
		btnMainOptions = (Button) findViewById(R.id.btnMainOptions);
		btnMainExit = (Button) findViewById(R.id.btnMainExit);
		
		// Set OnClickListener
		btnMainStart.setOnClickListener(this);
		btnMainOptions.setOnClickListener(this);
		btnMainExit.setOnClickListener(this);
		
		Log.d(LOG, "initialize() finished");

	}

	@Override
	public void onClick(View v)
	{
		//Check what was clicked
		switch (v.getId())
		{
			case R.id.btnMainStart:
				Log.d(LOG, "btnMainStart: Opening GameActivity");
				Intent gameStart = new Intent("com.eccproductions.androidinvaders.action.GAMEACTIVITY");
				startActivity(gameStart);
				break;
			case R.id.btnMainOptions:
				Log.d(LOG, "btnMainOptions: Opening OptionsActivity");
				Intent gameOptions = new Intent("com.eccproductions.androidinvaders.action.OPTIONSACTIVITY");
				startActivity(gameOptions);
				break;
			case R.id.btnMainExit:
				Log.d(LOG, "btnMainExit: Quitting");
				finish();
				break;
		}
	}

}
