package nth.sprite.vetsupport.basicsprites.bar;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public class SelectionBar extends ImageSprite {

	public SelectionBar(Canvas canvas) {
		this(canvas, 0, canvas.getParent().getWidth(), Constants.Y_LOW_SELECTION_BAR); // layer 1 to allow hand to be above or below
	}

	public SelectionBar(Canvas canvas, int x, int width, int y) {
		super(canvas, "bar_selection.png", width, 22);
		setX(x);
		setY(y);
		setLayer(1);
	}

}
