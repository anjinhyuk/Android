package edu.byuh.cis.cs203.bw4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by deda on 1/21/2016.
 */
public class SplashScreen extends Activity {

    public ImageView iv;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        iv = new ImageView(this);
        iv.setBackgroundResource(R.drawable.title);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        setContentView(iv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent m){
        /* splash screen.
         */
        if(m.getAction() == MotionEvent.ACTION_DOWN){
            float x = m.getX();
            float y = m.getY();
           if (x < iv.getWidth()*0.4 && y > iv.getHeight()*0.8){
                // about button with instruction
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage("If you touch half of the top right then shots right, the top left then shots left and bottom half, the depthCharge goes")
                        .setTitle("About")
                        .setNeutralButton("OK",null);
                ab.show();
            } else {
                // main activity
                Intent play = new Intent(this, MainActivity.class);
                startActivity(play);
                finish();
            }
        }
        return true;
    }
}
