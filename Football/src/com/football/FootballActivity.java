package com.football;

import com.football.gui.MainMenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class FootballActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		FootballController.getInstance().init(this);
		
		/*
		 * TODO: CALL SPLASH SCREEN
		 */
		
		/*
		 * TODO: CALL MENU SCREEN
		 */
		
		/*
		 * TEST - CALL MANAGE TEAM ACTIVITY
		 */
		startActivity(new Intent(this,MainMenuActivity.class));
		this.finish();
		
		
		/*
		 * String[] names = this.context.getResources().getStringArray(R.array.channel_names);
			String[] urls = this.context.getResources().getStringArray(R.array.channel_urls);
		 */
	}

}