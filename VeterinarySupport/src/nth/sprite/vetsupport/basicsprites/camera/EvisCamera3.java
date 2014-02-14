package nth.sprite.vetsupport.basicsprites.camera;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.pan.BadPan;
import nth.sprite.vetsupport.basicsprites.pan.Pan;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;
import nth.sprite.vetsupport.panconveyorinspection.EvisLine;

public class EvisCamera3 extends ImageSprite {

	private static int WIDTH = 70;
	private final EvisLine evisLine;
	private int c = 0;

	public EvisCamera3(Canvas canvas, EvisLine evisLine, int x, int y) {
		super(canvas, "camera.png", WIDTH, -1);
		this.evisLine = evisLine;
		setX(x);
		setY(y);
	}

	@Override
	public void process() {
		for (LinePosition linePosition : evisLine.getSprites()) {
			int x = linePosition.getShackle().getX();
			if (x < -20) {
				linePosition.setLight(null);
			}
			if (x == getX()) {
				Pan pan = linePosition.getPan();
				Color lightColor = Color.OFF;
				if (pan != null) {
					if (pan instanceof BadPan) {
						lightColor = Color.RED;
					}

					// false reject, false positive
					if (c == 10) {
						if (lightColor == Color.GREEN) {
							lightColor = Color.RED;// false reject
						}
					} else if (c == 20) {
						if (lightColor == Color.RED) {
							lightColor = Color.GREEN;// false positive
						}
						c = 0;
					} else {
						c++;
					}
				}

				if (lightColor != Color.OFF) {
					linePosition.getLight().setColor(lightColor);
				}
			}
		}
	}

}
