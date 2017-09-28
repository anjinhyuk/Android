package edu.byuh.cis.cs203.bw4;


import android.content.res.Resources;
import android.graphics.BitmapFactory;

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
		return (float) (Math.random() * 0.25 * GameView.getScreenHeight());
	}
/*
this returns the int ID of the image
 */
	@Override
	protected int explodingImage() {
		return R.drawable.airplane_explosion;
	}
/*
this sction of code below gives the score
after destroying the enemy
 */
	@Override
	protected int getPointValue(){
		switch (size) {
			case SMALL:
				return 75;
			case MEDIUM:
				return 20;
			case LARGE:
				return 15;
		}
		return 0;
	}

}
