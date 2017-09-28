package edu.byuh.cis.cs203.battleshipendgame2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;

public abstract class Sprite implements TickListener {
	
	protected Bitmap image;
	protected RectF bounds;
	protected Paint paint;
	protected PointF velocity;
	
	public Sprite() {
		bounds = new RectF();
		velocity = new PointF();
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Style.STROKE);
	}
	
	public void setLocation(float x, float y) {
		bounds.offsetTo(x, y);
	}
	
	public void draw(Canvas c) {
		c.drawBitmap(image, bounds.left,  bounds.top, paint);
	}

	public void scaleThyself(int w, int h) {
		int newWidth = (int)(w * relativeWidth());
		int newHeight = (int)(h * relativeHeight());
		image = Bitmap.createScaledBitmap(image,
				newWidth, newHeight, true);
		bounds.right = bounds.left + newWidth;
		bounds.bottom = bounds.top + newHeight;
	}
	
	public float getWidth() {
		return bounds.width();
	}
	
	public float getHeight() {
		return bounds.height();
	}

	public void move() {
		bounds.offset(velocity.x, velocity.y);
	}
	
	public boolean overlaps(Sprite s) {
		return RectF.intersects(s.bounds, this.bounds);
	}
	
	@Override
	public void tick() {
		move();
	}
	
	protected abstract float relativeWidth();
	protected abstract float relativeHeight();
}
