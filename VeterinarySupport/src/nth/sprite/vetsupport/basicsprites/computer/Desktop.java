package nth.sprite.vetsupport.basicsprites.computer;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;

public class Desktop extends ImageSprite {

	public Desktop(Canvas canvas, int x, int y) {
		super(canvas, "desktop.png", 120,-1);
		setX(x);
		setY(y);
	}

	
}
