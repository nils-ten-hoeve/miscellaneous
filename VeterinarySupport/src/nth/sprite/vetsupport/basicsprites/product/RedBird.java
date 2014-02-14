package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class RedBird extends Product {

	private static int  WIDTH=100;
	
	public RedBird(Canvas canvas) {
		super(canvas, "bird_red.png", WIDTH,-1);
		setX(WIDTH/2 *-1);
		setY(Constants.Y_PRODUCT);
	}


}
