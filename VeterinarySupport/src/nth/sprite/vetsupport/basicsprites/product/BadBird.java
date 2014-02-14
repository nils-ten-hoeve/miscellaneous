package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class BadBird extends Product {

	private static int  WIDTH=100;
	
	public BadBird(Canvas canvas) {
		super(canvas, "bird_bad.png", WIDTH,-1);
		setX(WIDTH/2 *-1);
		setY(Constants.Y_PRODUCT);
	}


}
