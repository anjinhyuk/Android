package edu.byuh.cis.cs203.battleshipwar_refactoringii;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class Preferences extends PreferenceActivity {

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        addPreferencesFromResource(R.xml.prefs);
    }

    public static boolean getSoundEffects(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("SoundEffects", false);
    }

    public static boolean getRapidFireMissile(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("RapidFireMissiles", false);
    }

    public static boolean getRapidCharges(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("RapidFireCharges", false);
    }

    public static int getPlaneNumber(Context c) {
        return Integer.parseInt(
                PreferenceManager.getDefaultSharedPreferences(c)
                        .getString("NumberOfPlanes", "3"));
    }

    public static int getSubNumber(Context c) {
        return Integer.parseInt(
                PreferenceManager.getDefaultSharedPreferences(c)
                        .getString("NumberOfSubs", "3"));
    }

    public static boolean getFrugality(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getBoolean("Frugality", false);
    }

}
