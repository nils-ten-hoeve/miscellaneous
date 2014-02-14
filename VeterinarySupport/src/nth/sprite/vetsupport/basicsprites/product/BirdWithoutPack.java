package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.Constants;

public class BirdWithoutPack extends Product {

	private static int  WIDTH=100;
	
	public BirdWithoutPack(Canvas canvas) {
		super(canvas, "bird.png", WIDTH,-1);
		setX(WIDTH/2*-1);
		setY(Constants.Y_PRODUCT);
	}

}
