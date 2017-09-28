package edu.byuh.cis.cs203.bw4;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements TickListener {

	private Bitmap water;
	private Battleship battleship;
	private Plane plane1, plane2, plane3;
	private Sub sub1, sub2, sub3;
	private boolean firstTime;
	private Paint paint;
	private static Resources res;
	private static int screenWidth, screenHeight;
	private MyTimer tim;
	private List<DepthCharge> charges;
	private List<Missile> missiles;
	private List<Plane> plane;
	private List<Sub> sub;
	private MediaPlayer dcSound, leftSound, rightSound, planeExplode, subExplode;
	private int score;
	private ArrayList<Sprite> gone;
	private List<Sprite> doomed;

	/**
	 * Constructor for our View subclass. Loads all the images
	 * @param context a reference to our main Activity class
	 */
	public GameView(Context context) {
		super(context);
		res = getResources();
		water = BitmapFactory.decodeResource(getResources(), R.drawable.water);
		battleship = Battleship.getInstance();
		firstTime = true;
		paint = new Paint();
		missiles = new ArrayList<Missile>();
		charges = new ArrayList<DepthCharge>();
		plane = new ArrayList<Plane>();
		sub = new ArrayList<Sub>();
		planeExplode = MediaPlayer.create(getContext(), R.raw.plane_explode);
		subExplode = MediaPlayer.create(getContext(), R.raw.sub_explode);
		dcSound = MediaPlayer.create(getContext(), R.raw.depth_charge);
		leftSound = MediaPlayer.create(getContext(), R.raw.left_gun);
		rightSound = MediaPlayer.create(getContext(), R.raw.right_gun);
		score = 0;
		for (Missile m: missiles){
			tim.subscribe(m);
		}
		for (DepthCharge d: charges){
			tim.subscribe(d);
		}
	}
	
	public static Bitmap myBitmapLoader(int id) {
		return BitmapFactory.decodeResource(res, id);
	}
	
	public static int getScreenWidth() {
		return screenWidth;
	}

	
	@Override
	public void onDraw(Canvas c) {
		c.drawColor(Color.WHITE);
		int w = c.getWidth();
		int h = c.getHeight();
		gone = new ArrayList<Sprite>();
		paint.setTextSize(70);
		c.drawText("Score: " + score, 15, h*0.58f, paint);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		if (firstTime) {
			firstTime = false;
			tim = new MyTimer();
			screenWidth = w;
			screenHeight = h;
			plane1 = new Plane(Size.LARGE, Direction.RIGHT_TO_LEFT);
			plane2 = new Plane(Size.MEDIUM, Direction.RIGHT_TO_LEFT);
			plane3 = new Plane(Size.SMALL, Direction.RIGHT_TO_LEFT);
			sub1 = new Sub(Size.LARGE, Direction.LEFT_TO_RIGHT);
			sub2 = new Sub(Size.MEDIUM, Direction.LEFT_TO_RIGHT);
			sub3 = new Sub(Size.SMALL, Direction.LEFT_TO_RIGHT);
			water = Bitmap.createScaledBitmap(water, w/45, h/30, true);
			battleship.scaleThyself(w, h);

			tim.subscribe(plane1);
			tim.subscribe(plane2);
			tim.subscribe(plane3);
			tim.subscribe(sub1);
			tim.subscribe(sub2);
			tim.subscribe(sub3);
			tim.subscribe(this);

			battleship.setLocation((w-battleship.getWidth())/2f,
					h/2-battleship.getHeight()+water.getHeight());

		}
		float waterX = 0;
		while (waterX < w) {
			c.drawBitmap(water, waterX, h/2, paint);
			waterX += water.getWidth();
		}
		/**
		 *  drowing and deleting
		 */
		battleship.draw(c);
		plane1.draw(c);
		plane2.draw(c);
		plane3.draw(c);
		sub1.draw(c);
		sub2.draw(c);
		sub3.draw(c);
		for (DepthCharge d : charges) {
			d.draw(c);
		}
		for (Missile m : missiles) {
			m.draw(c);
		}
		for (Missile m: missiles){
			if (!m.isVisible()) {
				tim.unsubscribe(m);
			}
		}
		for (DepthCharge d: charges){
			if (!d.isVisible()){
				tim.unsubscribe(d);
			}
		}

	}

	public static int getScreenHeight() {
		return screenHeight;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent me) {
		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			float x = me.getX();
			float y = me.getY();
			if (y > screenHeight/2) {
				DepthCharge dc = new DepthCharge();
				tim.subscribe(dc);
				dc.scaleThyself(screenWidth, screenHeight);
				dc.setLocation(screenWidth / 2, screenHeight * 0.52f);
				charges.add(dc);
			} else {
				Missile m;
				if (x < screenWidth/2) {
					m = new Missile(Direction.RIGHT_TO_LEFT);
					tim.subscribe(m);
					m.setLocation(screenWidth * 0.34f, screenHeight * 0.4f);
					leftSound.start();
				} else {
					m = new Missile(Direction.LEFT_TO_RIGHT);
					tim.subscribe(m);
					m.setLocation(screenWidth * 0.64f, screenHeight * 0.4f);
					rightSound.start();
				}
				m.scaleThyself(screenWidth, screenHeight);
				missiles.add(m);
			}
		}
		return true;
	}

	public void detectCollision() {
		plane.add(plane1);
		plane.add(plane2);
		plane.add(plane3);
		sub.add(sub1);
		sub.add(sub2);
		sub.add(sub3);
		for (Missile m : missiles) {
			for (Plane p : plane) {
				if (RectF.intersects(m.bounds, p.bounds)) {
					p.explode();
					score += p.getPointValue();
					planeExplode.start();
					gone.add(m);
					break;
				}
			}
		}
		for (Sprite g : gone){
			missiles.remove(g);
		}
		for (DepthCharge c: charges){
			for (Sub s : sub){
				if (RectF.intersects(c.bounds, s.bounds)){
					s.explode();
					score += s.getPointValue();
					subExplode.start();
					gone.add(c);
					break;
				}
			}
		}
		for (Sprite a: gone){
			charges.remove(a);
		}
		gone.clear();
	}
	int dcCount = 0;

	/**
	 * tick method
	 */
	@Override
	public void tick() {
		for(Missile m: missiles){
			if(!m.isVisible()){
				tim.unsubscribe(m);
				gone.add(m);
			}
		}
		for(Sprite m: gone){
			missiles.remove(m);
		}
		for (DepthCharge d: charges){
			if (!d.isVisible()){
				tim.unsubscribe(d);
				gone.add(d);
			}
		}
		for (Sprite d: gone){
			charges.remove(d);
		}
		gone.clear();

		if (charges.size() > 0 && dcCount % 10 == 0) {
			dcSound.start();
		}
		detectCollision();
		invalidate();
		dcCount++;
	}
}


//	public class MyTimer extends Handler {
//
//		private int dcCount;
//
//
//		public MyTimer() {
//			sendMessageDelayed(obtainMessage(0), 100);
//			dcCount = 0;
//			doomed = new ArrayList<Sprite>();
//		}
//
//		@Override
//		public void handleMessage(Message m) {
//			plane1.move();
//			plane2.move();
//			plane3.move();
//			sub1.move();
//			sub2.move();
//			sub3.move();
//			for (DepthCharge d : charges) {
//				d.move();
//				if (!d.isVisible()) {
//					doomed.add(d);
//				}
//			}
//			for (Sprite s : doomed) {
//				charges.remove(s);
//			}
//			for (Missile b : missiles) {
//				b.move();
//				if (!b.isVisible()) {
//					doomed.add(b);
//				}
//			}
//			for (Sprite s : doomed) {
//				missiles.remove(s);
//			}
//			doomed.clear();
//			if (charges.size() > 0 && dcCount % 10 == 0) {
//				dcSound.start();
//			}
//			invalidate();
//			dcCount++;
//			sendMessageDelayed(obtainMessage(0), 100);
//			detectCollision();
//		}
//
//	}

