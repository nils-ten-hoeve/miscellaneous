package nth.sprite.vetsupport.basicsprites.camera;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;

public class Sensor extends ImageSprite{

	public Sensor(Canvas canvas, int x, int y) {
		super(canvas, "sensor.png", 40,-1);
		setX(x);
		setY(y);
		setLayer(0);
	}


}
