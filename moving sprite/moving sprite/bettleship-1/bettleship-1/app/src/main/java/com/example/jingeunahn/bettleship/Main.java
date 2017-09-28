package com.example.jingeunahn.bettleship;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class Main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gv = new GameView(this);
        setContentView(gv);

    }
}
