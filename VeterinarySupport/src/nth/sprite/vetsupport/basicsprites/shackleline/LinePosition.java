package nth.sprite.vetsupport.basicsprites.shackleline;

import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Sprite;
import nth.sprite.SpriteCollection;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light;
import nth.sprite.vetsupport.basicsprites.pan.Pan;
import nth.sprite.vetsupport.basicsprites.product.Product;
import nth.sprite.vetsupport.basicsprites.product.Product.Ruling;
import nth.sprite.vetsupport.basicsprites.shackle.Shackle;

public class LinePosition extends SpriteCollection {

	private Shackle shackle;
	private Product product;
	private Pan pan;
	private Light light;
	private boolean dropBird = false;
	private Ruling ruling;

	public LinePosition(Canvas canvas, Shackle shackle, Product product, Pan pan, Light light, Ruling ruling) {
		super(canvas);
		setShackle(shackle);
		setProduct(product);
		setPan(pan);
		setLight(light);
		setRuling(ruling);
	}

	private void setRuling(Ruling ruling) {
		this.ruling = ruling;
	}

	public Shackle getShackle() {
		return shackle;
	}

	public void setShackle(Shackle newShackle) {
		List<Sprite> sprites = getSprites();
		sprites.remove(shackle);
		if (newShackle != null) {
			sprites.add(newShackle);
		}
		shackle = newShackle;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product newProduct) {
		List<Sprite> sprites = getSprites();
		sprites.remove(product);
		if (newProduct != null) {
			sprites.add(newProduct);
		}
		product = newProduct;
	}

	public Pan getPan() {
		return pan;
	}

	public void setPan(Pan newPan) {
		List<Sprite> sprites = getSprites();
		sprites.remove(pan);
		if (newPan != null) {
			sprites.add(newPan);
		}
		pan = newPan;
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light newLight) {
		List<Sprite> sprites = getSprites();
		sprites.remove(light);
		if (newLight != null) {
			sprites.add(newLight);
		}
		light = newLight;
	}

	// public Ruling getProductRuling() {
	// if (product == null) {
	// return Ruling.OK;
	// }
	// return product.getRuling();
	// }
	//
	// public Ruling getGibletRuling() {
	// if (pan == null) {
	// return Ruling.OK;
	// }
	// return pan.getRuling();
	// }

	// public Ruling getOverAllRuling() {
	// Ruling productRuling = getProductRuling();
	// Ruling gibletRuling = getGibletRuling();
	// if (productRuling == Ruling.CONDAMN || gibletRuling == Ruling.CONDAMN) {
	// return Ruling.CONDAMN;
	// } else if (productRuling == Ruling.REWORK || gibletRuling == Ruling.REWORK) {
	// return Ruling.REWORK;
	// } else {
	// return Ruling.OK;
	// }
	// }

	public Ruling needCorrection() {
		Ruling ruling = getRuling();

		if (ruling == Ruling.OK && light != null && light.getColor() != Light.Color.GREEN) {
			return Ruling.OK;
		}
		if (ruling == Ruling.REWORK && light != null && light.getColor() != Light.Color.ORANGE) {
			return Ruling.REWORK;
		}
		if (ruling == Ruling.CONDAMN && light != null && light.getColor() != Light.Color.RED) {
			return Ruling.CONDAMN;

		}
		return null;// =no correction needed
	}

	private Ruling getRuling() {
		return ruling;
	}

	public void dropProduct() {
		dropBird = true;
	}

	@Override
	public void process() {
		if (dropBird && product != null) {
			if (product.getY() < Constants.Y_PRODUCT_DROPPED) {
				product.move(-1, +4);// dropping: don't move with line and drop
			} else {
				product.move(-1, 0);// dropped product: stay at position
			}
		}
	}

	/**
	 * set if the inspector can change the status of the line position, if so: move it to the front layer, if not remove the light
	 * 
	 * @param checked
	 */
	public void setChecked(boolean checked) {
		if (checked) {
			shackle.setLayer(2);
			if (product != null) {
				product.setLayer(2);
			}
		} else {
			setLight(null);
		}

	}

}
