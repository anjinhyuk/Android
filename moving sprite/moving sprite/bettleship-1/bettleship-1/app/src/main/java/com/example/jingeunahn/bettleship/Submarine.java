package com.example.jingeunahn.bettleship;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by anjin on 11/24/2015.
 */
public class Submarine extends Enemy {
    /**
     * using image files
     */
    public static final int Bsub = R.drawable.big_submarine;
    public static final int Msub = R.drawable.medium_submarine;
    public static final int Lsub = R.drawable.little_submarine;
    private Size s;

    /**
     * determing sizes
     * @param id
     */
    public Submarine(int id) {
        if (id == Bsub) {
            s = Size.BIG;
            loc = new RectF();
            loc.left = 0.4f;
            loc.top = 0.6f;
            b = GameView.loadImage(Bsub);
        }
        if (id == Msub) {
            s = Size.MEDIUM;
            loc = new RectF();
            loc.left = 0.6f;
            loc.top = 0.8f;
            b = GameView.loadImage(Msub);
        }
        if (id == Lsub) {
            s = Size.LITTLE;
            loc = new RectF();
            loc.left = 0.2f;
            loc.top = 0.7f;
            b = GameView.loadImage(Lsub);
        }
    }

    protected float relativeWidth() {
        if (s == Size.BIG) {
            return 0.09f;
        }
        if (s == Size.MEDIUM) {
            return 0.07f;
        }
        if (s == Size.LITTLE) {
            return 0.03f;
        }
        return 0;
    }

    protected float relativeHeight() {
        if (s == Size.BIG) {
            return 0.09f;
        }
        if (s == Size.MEDIUM) {
            return 0.07f;
        }
        if (s == Size.LITTLE) {
            return 0.03f;
        }
        return 0;
    }
    /**
     * moving algorithm
     */
    public void move() {
        /**
         * initial speed
         */
        float ranX = (float)(0.01f * Math.random());
        loc.offset(ranX, 0);
        if(loc.left < 1.1) {
            float randX = (float) (.03*Math.random());
            loc.offset(randX, 0);
            /**
             * changing speed after disappearing
             */
        } else {
            float ranY = (float)(Math.random()*-.2f) + .8f;
            loc.top = ranY;
            loc.left = 0;
        }

    }
}
