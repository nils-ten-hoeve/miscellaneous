package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class OneLegBird extends Product {

	private static int  WIDTH=100;
	
	public OneLegBird(Canvas canvas) {
		super(canvas, "bird_one_leg.png", WIDTH,-1);
		setX((WIDTH/2+28) *-1);
		setY(Constants.Y_PRODUCT-5);
	}

}
