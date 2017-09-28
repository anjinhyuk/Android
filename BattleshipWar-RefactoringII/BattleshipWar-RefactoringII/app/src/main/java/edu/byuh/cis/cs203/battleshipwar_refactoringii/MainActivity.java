package edu.byuh.cis.cs203.battleshipwar_refactoringii;

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



}
