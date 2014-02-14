package nth.sprite.vetsupport.basicsprites.camera;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.product.BadBird;
import nth.sprite.vetsupport.basicsprites.product.BadWingBird;
import nth.sprite.vetsupport.basicsprites.product.OneLegBird;
import nth.sprite.vetsupport.basicsprites.product.Product;
import nth.sprite.vetsupport.basicsprites.product.RedBird;
import nth.sprite.vetsupport.basicsprites.product.SmallBird;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;
import nth.sprite.vetsupport.panconveyorinspection.EvisLine;

public class EvisCamera1 extends ImageSprite {

	private static int WIDTH = 70;
	private final EvisLine evisLine;
	private int c = 0;

	public EvisCamera1(Canvas canvas, EvisLine evisLine, int x, int y) {
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
				Product product = linePosition.getProduct();
				Color lightColor = Color.OFF;
				if (product != null) {
					lightColor = Color.GREEN;
					if (product instanceof SmallBird) {
						lightColor = Color.RED;
					}
					if (product instanceof RedBird) {
						lightColor = Color.RED;
					}
					if (product instanceof BadBird) {
						lightColor = Color.RED;
					}
					if (product instanceof BadWingBird) {
						lightColor = Color.ORANGE;
					}
					if (product instanceof OneLegBird) {
						lightColor = Color.ORANGE;
					}

					if (c == 20) {
						if (lightColor == Color.GREEN) {
							lightColor = Color.RED;// false reject
							c++;
						}
					} else if (c == 40) {
						if (lightColor == Color.RED || lightColor == Color.ORANGE) {
							lightColor = Color.GREEN;// false positive
							c = 0;
						}
					} else {
						c++;
					}
				}

				if (lightColor != Color.OFF) {
					Light light = new Light(getCanvas(), lightColor);
					light.setX(x + 23);
					light.setY(Constants.Y_HIGH_SELECTION_BAR_LIGHT);
					linePosition.setLight(light);
				}
			}
		}
	}

}
