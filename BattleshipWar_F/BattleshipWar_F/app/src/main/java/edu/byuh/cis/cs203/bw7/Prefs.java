package edu.byuh.cis.cs203.bw7;

import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.widget.Toast;

public class Prefs extends PreferenceActivity{
	//option names and default values
	private static final String OPT_SOUND = "soundfx";
	private static final String OPT_RAPID_GUNS = "rapid_guns";
	private static final String OPT_RAPID_DC = "rapid_dc";
	private static final String OPT_MINUTES = "minutes";
	private static final String OPT_NUM_SUBS = "num_subs";
	private static final String OPT_NUM_PLANES = "num_planes";
	private static final String OPT_PLANE_SPEED = "plane_speed";
	private static final String OPT_SUB_SPEED = "sub_speed";

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.options);		
	}

	//Get the current value of the sound option
	public static boolean getSoundFX(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_SOUND, true);
	}

	public static boolean getRapidGuns(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_RAPID_GUNS, true);
	}

	public static boolean getRapidDC(Context context){
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean(OPT_RAPID_DC, false);
	}

//	public static int getGameLength(Context context) {
//		String tmp = PreferenceManager.getDefaultSharedPreferences(context)
//				.getString(OPT_MINUTES, "180");
//		return Integer.parseInt(tmp);
//	}
	
	public static int getNumPlanes(Context context) {
		String tmp = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(OPT_NUM_PLANES, "3");
		return Integer.parseInt(tmp);
	}
	
	public static int getNumSubs(Context context) {
		String tmp = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(OPT_NUM_SUBS, "3");
		return Integer.parseInt(tmp);
	}
	
	public static float getPlaneSpeed(Context context) {
		String tmp = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(OPT_PLANE_SPEED, "0.00625");
		return Float.parseFloat(tmp);
	}
	public static float getSubSpeed(Context context) {
		String tmp = PreferenceManager.getDefaultSharedPreferences(context)
				.getString(OPT_SUB_SPEED, "0.00625");
		return Float.parseFloat(tmp);
	}

}
