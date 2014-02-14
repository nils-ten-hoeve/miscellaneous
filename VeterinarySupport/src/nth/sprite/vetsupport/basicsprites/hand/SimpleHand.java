package nth.sprite.vetsupport.basicsprites.hand;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;

public class SimpleHand extends ImageSprite {

	public SimpleHand(Canvas canvas, int x, int y) {
		super(canvas, "hand.png", 40,-1);
		setX(x);
		setY(y);
		setLayer(5);
	}

	
}
