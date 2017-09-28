package com.example.jingeunahn.bettleship;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
//view class
public class GameView extends View {

    private boolean initialized = false;
    private static Resources res;
    private Airplane bp, mp, lp;
    private Submarine bs, ms, ls;
    private Battleship bat;
    private Bitmap water;

    /**
     * Timer and Handler
     */
    private class Timer extends Handler {

        public Timer() {
            sendMessageDelayed(obtainMessage(), 50);
        }

        /**
         * make things move every 50 milliseconds
         */
            @Override
            public void handleMessage(Message m) {
                bp.move();
                mp.move();
                lp.move();
                bs.move();
                ms.move();
                ls.move();
                invalidate();
                sendMessageDelayed(obtainMessage(), 50);
            }
    }

    /**
     * factoring method
     * @param pic
     * @return
     */
    public static Bitmap loadImage(int pic) {
        return BitmapFactory.decodeResource(res, pic);
    }

    /**
     * @param context
     */
    public GameView(Context context) {
        super(context);
        initialized = false;
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        //get valuables for width and height of creen
        int w = getWidth();
        int h = getHeight();
        //paint method
        Paint forbitmap = new Paint();
        c.drawColor(Color.WHITE);

        if (!initialized) {
            Timer t = new Timer();
            res = getResources();
            bat = Battleship.getInstance();
            bp = new Airplane(Airplane.Bplane);
            bp.scale(w, h);
            mp = new Airplane(Airplane.Mplane);
            mp.scale(w, h);
            lp = new Airplane(Airplane.Lplane);
            lp.scale(w, h);
            bs = new Submarine(Submarine.Bsub);
            bs.scale(w, h);
            ms = new Submarine(Submarine.Msub);
            ms.scale(w, h);
            ls = new Submarine(Submarine.Lsub);
            ls.scale(w, h);
            bat.scale(w, h);
            water = BitmapFactory.decodeResource(res, R.drawable.water);
            initialized = true;

            /**
             * drawing
             */
        }
        bp.Draw(c);
        mp.Draw(c);
        lp.Draw(c);
        bs.Draw(c);
        ms.Draw(c);
        ls.Draw(c);
        bat.Draw(c);
        water = water.createScaledBitmap(water, (int) (w * 0.02), (int) (w * 0.02), true);

        //positions
        for (int i = 0; i < w; i += water.getWidth()) {
            c.drawBitmap(water, i, h * 0.5f, forbitmap);
        }

    }
}
