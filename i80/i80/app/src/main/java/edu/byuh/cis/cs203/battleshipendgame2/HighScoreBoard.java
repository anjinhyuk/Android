package edu.byuh.cis.cs203.battleshipendgame2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class HighScoreBoard extends Activity {

	private HighScore currentScore;
	private List<HighScore> scores;
	private LinearLayout content;
	public TextView input;

	public static final String FILENAME = "scores.txt";

	public static final String SCORE_KEY = "score";
	public static final String PLAY_AGAIN_KEY = "playAgain";
	public static final int ID = 1;

	/**
	 *
	 * @param savedInstanceState
     */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		Intent data = getIntent();
		int score = data.getIntExtra(SCORE_KEY, 0);
		currentScore = new HighScore(score);
		scores = loadThem();
		Collections.sort(scores);
		TextView tv = new TextView(this);
		String hs1 = getResources().getString(R.string.hs1);
		String hs2 = getResources().getString(R.string.hs2);
		String hs3 = getResources().getString(R.string.hs3);
		String hs4 = getResources().getString(R.string.hs4);
		String hs5 = getResources().getString(R.string.hs5);
		if (score > scores.get(0).getScore()) {
			tv.setText(hs1 + score + hs2 );
		} else {
			tv.setText(hs3+ score + hs4+ scores.get(0).getScore() + hs5);
		}
		//save it all to disk
		scores.add(currentScore);
		saveThem();

		TableLayout tl = new TableLayout(this);

		Collections.sort(scores);
		/**
		 * give 5 top values
		 */
		HighScore h;
		for (int i = 0; i < 5; i++) {
			h = scores.get(i);
			tl.addView(h.createRow(this));
		}

		content = new LinearLayout(this);
		content.setOrientation(LinearLayout.VERTICAL);
		content.addView(tv);

		content.addView(tl);

		LinearLayout buttons = new LinearLayout(this);
		LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		Button yesButton = new Button(this);
		Button noButton = new Button(this);
		yesButton.setText(R.string.yes);
		noButton.setText(R.string.no);
		yesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra(PLAY_AGAIN_KEY, true);
				setResult(Activity.RESULT_OK, data);
				saveThem();
				finish();
			}

		});
		noButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra(PLAY_AGAIN_KEY, false);
				setResult(Activity.RESULT_OK, data);
				saveThem();
				finish();
			}

		});

		buttons.addView(yesButton, parms);
		buttons.addView(noButton, parms);
		content.addView(buttons);
		setContentView(content);

	}

	private List<HighScore> loadThem() {
		List<HighScore> result = new ArrayList<HighScore>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			String jsonText = "";
			Scanner s = new Scanner(fis);
			while ( s.hasNext() ) jsonText += s.nextLine();
			s.close();
			JSONArray ja = new JSONArray(jsonText);
			for (int i=0; i<ja.length(); i++) {
				HighScore h = new HighScore(ja.getJSONObject(i));
				result.add(h);
			}
		} catch (Exception e) {
			/**
			 * give some default values
			 */
			String nic1 = getResources().getString(R.string.r1);
			String nic2 = getResources().getString(R.string.r2);
			String nic3 = getResources().getString(R.string.r3);
			String nic4 = getResources().getString(R.string.r4);
			String nic5 = getResources().getString(R.string.r5);
			result.add(new HighScore(nic1, 50));
			result.add(new HighScore(nic2, 40));
			result.add(new HighScore(nic3, 30));
			result.add(new HighScore(nic4, 20));
			result.add(new HighScore(nic5, 10));
		}
		return result;
	}

	/**
	 * save the current score
	 */
	private void saveThem() {
		try {
			currentScore.setText(input);
			JSONArray ja = new JSONArray();
			for (HighScore s : scores) {
				ja.put(s.saveState());
			}
			String jsonString = ja.toString();
			FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
			fos.write(jsonString.getBytes());
			fos.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * set the names and input
	 * @param e
     */
	public void setUserName(TextView e){
		input = e;
	}

}