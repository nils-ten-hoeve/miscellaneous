package nth.sprite.vetsupport.basicsprites.shackleline;

import java.util.Random;

import nth.sprite.Canvas;
import nth.sprite.vetsupport.basicsprites.bar.Light;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.pan.BadPan;
import nth.sprite.vetsupport.basicsprites.pan.EmptyPan;
import nth.sprite.vetsupport.basicsprites.pan.FullPan1;
import nth.sprite.vetsupport.basicsprites.pan.FullPan2;
import nth.sprite.vetsupport.basicsprites.pan.Pan;
import nth.sprite.vetsupport.basicsprites.product.BadBird;
import nth.sprite.vetsupport.basicsprites.product.BadWingBird;
import nth.sprite.vetsupport.basicsprites.product.BirdWithoutPack;
import nth.sprite.vetsupport.basicsprites.product.NormalBird;
import nth.sprite.vetsupport.basicsprites.product.OneLegBird;
import nth.sprite.vetsupport.basicsprites.product.Product;
import nth.sprite.vetsupport.basicsprites.product.Product.Ruling;
import nth.sprite.vetsupport.basicsprites.product.RedBird;
import nth.sprite.vetsupport.basicsprites.product.SmallBird;
import nth.sprite.vetsupport.basicsprites.shackle.EvisShackle;

public class EvisLinePositionFactory {

	private enum PanType {
		FULL, EMPTY, BAD
	}
	private static Random random;

	static {
		random = new Random();
	}

	public static LinePosition createEmptyPosition(Canvas canvas, boolean hasLight) {
		return createBirdPosition(canvas, null, PanType.EMPTY, Ruling.OK,hasLight);
	}

	public static LinePosition createRandomPosition(Canvas canvas, boolean hasLight) {
		switch (random.nextInt(30)) {
		// Empty
		case 0:
			return createEmptyPosition(canvas, hasLight);
			// Rework
		case 1:
			return createBirdPosition(canvas, new OneLegBird(canvas), PanType.EMPTY, Ruling.REWORK, hasLight);
		case 2:
			return createBirdPosition(canvas, new BadWingBird(canvas), PanType.FULL, Ruling.REWORK, hasLight);
		case 3:
		case 4:	
			return createBirdPosition(canvas, new BirdWithoutPack(canvas), PanType.EMPTY, Ruling.REWORK, hasLight);
			// Condemn
		case 5:
			return createBirdPosition(canvas, new RedBird(canvas), PanType.FULL, Ruling.CONDAMN, hasLight);
		case 6:
			return createBirdPosition(canvas, new SmallBird(canvas), PanType.EMPTY, Ruling.CONDAMN, hasLight);
		case 7:
			return createBirdPosition(canvas, new BadBird(canvas), PanType.FULL, Ruling.CONDAMN, hasLight);
		case 8:
		case 9:
			return createBirdPosition(canvas, new NormalBird(canvas), PanType.BAD, Ruling.CONDAMN, hasLight);
			// OK
		default:
			return createBirdPosition(canvas, new NormalBird(canvas), PanType.FULL, Ruling.OK, hasLight);
		}
	}

	private static LinePosition createBirdPosition(Canvas canvas, Product product, PanType panType, Ruling ruling, boolean hasLight) {
		EvisShackle shackle = new EvisShackle(canvas);
		Pan pan = null;
		switch (panType) {
		case FULL:
			pan = createFullPan(canvas);
			break;
		case BAD:
			pan = new BadPan(canvas);
			break;
		case EMPTY:
			pan = new EmptyPan(canvas);
			break;
		}
		Light light = null;
		if (hasLight) {
			light = new Light(canvas, Color.GREEN);
		}
		return new LinePosition(canvas, shackle, product, pan, light, ruling);
	}

	private static Pan createFullPan(Canvas canvas) {
		int panNr = random.nextInt(2);
		switch (panNr) {
		case 0:
			return new FullPan1(canvas);
		default:
			return new FullPan2(canvas);
		}
	}

}
