package edu.byuh.cis.cs203.bw7;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SplashScreen extends Activity {

	private ImageView iv;
	@Override
	public void onCreate(Bundle b) {
		super.onCreate(b);
		iv = new ImageView(this);
		iv.setImageResource(R.drawable.splash);
		iv.setScaleType(ScaleType.FIT_XY);
		setContentView(iv);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		int w = iv.getWidth();
		int h = iv.getHeight();
		float x = me.getX();
		float y = me.getY();
		RectF questionMark = new RectF(0, 0.86f*h, 0.09f*w, h);
		RectF prefsBox = new RectF(0, 0, 0.09f*w, 0.15f*h);
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			if (questionMark.contains(x, y)) {
				AlertDialog.Builder ab = new AlertDialog.Builder(this);
				ab.setTitle("About Battleship War");
				ab.setMessage("BATTLESHIP WAR was built by the students of CS 203 at Brigham Young University Hawaii (Winter 2015/2016 semester).\n\nThis game was inspired by the Commodore 64 game \"Battleship War\" by Keith Meade, as published in the August 1984 issue of RUN Magazine.");
				ab.setNeutralButton("OK", null);
				ab.show();
			} else if (prefsBox.contains(x, y)) {
				Intent i = new Intent(this, Prefs.class);
				startActivity(i);
			} else {
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}
		return true;
	}
}
