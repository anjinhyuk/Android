package edu.byuh.cis.cs203.battleshipwar_refactoringii;

public class DepthCharge extends Sprite {

	public DepthCharge() {
		super();
		image = GameView.myBitmapLoader(R.drawable.depth_charge);
		velocity.y = GameView.getScreenHeight()/100;
	}

	@Override
	protected float relativeWidth() {
		return 0.025f;
	}

	@Override
	protected float relativeHeight() {
		return 0.02f;
	}
	
	public boolean isVisible() {
		return bounds.top < GameView.getScreenHeight();
	}

}
