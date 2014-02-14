package nth.sprite.vetsupport.basicsprites.shackle;

import nth.sprite.Canvas;

public class EvisShackle extends Shackle{

	public EvisShackle(Canvas canvas) {
		super(canvas, "evis");
	}

	public EvisShackle(Canvas canvas, int x, int y) {
		super(canvas, "evis");
		setX(x);
		setY(y);
	}

}
