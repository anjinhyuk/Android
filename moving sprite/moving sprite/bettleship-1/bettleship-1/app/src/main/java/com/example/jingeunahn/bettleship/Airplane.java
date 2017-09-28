package com.example.jingeunahn.bettleship;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by anjin on 11/24/2015.
 */
public class Airplane extends Enemy {
    /**
     * using image files
     */
    public static final int Bplane = R.drawable.big_airplane;
    public static final int Mplane = R.drawable.medium_airplane;
    public static final int Lplane = R.drawable.little_airplane;
    private Size s;

    /**
     * determining sizes by id
     * @param id
     */
    public Airplane(int id) {
        if (id == Bplane) {
            s = Size.BIG;
            loc = new RectF();
            loc.left = 0.6f;
            loc.top = 0.05f;
            b = GameView.loadImage(Bplane);
        }
        if(id == Mplane) {
            s = Size.MEDIUM;
            loc = new RectF();
            loc.left = 0.4f;
            loc.top = 0.15f;
            b = GameView.loadImage(Mplane);
        }
        if(id == Lplane){
            s = Size.LITTLE;
            loc = new RectF();
            loc.left = 0.15f;
            loc.top = 0.1f;
            b = GameView.loadImage(Lplane);
        }
    }
    @Override
    protected float relativeWidth(){
        if (s == Size.BIG) {
            return 0.09f;
        }if (s == Size.MEDIUM){
            return 0.07f;
        }if (s == Size.LITTLE) {
            return 0.03f;
        }
        return 0;
    }
    @Override
    protected float relativeHeight() {
        if (s == Size.BIG) {
            return 0.09f;
        }if (s == Size.MEDIUM){
            return 0.07f;
        }if (s == Size.LITTLE) {
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
        float ranX = -(float)(0.01f * Math.random());
        loc.offset(ranX, 0);
        if(loc.left > -.05) {
            float randX = -(float) (.03*Math.random());
            loc.offset(randX, 0);
            /**
             * changing speed after disappearing
             */
        } else {
            float ranY = (float)(Math.random() * -.2f) + .2f;
            loc.top = ranY;
            loc.left = 1;
        }

    }

}
