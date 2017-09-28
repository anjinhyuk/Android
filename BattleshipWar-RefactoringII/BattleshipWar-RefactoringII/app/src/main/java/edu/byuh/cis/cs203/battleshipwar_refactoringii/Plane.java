package edu.byuh.cis.cs203.battleshipwar_refactoringii;


import android.content.Context;

public class Plane extends Enemy {

	public Plane(Size size, Direction dir) {
		super();
		reset(size, dir);
	}

	public void reset(Size s, Direction d) {
		size = s;
		dir = d;
		switch (size) {
			case SMALL:
				if (dir == Direction.RIGHT_TO_LEFT) {
					image = GameView.myBitmapLoader(R.drawable.little_airplane);
				} else {
					image = GameView.myBitmapLoader(R.drawable.little_airplane_flip);
				}
				break;
			case MEDIUM:
				if (dir == Direction.RIGHT_TO_LEFT) {
					image = GameView.myBitmapLoader(R.drawable.medium_airplane);
				} else {
					image = GameView.myBitmapLoader(R.drawable.medium_airplane_flip);
				}
				break;
			case LARGE:
			default:
				if (dir == Direction.RIGHT_TO_LEFT) {
					image = GameView.myBitmapLoader(R.drawable.big_airplane);
				} else {
					image = GameView.myBitmapLoader(R.drawable.big_airplane_flip);
				}
				break;
		}
		scaleThyself(GameView.getScreenWidth(), GameView.getScreenHeight());
		super.reset(size, dir);
	}


	@Override
	protected float relativeWidth() {
		switch (size) {
		case SMALL:
			return 0.05f;
		case MEDIUM:
			return 0.083f;
		case LARGE:
			default:
			return 0.1f;
		} 
	}

	@Override
	protected float relativeHeight() {
		switch (size) {
		case SMALL:
			return 0.05f;
		case MEDIUM:
			return 0.067f;
		case LARGE:
			default:
			return 0.067f;
		} 
	}
	
	@Override
	protected float getRandomHeight() {
		return (float)(Math.random() * 0.25 * GameView.getScreenHeight());
	}

	@Override
	protected int explodingImage() {
		return R.drawable.airplane_explosion;
	}

	@Override
	protected int getPointValue() {
		switch (size) {
		case SMALL:
			return 75;
		case MEDIUM:
			return 20;
		case LARGE:
			default:
			return 15;
		} 
	}
//	public void move(Context c) {
//		float sp;
//		String Pspeed = Prefs.getPlaneSpeed(c);
//		if (Pspeed.equals("Slow")) {
//			sp = 0.6f;
//		} else if (Pspeed.equals("Medium")) {
//			sp = 0;
//		} else {
//			sp = 1.4f;
//		}
//		bounds.offset(velocity.x * sp, velocity.y * sp);
//	}
//	@Override
//	public void tick() {
//		move();
//	}
}
