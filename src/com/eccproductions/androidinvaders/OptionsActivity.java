package com.eccproductions.androidinvaders;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class OptionsActivity extends Activity implements OnClickListener
{
	// LOG for debugging
	private static final String LOG = OptionsActivity.class.getSimpleName();

	//Variables
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.d(LOG, "onCreate called");

		// Set content to activity_options
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		// initialize links to activity_options
		initialize();

	}

	private void initialize()
	{
		Log.d(LOG, "initialize() called");

		// Link to ID
		
		// Set OnClickListener
		
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
				break;
			case R.id.btnMainOptions:
				Log.d(LOG, "btnMainOptions: Opening OptionsActivity");
				break;
			case R.id.btnMainExit:
				Log.d(LOG, "btnMainExit: Quitting");
				finish();
				break;
		}
	}
}
