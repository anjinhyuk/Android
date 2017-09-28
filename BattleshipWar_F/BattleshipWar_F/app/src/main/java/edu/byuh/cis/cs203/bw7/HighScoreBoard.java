package edu.byuh.cis.cs203.bw7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by anjin on 2016-02-04.
 */
public class HighScoreBoard extends Activity {

    private boolean playAgain;
    public static  final int ID = 49125;
    public static final String PLAYAGAIN = "playAgain";
    public String highscore;


    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        /**
         * inputting
         */
        try {
            FileInputStream fis = openFileInput("score.txt");
            Scanner s = new Scanner(fis);
            highscore =  ""+  s.nextInt();
            s.close();
        } catch (Exception e) {
            highscore =""+ 0;
        }
        /**
         * comparing scores
         */
        if(GameView.score > Integer.parseInt(highscore)) {
            highscore = "" + GameView.score;
        }
        //outputting here
        try {
            FileOutputStream fos = openFileOutput("score.txt", MODE_PRIVATE);
            fos.write(highscore.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        Intent in = getIntent();
        LinearLayout lnl = new LinearLayout(this);
        LinearLayout buttonRow = new LinearLayout(this);
        lnl.setOrientation(LinearLayout.VERTICAL);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams
                (0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        TextView tv = new TextView(this);

        /**
         * printing results
         */
        if (GameView.score >= Integer.parseInt(highscore)) {
            highscore = ""+GameView.score;
            tv.setText("Game Over! You got the best score " + highscore + " Do you want to keep playing");
        } else {
        tv.setText("Game Over! Your score is " + GameView.score + " Do you want to keep playing?");
        }


        lnl.addView(tv);
        playAgain = false;
        /**
         * buttons
         */
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

    /**
     * finishing method
     */
    private void end() {
        Intent fin = new Intent();
        fin.putExtra(PLAYAGAIN, playAgain);
        setResult(Activity.RESULT_OK, fin);
        finish();
    }
}
