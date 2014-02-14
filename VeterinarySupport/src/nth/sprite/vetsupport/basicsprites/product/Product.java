package nth.sprite.vetsupport.basicsprites.product;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;


public abstract class Product extends ImageSprite {

	
	public enum Ruling {OK, REWORK, CONDAMN};
	
	public Product(Canvas canvas, String imageFileName) {
		super(canvas, imageFileName);
	}

	public Product(Canvas canvas, String imageFileName, int width, int heigth) {
		super(canvas, imageFileName, width, heigth);
	}


}
