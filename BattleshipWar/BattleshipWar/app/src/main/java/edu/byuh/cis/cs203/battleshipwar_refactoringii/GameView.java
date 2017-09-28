package edu.byuh.cis.cs203.battleshipwar_refactoringii;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
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
	private int score;
	private List<Sprite> doomed;//for "concurrent modification error" avoidance
	private int dcCount; //for timing the depth charge "beep" sound


	

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
		for (Sub s : subs) {
			for (DepthCharge d : charges) {
				if (d.overlaps(s)) {
					s.explode();
					//sound effect
					if (Preferences.getSoundEffects(getContext())) {
						waterExplosion.start();
					}
					score += s.getPointValue();
					doomed.add(d);
				}
			}
		}
		for (DepthCharge d : charges) {
			if (!d.isVisible()) {
				//frugality mode
				if (Preferences.getFrugality(getContext())){
					score --;
				}
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
					if (Preferences.getSoundEffects(getContext())) {
						airExplosion.start();
					}
					score += p.getPointValue();
					doomed.add(m);
				}

			}
		}
		for (Missile b : missiles) {
			if (!b.isVisible()) {
				if (Preferences.getFrugality(getContext())){
					score --;
				}
				doomed.add(b);
			}
		}
		for (Sprite s : doomed) {
			missiles.remove(s);
			//tim.removeListener(s);
		}
		doomed.clear();
		if (charges.size() > 0 && dcCount % 10 == 0) {
			dcSound.start();
		}
		dcCount++;

	}

	/**
	 *
 	 * @param c
     */
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
	}

	/**
	 *
	 * @param w
	 * @param h
     */
	private void firstTimeInit(int w, int h) {
		firstTime = false;

		
		//set sizes
		screenWidth = w;
		screenHeight = h;
//		planes.add(new Plane(Size.LARGE, Direction.RIGHT_TO_LEFT));
//		planes.add(new Plane(Size.MEDIUM, Direction.RIGHT_TO_LEFT));
//		planes.add(new Plane(Size.SMALL, Direction.RIGHT_TO_LEFT));
//		subs.add(new Sub(Size.LARGE, Direction.LEFT_TO_RIGHT));
//		subs.add(new Sub(Size.MEDIUM, Direction.LEFT_TO_RIGHT));
//		subs.add(new Sub(Size.SMALL, Direction.LEFT_TO_RIGHT));

		//number of plane
		for(int i=0; i < Preferences.getPlaneNumber(getContext()); i++) {
			double rand = Math.random();
			if (rand < 0.5) {
				planes.add(new Plane(Size.MEDIUM, Direction.RIGHT_TO_LEFT));
			} else if (rand > 0.5 && rand < 0.7) {
				planes.add(new Plane(Size.LARGE, Direction.RIGHT_TO_LEFT));
			} else {
				planes.add(new Plane(Size.SMALL, Direction.LEFT_TO_RIGHT));
			}
		}

		//number of sub
		for(int i=0; i <Preferences.getSubNumber(getContext()); i++) {
			double rand = Math.random();
			if (rand < 0.5) {
				subs.add(new Sub(Size.LARGE, Direction.RIGHT_TO_LEFT));
			} else if (rand > 0.5 && rand < 0.7) {
				subs.add(new Sub(Size.MEDIUM, Direction.RIGHT_TO_LEFT));
			} else {
				subs.add(new Sub(Size.SMALL, Direction.LEFT_TO_RIGHT));
			}
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

	/**
	 *
 	 * @param me
	 * @return
     */
	@Override
	public boolean onTouchEvent(MotionEvent me) {

		if (me.getAction() == MotionEvent.ACTION_DOWN) {
			float x = me.getX();
			float y = me.getY();
				if (y > screenHeight/2) {
					DepthCharge dc = new DepthCharge();
					dc.scaleThyself(screenWidth, screenHeight);
					dc.setLocation(screenWidth / 2, screenHeight * 0.52f);

					//rapid charge
					if (!Preferences.getRapidCharges(getContext())) {
						if (charges.size() < 1) {
							charges.add(dc);
						}
					} else {
						charges.add(dc);
					}
					tim.addListener(dc);

			} else {
				Missile m;
				if (x < screenWidth/2) {
					m = new Missile(Direction.RIGHT_TO_LEFT);
					m.setLocation(screenWidth * 0.34f, screenHeight * 0.4f);
					if (Preferences.getSoundEffects(getContext())) {
						leftSound.start();
					}
				} else {
					m = new Missile(Direction.LEFT_TO_RIGHT);
					m.setLocation(screenWidth * 0.64f, screenHeight * 0.4f);
					if (Preferences.getSoundEffects(getContext())) {
						rightSound.start();
					}
				}
				m.scaleThyself(screenWidth, screenHeight);

					//rapid missile
					if (!Preferences.getRapidFireMissile(getContext())) {
						if (missiles.size() < 1) {
							missiles.add(m);
						}
					} else {
						missiles.add(m);
					}
				tim.addListener(m);
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
		detectCollisions();
		invalidate();		
	}


}
