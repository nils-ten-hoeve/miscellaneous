package nth.sprite.vetsupport.basicsprites.unloader;

import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;
import nth.sprite.vetsupport.panconveyorinspection.EvisLine;

public class Unloader extends ImageSprite {

	private final EvisLine evisLine;
	private final Color unloadColor;

	public Unloader(Canvas canvas, int x, int y) {
		this(canvas, null, x, null);
		setY(y);
	}

	public Unloader(Canvas canvas, EvisLine evisLine, int xPos, Color unloadColor) {
		super(canvas, "unloader.png", 60, -1);
		this.evisLine = evisLine;
		this.unloadColor = unloadColor;
		setY(Constants.Y_PRODUCT - 5);
		setX(xPos);
		setLayer(3);
	}

	@Override
	public void process() {
		if (evisLine != null) {
			List<LinePosition> linePositions = evisLine.getSprites();
			for (int i = linePositions.size() - 1; i > 0; i--) {
				LinePosition linePosition = linePositions.get(i);
				if (linePosition.getLight().getColor() == unloadColor && linePosition.getShackle().getX() > getX()) {
					linePosition.dropProduct();
				}
			}
		}
	}

}
