package edu.byuh.cis.cs203.bw4;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Missile extends Sprite {

	private Direction dir;
	
	public Missile(Direction d) {
		super();
		dir = d;
		velocity.y = -GameView.getScreenHeight()/20;
		velocity.x = velocity.y;
		if (dir == Direction.LEFT_TO_RIGHT) {
			velocity.x = -velocity.x;
		}
	}

	@Override
	protected float relativeWidth() {
		return 0.03f;
	}

	@Override
	protected float relativeHeight() {
		return 0.05f;
	}
	
	@Override
	public void draw(Canvas c) {
		if (dir == Direction.RIGHT_TO_LEFT) {
			c.drawLine(bounds.right, bounds.bottom, bounds.left, bounds.top, paint);
		} else {
			c.drawLine(bounds.left, bounds.bottom, bounds.right, bounds.top, paint);
		}
	}

	@Override
	public void scaleThyself(int w, int h) {
		int newWidth = (int)(w * relativeWidth());
		int newHeight = (int)(h * relativeHeight());
		bounds.right = bounds.left + newWidth;
		bounds.bottom = bounds.top + newHeight;
	}
	
	public boolean isVisible() {
		return (bounds.bottom > 0);
	}


}
