package com.football.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.football.FootballController;
import com.football.R;

public class MainMenuActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.main_menu);

		((Button) this.findViewById(R.id.buttonNewGame)).setOnClickListener(this);
		((Button) this.findViewById(R.id.buttonLoadGame)).setOnClickListener(this);
		((Button) this.findViewById(R.id.buttonOptions)).setOnClickListener(this);
		((Button) this.findViewById(R.id.buttonQuit)).setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonNewGame:
				startActivity(new Intent(this, CreateNewGameActivity.class));
				break;
			case R.id.buttonLoadGame:

				break;
			case R.id.buttonOptions:

				break;
			case R.id.buttonQuit:
				this.finish();
				break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		FootballController.getInstance().destroy();
	}

}
