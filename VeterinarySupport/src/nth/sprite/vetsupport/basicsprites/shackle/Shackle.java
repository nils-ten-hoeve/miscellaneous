package nth.sprite.vetsupport.basicsprites.shackle;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public abstract class Shackle extends ImageSprite{

	public static int WIDTH=60;
	public Shackle(Canvas canvas,String shackleName) {
		super(canvas, "shackle_"+shackleName+".png", WIDTH, -1);
		setX(WIDTH/2*-1);
		setY(Constants.Y_SHACKLE);
		setLayer(0);
	}

}
