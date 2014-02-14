package nth.sprite.vetsupport.basicsprites.camera;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.product.BirdWithoutPack;
import nth.sprite.vetsupport.basicsprites.product.Product;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;
import nth.sprite.vetsupport.panconveyorinspection.EvisLine;

public class EvisCamera2 extends ImageSprite {

	private static int WIDTH = 70;
	private final EvisLine evisLine;
	private int c = 0;

	public EvisCamera2(Canvas canvas, EvisLine evisLine, int x, int y) {
		super(canvas, "camera_upside_down.png", WIDTH, -1);
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
				Product product = linePosition.getProduct();
				Color lightColor = Color.OFF;
				if (product != null) {
					if (product instanceof BirdWithoutPack) {
						lightColor = Color.ORANGE;
					}

					// false reject, false positive
					if (c == 10) {
						if (lightColor == Color.GREEN) {
							lightColor = Color.ORANGE;// false reject
						}
					} else if (c == 20) {
						if (lightColor == Color.ORANGE) {
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
