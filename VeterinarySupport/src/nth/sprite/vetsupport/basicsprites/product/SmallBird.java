package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class SmallBird extends Product {

	private static int  WIDTH=70;
	
	public SmallBird(Canvas canvas) {
		super(canvas, "bird.png", WIDTH,-1);
		setX(WIDTH/2*-1);
		setY(Constants.Y_PRODUCT);
	}

}
