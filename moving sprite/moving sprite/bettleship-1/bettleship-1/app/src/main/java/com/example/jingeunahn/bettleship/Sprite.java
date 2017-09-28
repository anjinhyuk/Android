package com.example.jingeunahn.bettleship;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Created by anjin on 11/24/2015.
 */
public abstract class Sprite {
    /**
     * variables
     * @return
     */
    protected abstract float relativeWidth();
    protected abstract float relativeHeight();
    protected Bitmap b;
    protected RectF loc;
    protected int newPointWidth;
    protected int newPointHeight;

    public static float getScreenWidth, getScreenHeight;

    /**
     * scale the images
     * @param w width
     * @param h height
     */
    public void scale(float w, float h) {
        newPointWidth = (int) (w * relativeWidth());
        newPointHeight = (int) (h * relativeHeight());
        b = Bitmap.createScaledBitmap(b, newPointWidth, newPointHeight, true);
    }

    /**
     * drawing method
     * @param c
     */
    public void Draw (Canvas c) {
        int w = c.getWidth();
        int h = c.getHeight();
        /**
         * drawing part with size
         */
        c.drawBitmap (b, w * loc.left, h * loc.top, null);
    }

}

