package edu.byuh.cis.cs203.battleshipendgame2;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScore implements Comparable<HighScore> {

	private String name;
	private Integer score;

	public static final String NAME_KEY = "name";
	public static final String SCORE_KEY = "score";
	public TableRow tr;

	public HighScore(int s) {
		this(null, s);
	}

	/**
	 * another constructor for name and score
	 * @param name
	 * @param s
	 */
	public HighScore(String name, int s){
		this.name = name;
		this.score = s;
	}

	public HighScore(JSONObject j) throws JSONException {
		name = j.optString(NAME_KEY, null);
		score = j.getInt(SCORE_KEY);
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(HighScore another) {
		return -score.compareTo(another.score);
	}

	public JSONObject saveState() throws JSONException {
		JSONObject jo = new JSONObject();
		jo.put(NAME_KEY, name);
		jo.put(SCORE_KEY, score);
		Log.d("CS203", "saving " + name + " and " + score);
		return jo;
	}

	/**
	 * score table method
	 * @param h
	 * @return
     */
	public TableRow createRow(HighScoreBoard h){
		TextView left = new TextView(h);
		TextView right = new TextView(h);
		if (name != null){
			left.setText("" + name);
			/**
			 * when new score appear
			 */
		} else if (name == null) {
			left = new EditText(h);
			final TextView finalLeft = left;
						//hint method
			left.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean b) {
					if (b) {
						finalLeft.setHint("Enter Your Name Here");
					}
				}
			});
			h.setUserName(left);

		}
		tr = new TableRow(h);
		right.setText(""+ score);
		tr.addView(left);
		tr.addView(right);
		return tr;
	}

	/**
	 * bring values
	 * @param t
     */
	public void setText(TextView t){
		if ( t != null )
			name = t.getText().toString();

	}

}