package edu.byuh.cis.cs203.bw7;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Enemy extends Sprite {
	
	protected Size size;
	protected Direction dir;
	private boolean exploding;
	private float speedFactor;
	
	public Enemy(float speed) {
		super();
		speedFactor = speed;
		exploding = false;
	}
	
	@Override
	public void move() {
		super.move();
		if (Math.random() < 0.1) {
			velocity.x = getRandomVelocity() * Math.signum(velocity.x);
		}
		if (bounds.right < 0 || bounds.left > GameView.getScreenWidth()) {
			resetRandom();
		}

	}
	
	protected abstract int explodingImage();
	protected abstract int getPointValue();
	
	public void explode() {
		//scale all explosions the same, regardless of enemy size
		int newWidth = GameView.getScreenWidth() / 10;
		int newHeight = GameView.getScreenHeight() / 10;
		bounds.offset(-(newWidth-image.getWidth())/2, -(newHeight-image.getHeight())/2);
		image = GameView.myBitmapLoader(explodingImage());
		image = Bitmap.createScaledBitmap(image,
				newWidth, newHeight, true);
		velocity.set(0, 0);
		exploding = true;
	}
	
	@Override
	public void draw(Canvas c) {
		super.draw(c);
		if (exploding) {
			exploding = false;
			resetRandom();
		}
	}
	
	protected void resetRandom() {
		double r1 = Math.random();
		double r2 = Math.random();
		Direction newDirection;
		Size newSize;
		if (r1 < 0.5) {
			newDirection = Direction.RIGHT_TO_LEFT;
		} else {
			newDirection = Direction.LEFT_TO_RIGHT;
		}
		if (r2 < 0.3333) {
			newSize = Size.LARGE;
		} else if (r2 < 0.667) {
			newSize = Size.MEDIUM;
		} else {
			newSize = Size.SMALL;
		}
		reset(newSize, newDirection);
	}
	
	protected float getRandomVelocity() {
		return (float)(Math.random() * speedFactor * GameView.getScreenWidth());
	}
	
	protected abstract float getRandomHeight();
	
	protected void reset(Size s, Direction d) {
		if (d == Direction.RIGHT_TO_LEFT) {
			velocity.x = -1 * getRandomVelocity();
			bounds.offsetTo(GameView.getScreenWidth(), getRandomHeight());
		} else {
			velocity.x = getRandomVelocity();
			bounds.offsetTo(-image.getWidth(), getRandomHeight());
		}

	}

}
