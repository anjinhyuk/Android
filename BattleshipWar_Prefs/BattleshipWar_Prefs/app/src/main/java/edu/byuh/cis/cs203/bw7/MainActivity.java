package edu.byuh.cis.cs203.bw7;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Paint;
import android.view.Menu;

public class MainActivity extends Activity {

	private GameView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gv = new GameView(this);
		setContentView(gv);
	}

	//helper method I made up one day, to work around the
	//lack of documentation about font sizes in Android.
	public static float findThePerfectFontSize(float dim) {
		float fontSize = 1;
		Paint p = new Paint();
		p.setTextSize(fontSize);
		float lowerThreshold = dim;
		while (true) {
			float asc = -p.getFontMetrics().ascent;
			if (asc > lowerThreshold) {
				break;
			}
			fontSize++;
			p.setTextSize(fontSize);
		}
		return fontSize;
	}
	@Override
	protected void onActivityResult(int requestCode,
									int resultCode,
									Intent data) {
		if(requestCode == HighScoreBoard.ID) {
			if(resultCode == Activity.RESULT_OK) {
				boolean playAgain = data.getBooleanExtra("playAgain", false);
				if (playAgain) {
					gv.timeLeft = 10;
					gv.score = 0;
				} else {
					finish();
				}
			}
		}
	}


}
