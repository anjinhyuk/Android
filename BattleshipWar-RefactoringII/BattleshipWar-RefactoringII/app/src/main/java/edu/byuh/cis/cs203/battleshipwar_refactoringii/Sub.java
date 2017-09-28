package edu.byuh.cis.cs203.battleshipwar_refactoringii;


public class Sub extends Enemy {

	public Sub(Size size, Direction dir) {
		super();
		reset(size, dir);
	}
	
	@Override
	public void reset(Size s, Direction d) {
		size = s;
		dir = d;
		switch (size) {
		case SMALL:
			if (dir == Direction.LEFT_TO_RIGHT) {
				image = GameView.myBitmapLoader(R.drawable.little_submarine);
			} else {
				image = GameView.myBitmapLoader(R.drawable.little_submarine_flip);
			}
			break;
		case MEDIUM:
			if (dir == Direction.LEFT_TO_RIGHT) {
				image = GameView.myBitmapLoader(R.drawable.medium_submarine);
			} else {
				image = GameView.myBitmapLoader(R.drawable.medium_submarine_flip);
			}
			break;
		case LARGE:
		default:
			if (dir == Direction.LEFT_TO_RIGHT) {
				image = GameView.myBitmapLoader(R.drawable.big_submarine);
			} else {
				image = GameView.myBitmapLoader(R.drawable.big_submarine_flip);
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
		return (float)((0.55 + Math.random() * 0.4) * GameView.getScreenHeight());
	}

	@Override
	protected int explodingImage() {
		return R.drawable.submarine_explosion;
	}

	@Override
	protected int getPointValue() {
		switch (size) {
		case SMALL:
			return 150;
		case MEDIUM:
			return 40;
		case LARGE:
			default:
			return 20;
		} 
	}

	public void move() {

		bounds.offset(velocity.x, velocity.y);
	}
	@Override
	public void tick() {
		move();
	}
}
