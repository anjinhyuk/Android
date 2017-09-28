package edu.byuh.cis.cs203.bw4;

import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deda on 1/19/2016.
 */
public class MyTimer extends Handler{
    /**
     * observer
     */
        private ArrayList<TickListener> listener;

    /**
     * instantiate
     */
        public MyTimer() {
            listener = new ArrayList<TickListener>();
            sendMessageDelayed(obtainMessage(0), 100);
        }

    /**
     * check TickListener
     * @param m
     */
        @Override
        public void handleMessage(Message m) {
            for (TickListener t: listener){
                t.tick();
            }
            sendMessageDelayed(obtainMessage(0), 100);
        }

    /**
     * subscribing for adding and unsubscribing for removing
     * @param t
     */
        public void subscribe(TickListener t){
            listener.add(t);
        }

        public void unsubscribe(TickListener t){
            listener.remove(t);
        }
}
//            plane1.move();
//            plane2.move();
//            plane3.move();
//            sub1.move();
//            sub2.move();
//            sub3.move();w
//            for (DepthCharge d : charges) {
//                d.move();
//                if (!d.isVisible()) {
//                    doomed.add(d);
//                }
//            }
//            for (Sprite s : doomed) {
//                charges.remove(s);
//            }
//            for (Missile b : missiles) {
//                b.move();
//                if (!b.isVisible()) {
//                    doomed.add(b);
//                }
//            }
//            for (Sprite s : doomed) {
//                missiles.remove(s);
//            }
//            doomed.clear();
//            if (charges.size() > 0 && dcCount % 10 == 0) {
//                dcSound.start();
//            }
//            invalidate();
//            dcCount++;
//            detectCollision();