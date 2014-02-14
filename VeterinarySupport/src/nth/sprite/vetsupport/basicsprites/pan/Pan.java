package nth.sprite.vetsupport.basicsprites.pan;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public abstract class Pan extends ImageSprite {

	private static int  WIDTH=55;
	
	public Pan(Canvas canvas, String panName) {
		super(canvas, "pan_"+panName+".png", WIDTH,-1);
		setX(WIDTH/2*-1);
		setY(Constants.Y_PAN);
	}

}
