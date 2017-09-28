package edu.byuh.cis.cs203.battleshipwar_refactoringii;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Created by anjin on 2016-01-27.
 */
public class Prefs extends PreferenceActivity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        addPreferencesFromResource(R.xml.prefs);
    }

    public static boolean getSoundPref(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("Sound", true);
    }

    public static boolean getRapidMissiles(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("rapidMissiles", false);
    }

    public static boolean getRapidCharges(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("rapidCharges", false);
    }

    public static String getPlaneDirection(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("Direction", "Random");
    }

    public static  String getSubDirection(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("Direction", "Random");
    }

    public static String getPlaneSpeed(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("Speed", "Medium");
    }

    public static String getSubSpeed(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("Speed", "Medium");
    }

    public static Integer getNumPlane(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt("Number", 5);
    }

    public static Integer getNumSub(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getInt("Number", 5);
    }

    public static boolean getFrugality(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("Frugality", false);
    }
}
