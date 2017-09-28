package edu.byuh.cis.cs203.bw4;

import android.graphics.Canvas;

public abstract class Enemy extends Sprite {
	
	protected Size size;
	protected Direction dir;
	private boolean exploding;

	public Enemy() {
		super();
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
	
	private void resetRandom() {
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
		return (float)(Math.random() * 0.00625 * GameView.getScreenWidth());
	}
	
	@Override
	public void draw(Canvas c) {

		super.draw(c);
		if (exploding){
			exploding = false;
			resetRandom();
		}
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

	protected abstract int explodingImage();
/*
this one here is for the explosion
after defeating the enemy
 */
	protected void explode() {
		exploding = true;
		image = GameView.myBitmapLoader(explodingImage());
		bounds.offset(0, 0);
	}

	protected abstract int getPointValue();


}
