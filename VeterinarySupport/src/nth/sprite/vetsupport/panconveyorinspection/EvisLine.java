package nth.sprite.vetsupport.panconveyorinspection;

import java.util.ArrayList;
import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.SpriteCollection;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light;
import nth.sprite.vetsupport.basicsprites.shackle.EvisShackle;
import nth.sprite.vetsupport.basicsprites.shackleline.EvisLinePositionFactory;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;

public class EvisLine extends SpriteCollection<LinePosition> {

	private int linePositionCounter = 0;
	private final boolean allLinePositionsChecked;
	private final int x1PanConveyor;
	private final int x2PanConveyor;
	private final boolean hasLight;

	public EvisLine(Canvas canvas) {
		this(canvas, true, -80, canvas.getParent().getParent().getWidth() + 20,true);
	}

	public EvisLine(Canvas canvas, boolean allLinePositionsChecked) {
		this(canvas, allLinePositionsChecked, -80, canvas.getParent().getParent().getWidth() + 20,true);
	}

	public EvisLine(Canvas canvas, boolean allLinePositionsChecked, int x1PanConvayor, int x2PanConvayor, boolean hasLight) {
		super(canvas);
		this.allLinePositionsChecked = allLinePositionsChecked;
		this.x1PanConveyor = x1PanConvayor;
		this.x2PanConveyor = x2PanConvayor;
		this.hasLight = hasLight;
		List<LinePosition> sprites = getSprites();

		int offsetX = 0;
		for (int i = 0; i < 19; i++) {
			LinePosition linePosition = EvisLinePositionFactory.createEmptyPosition(canvas, hasLight);
			linePosition.move(offsetX, 0);
			linePosition.setChecked(isLinePositionChecked());
			sprites.add(linePosition);
			offsetX += EvisShackle.WIDTH;
		}

		LinePosition linePosition = EvisLinePositionFactory.createRandomPosition(canvas, hasLight);
		linePosition.move(offsetX, 0);
		linePosition.setChecked(isLinePositionChecked());
		sprites.add(linePosition);
		offsetX -= EvisShackle.WIDTH;
	}

	private boolean isLinePositionChecked() {
		if (allLinePositionsChecked) {
			return true;
		}
		linePositionCounter++;
		if (linePositionCounter > 2) {
			linePositionCounter = 0;
			return true;
		}
		return false;
	}

	@Override
	public void process() {
		super.process();
		List<LinePosition> linePositions = getSprites();
		List<LinePosition> linePositionsToRemove = new ArrayList<LinePosition>();
		LinePosition leftMostLinePosition = linePositions.get(0);
		Canvas canvas = leftMostLinePosition.getShackle().getCanvas();
		// move all sprites
		for (LinePosition linePosition : linePositions) {
			linePosition.move(1, 0);// move line position to the right

			int xPos = linePosition.getShackle().getX();
			if (xPos > x1PanConveyor && xPos < x2PanConveyor) {
				linePosition.getPan().setVisible(true);
				Light light = linePosition.getLight();
				if (light != null) {
					light.setY(Constants.Y_LOW_SELECTION_BAR_LIGHT);
				}
			} else {
				linePosition.getPan().setVisible(false);
				Light light = linePosition.getLight();
				if (light != null) {
					light.setY(Constants.Y_HIGH_SELECTION_BAR_LIGHT);
				}
			}

			int canvasWidth = canvas.getParent().getWidth();
			if (xPos > canvasWidth) {
				// remember all line position right of canvas (so we can remove them later)
				linePositionsToRemove.add(linePosition);
			}

		}
		// remove all line position right of canvas
		linePositions.removeAll(linePositionsToRemove);

		// create new line positions
		int leftMostShacklePosition = leftMostLinePosition.getShackle().getX();
		if (leftMostShacklePosition > -30) {
			LinePosition newLinePosition = EvisLinePositionFactory.createRandomPosition(canvas, hasLight);
			int newX = leftMostShacklePosition - EvisShackle.WIDTH;
			int currentX = newLinePosition.getShackle().getX();
			int offsetX = newX - currentX;
			newLinePosition.move(offsetX, 0);
			newLinePosition.setChecked(isLinePositionChecked());
			linePositions.add(0, newLinePosition);
		}

	}

}
