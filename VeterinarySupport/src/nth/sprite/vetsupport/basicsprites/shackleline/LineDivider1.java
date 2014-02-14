package nth.sprite.vetsupport.basicsprites.shackleline;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public class LineDivider1 extends ImageSprite {

	public LineDivider1(Canvas canvas) {
		super(canvas, "linedivider1.png",200,-1);
		setLayer(1);
		setX(100);
		setY(Constants.Y_LINE_DIVIDER-36);
	}

	
	
}
