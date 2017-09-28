package edu.byuh.cis.cs203.bw7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

/**
 * Created by anjin on 2016-02-02.
 */
public class HighScoreBoard extends Activity {

    private boolean playAgain;
    public static  final int ID = 49125;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        Intent in = getIntent();
        LinearLayout lnl = new LinearLayout(this);
        LinearLayout buttonRow = new LinearLayout(this);
        lnl.setOrientation(LinearLayout.VERTICAL);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        TextView tv = new TextView(this);
        tv.setText("Game Over!");
        lnl.addView(tv);


        playAgain = false;
        Button yesButton = new Button(this);
        yesButton.setText("YES");
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgain = true;
                end();
            }
        });

        Button noButton = new Button(this);
        noButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                playAgain = false;
                end();
            }
        });
        noButton.setText("No");
        buttonRow.addView(yesButton, parms);
        buttonRow.addView(noButton, parms);
        lnl.addView(buttonRow);

        setContentView(lnl);
    }

    private void end() {
        Intent fin = new Intent();
        fin.putExtra("playAgain", playAgain);
        setResult(Activity.RESULT_OK, fin);
        finish();
    }


//    private void read() {
//        FileInputStream fis = openFileInput(score.txt);
//        Scanner s = new Scanner(fis);
//        int highScore = s.nextInt();
//        s.close();
//    }
//
//    private void write() {
//        FileOutputStream fos = openFileOutput(score.txt);
//        String scott = ""+GameView.score;
//        fos.write(scott.getBytes());
//        fos.close();
//    }
}
