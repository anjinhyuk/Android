package edu.byuh.cis.cs203.bw7;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements TickListener {

	private Bitmap water;
	private Battleship battleship;
	private List<Plane> planes;
	private List<Sub> subs;
	private boolean firstTime;
	private Paint paint;
	private static Resources res;
	private static int screenWidth, screenHeight;
	private MyTimer tim;
	private List<DepthCharge> charges;
	private List<Missile> missiles;
	private MediaPlayer dcSound, leftSound, rightSound, airExplosion, waterExplosion;
	public static int score;
	private List<Sprite> doomed;//for "concurrent modification error" avoidance
	private int dcCount; //for timing the depth charge "beep" sound
	public static int timeLeft =10;
	public long timeBefore;
	public long timeNow;
	public int minLeft;
	public int secLeft;
	public String zero;

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
		planes = new ArrayList<Plane>();
		subs = new ArrayList<Sub>();
		missiles = new ArrayList<Missile>();
		charges = new ArrayList<DepthCharge>();
		doomed = new ArrayList<Sprite>();
		dcSound = MediaPlayer.create(getContext(), R.raw.depth_charge);
		leftSound = MediaPlayer.create(getContext(), R.raw.left_gun);
		rightSound = MediaPlayer.create(getContext(), R.raw.right_gun);
		airExplosion = MediaPlayer.create(getContext(), R.raw.plane_explode);
		waterExplosion = MediaPlayer.create(getContext(), R.raw.sub_explode);
		dcCount = 0;
		score = 0;
	}

	public static Bitmap myBitmapLoader(int id) {
		return BitmapFactory.decodeResource(res, id);
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	private void detectCollisions() {
		//timer algorithm
		timeNow =System.currentTimeMillis();
		if (timeNow - timeBefore >= 1000){
			timeLeft --;
			timeBefore = timeNow;
		}
		if (timeLeft >= 180) {
			minLeft = 3;
			secLeft = (timeLeft % 60);
			if (secLeft >= 10) {
				zero = "";
			} else {
				zero = "0";
			}
		} else if (timeLeft < 180 && timeLeft >= 120) {
			minLeft = 2;
			secLeft = (timeLeft % 60);
			if (secLeft >= 10) {
				zero = "";
			} else {
				zero = "0";
			}
		} else if (timeLeft <120 && timeLeft >= 60) {
			minLeft = 1;
			secLeft = (timeLeft % 60);
			if (secLeft >= 10) {
				zero = "";
			} else {
				zero = "0";
			}
		} else {
			minLeft = 0;
			secLeft = (timeLeft % 60);
			if (secLeft >= 10) {
				zero = "";
			} else {
				zero = "0";
			}
		}

		for (Sub s : subs) {
			for (DepthCharge d : charges) {
				if (d.overlaps(s)) {
					s.explode();
					if (Prefs.getSoundFX(getContext())) {
						waterExplosion.start();
					}
					score += s.getPointValue();
					doomed.add(d);
				}
			}
		}
		for (DepthCharge d : charges) {
			if (!d.isVisible()) {
				doomed.add(d);
			}
		}
		for (Sprite s : doomed) {
			charges.remove(s);
			//tim.removeListener(s);
		}


		for (Plane p : planes) {
			for (Missile m : missiles) {
				if (p.overlaps(m)) {
					p.explode();
					if (Prefs.getSoundFX(getContext())) {
						airExplosion.start();
					}
					score += p.getPointValue();
					doomed.add(m);
				}
			}
		}
		for (Missile b : missiles) {
			if (!b.isVisible()) {
				doomed.add(b);
			}
		}
		for (Sprite s : doomed) {
			missiles.remove(s);
			//tim.removeListener(s);
		}
		doomed.clear();
		if (charges.size() > 0 && dcCount % 10 == 0) {
			if (Prefs.getSoundFX(getContext())) {
				dcSound.start();
			}
		}
		dcCount++;

	}


	@Override
	public void onDraw(Canvas c) {
		c.drawColor(Color.WHITE);
		int w = c.getWidth();
		int h = c.getHeight();
		if (firstTime) {
			firstTimeInit(w, h);
		}

		float waterX = 0;
		while (waterX < w) {
			c.drawBitmap(water, waterX, h/2, paint);
			waterX += water.getWidth();
		}

		battleship.draw(c);
		for (Plane p : planes) {
			p.draw(c);
		}
		for (Sub s : subs) {
			s.draw(c);
		}
		for (DepthCharge d : charges) {
			d.draw(c);
		}
		for (Missile m : missiles) {
			m.draw(c);
		}
		c.drawText("SCORE: " + score, 5, h*0.6f, paint);

		//printing time and when time is over show the dialogue
		c.drawText("TIME: " + minLeft + ":" + zero + secLeft, 1600, h*0.6f, paint);
		if (timeLeft == 0) {
			Intent i = new Intent(getContext(), HighScoreBoard.class);
			((MainActivity) getContext()).startActivityForResult(i, HighScoreBoard.ID);
		}
	}

	private void firstTimeInit(int w, int h) {
		firstTime = false;

		//set sizes
		screenWidth = w;
		screenHeight = h;
		for (int i=0; i<Prefs.getNumPlanes(getContext()); i++) {
			planes.add(new Plane(Prefs.getPlaneSpeed(getContext())));
		}
		for (int i=0; i<Prefs.getNumSubs(getContext()); i++) {
			subs.add(new Sub(Prefs.getSubSpeed(getContext())));			
		}
		water = Bitmap.createScaledBitmap(water, w/45, h/30, true);
		battleship.scaleThyself(w, h);
		paint.setTextSize(MainActivity.findThePerfectFontSize(h/20));

		//set positions
		battleship.setLocation((w-battleship.getWidth())/2f,
				h/2-battleship.getHeight()+water.getHeight());
		tim = new MyTimer();

		//register observers with timer
		for (Plane o : planes) {
			tim.addListener(o);
		}
		for (Sub o : subs) {
			tim.addListener(o);
		}
		tim.addListener(this);

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
				if (Prefs.getRapidDC(getContext()) || charges.isEmpty()) {
					DepthCharge dc = new DepthCharge();
					dc.scaleThyself(screenWidth, screenHeight);
					dc.setLocation(screenWidth/2, screenHeight * 0.52f);
					charges.add(dc);
					tim.addListener(dc);
				}
			} else {
				if (Prefs.getRapidGuns(getContext()) || missiles.isEmpty()) {
					Missile m;
					if (x < screenWidth/2) {
						m = new Missile(Direction.RIGHT_TO_LEFT);
						m.setLocation(screenWidth * 0.34f, screenHeight * 0.4f);
						if (Prefs.getSoundFX(getContext())) {
							leftSound.start();
						}
					} else {
						m = new Missile(Direction.LEFT_TO_RIGHT);
						m.setLocation(screenWidth * 0.64f, screenHeight * 0.4f);
						if (Prefs.getSoundFX(getContext())) {
							rightSound.start();
						}
					}
					m.scaleThyself(screenWidth, screenHeight);
					missiles.add(m);
					tim.addListener(m);
				}
			}
			cleanupUnusedProjectiles();
		}
		return true;
	}

	private void cleanupUnusedProjectiles() {
		for (Sprite s : doomed) {
			tim.removeListener(s);
		}
	}

	@Override
	public void tick() {
		//stop when time is over
		if(timeLeft > 0){
			detectCollisions();
			invalidate();
		}
	}


}
