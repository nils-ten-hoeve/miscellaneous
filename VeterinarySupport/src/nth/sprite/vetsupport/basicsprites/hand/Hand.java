package nth.sprite.vetsupport.basicsprites.hand;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.product.Product.Ruling;
import nth.sprite.vetsupport.basicsprites.shackleline.LinePosition;
import nth.sprite.vetsupport.panconveyorinspection.EvisLine;

public class Hand extends ImageSprite {

	private static final String HAND_UP_PNG = "hand_up80.png";
	private static final String HAND_DOWN_PNG = "hand_down80.png";
	private static final String HAND_ROTATED_PNG = "hand_rotated80.png";
	private static final int HAND_WIDTH = 80;

	public enum Destination {
		HOME, ABOVE_BAR, UNDER_BAR, LEFT, RIGHT, ABOVE_PAN, ABOVE_AND_BELOW_BAR
	}

	private final int LAYER_ABOVE_BAR = 3;
	private final int LAYER_UNDER_BAR = 0;

	// private int step;
	private Destination destination;
	// private Path onBarPath;
	// private Path offBarPath;
	private final EvisLine evisLine;
	private final int startPosX;
	private Object handDown;
	private Image handUp;
	private Image handRotated;

	public Hand(Canvas canvas, EvisLine evisLine, int startPosX) {
		super(canvas, HAND_DOWN_PNG, HAND_WIDTH, -1);
		this.evisLine = evisLine;
		this.startPosX = startPosX;
		setX(startPosX);
		setY(Constants.Y_HAND);
	}

	@Override
	public void process() {
		LinePosition lastLinePos = findLastLinePositionNeedingCorrection();

		int handX = getX();
		int handY = getY();

		if (lastLinePos == null) {
			destination = Destination.HOME;
		} else {
			Ruling neededCorrection = lastLinePos.needCorrection();
			if (handY != Constants.Y_PAN && destination != Destination.ABOVE_BAR && destination != Destination.UNDER_BAR && destination != Destination.ABOVE_AND_BELOW_BAR) {
				destination = Destination.ABOVE_PAN;
			} else {
				int destinationX = lastLinePos.getLight().getX() - 30;
				if (handX < destinationX) {
					destination = Destination.RIGHT;
				} else if (handX > destinationX) {
					destination = Destination.LEFT;
				} else if (neededCorrection == Ruling.OK) {
					destination = Destination.ABOVE_AND_BELOW_BAR;
					setImage(HAND_ROTATED_PNG);
					setLayer(LAYER_ABOVE_BAR);
				} else if (neededCorrection == Ruling.CONDAMN) {
					destination = Destination.ABOVE_BAR;
					setImage(HAND_DOWN_PNG);
					setLayer(LAYER_ABOVE_BAR);
				} else {
					destination = Destination.UNDER_BAR;
					setImage(HAND_UP_PNG);
					setLayer(LAYER_UNDER_BAR);
				}
			}
		}

		switch (destination) {
		case HOME:
			if (handY < Constants.Y_HAND) {
				move(0, 2);// move down
				if (getY() >= Constants.Y_HAND) {
					setImage(HAND_DOWN_PNG);
				}
			}
			if (handY > Constants.Y_PAN) {
				if (handX > startPosX) {
					move(-2, 0);// move left
				} else if (handX < startPosX) {
					move(2, 0);// move right
				}
			}
			break;
		case ABOVE_PAN:
			if (handY < Constants.Y_PAN) {
				move(0, 2);// move down
			} else if (handY > Constants.Y_PAN) {
				move(0, -2);// move up
			} else {
				destination = null;
			}
			break;
		case LEFT:
			move(-2, 0);
			break;
		case RIGHT:
			move(2, 0);
			break;
		case ABOVE_AND_BELOW_BAR:
			if (getLayer() == LAYER_UNDER_BAR && handY < Constants.Y_PAN) {
				move(1, 2);// first move down to go above
			}
			setLayer(LAYER_ABOVE_BAR);
			if (handY > Constants.Y_LOW_SELECTION_BAR) {
				move(1, -2);// move up to light
			} else {
				// we are there
				// change light color
				lastLinePos.getLight().setColor(Color.GREEN);
				// move down for next one
				destination = Destination.ABOVE_PAN;
			}
			break;
		case ABOVE_BAR:
			if (getLayer() == LAYER_UNDER_BAR && handY < Constants.Y_PAN) {
				move(1, 2);// first move down to go above
			}
			setLayer(LAYER_ABOVE_BAR);
			if (handY > Constants.Y_LOW_SELECTION_BAR) {
				move(1, -2);// move up to light
			} else {
				// we are there
				// change light color
				lastLinePos.getLight().setColor(Color.RED);
				// move down for next one
				destination = Destination.ABOVE_PAN;
			}
			break;
		case UNDER_BAR:
			if (getLayer() == LAYER_ABOVE_BAR && handY < Constants.Y_PAN) {
				move(1, 2);// first move down to go above
			}
			setLayer(LAYER_UNDER_BAR);
			if (handY > Constants.Y_LOW_SELECTION_BAR) {
				move(1, -2);// move up to light
			} else {
				// we are there
				// change light color
				lastLinePos.getLight().setColor(Color.ORANGE);
				// move down for next one
				destination = Destination.ABOVE_PAN;
			}
			break;

		default:
			break;
		}
	}

	private void setImage(String imageFileName) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imageFileName));
		Image image = imageIcon.getImage();
		// image = image.getScaledInstance(HAND_WIDTH, -1, Image.SCALE_SMOOTH);
		setImage(image);
	}

	private LinePosition findLastLinePositionNeedingCorrection() {
		List<LinePosition> linePositions = evisLine.getSprites();
		for (int i = linePositions.size() - 1; i > 0; i--) {
			LinePosition linePosition = linePositions.get(i);
			if (linePosition.needCorrection() != null && linePosition.getShackle().getX() > startPosX) {
				return linePosition;
			}
		}
		return null;
	}

}
