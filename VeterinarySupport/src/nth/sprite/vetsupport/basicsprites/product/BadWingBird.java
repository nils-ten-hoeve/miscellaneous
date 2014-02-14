package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class BadWingBird extends Product {

	private static int  WIDTH=100;
	
	public BadWingBird(Canvas canvas) {
		super(canvas, "bird_bad_wing.png", WIDTH,-1);
		setX(WIDTH/2*-1);
		setY(Constants.Y_PRODUCT);
	}

}
