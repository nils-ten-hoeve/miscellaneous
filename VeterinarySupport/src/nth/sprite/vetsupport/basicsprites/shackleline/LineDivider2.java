package nth.sprite.vetsupport.basicsprites.shackleline;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public class LineDivider2 extends ImageSprite {

	public LineDivider2(Canvas canvas) {
		super(canvas, "linedivider2.png",600,160);
		setLayer(1);
		setX(300);
		setY(Constants.Y_LINE_DIVIDER);
	}

	
	
}
