package nth.sprite.vetsupport.basicsprites.line;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;

public class Line extends ImageSprite {

	public Line(Canvas canvas, int x, int y, int width, int height) {
		super(canvas, "line.png", width, height);
		setX(x);
		setY(y);
	}

}
