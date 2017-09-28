package com.example.jingeunahn.bettleship;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by anjin on 11/24/2015.
 */
public class Battleship extends Sprite {
    /**
     * declare singleton
     */
    private static Battleship Singleton;

    /**
     * loading image with size
     */
    private Battleship(){
        b = GameView.loadImage(R.drawable.battleship);
        loc = new RectF();
        loc.left = 0.25f;
        loc.top = 0.31f;
    }

    public static Battleship getInstance() {
        if (Singleton == null) {
            Singleton = new Battleship();
        }
        return Singleton;
    }

    @Override
    protected float relativeWidth() {
        return 0.45f;
    }

    @Override
    protected float relativeHeight() {
        return 0.23f;
    }
}
