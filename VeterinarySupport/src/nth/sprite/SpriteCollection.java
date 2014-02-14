package nth.sprite;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpriteCollection<E extends Sprite> implements Sprite {

	private List<E> sprites = new ArrayList<E>();
	private int layer = 0;
	private boolean visible = true;
	private final Canvas canvas;

	public SpriteCollection(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void process() {
		for (Sprite sprite : sprites) {
			sprite.process();
		}
	}

	@Override
	public void paint(Graphics graphics, ImageObserver imageObserver) {
		if (isVisible()) {
			Map<Integer, List<Sprite>> layers = getLayers(getAllSprites());

			List<Integer> layerNumbers = new ArrayList<Integer>();
			layerNumbers.addAll(layers.keySet());
			Collections.sort(layerNumbers);

			for (Integer layerNumber : layerNumbers) {
				List<Sprite> spritesInLayer = layers.get(layerNumber);

				for (Sprite sprite : spritesInLayer) {
					if (sprite.isVisible()) {
						sprite.paint(graphics, imageObserver);
					}
				}
			}
		}

	}

	private Map<Integer, List<Sprite>> getLayers(List<Sprite> sprites) {
		Map<Integer, List<Sprite>> layers = new HashMap<Integer, List<Sprite>>();
		for (Sprite sprite : sprites) {
			int layerNr = sprite.getLayer();
			List<Sprite> spritesInLayer = layers.get(layerNr);
			if (spritesInLayer == null) {
				spritesInLayer = new ArrayList<Sprite>();
				layers.put(layerNr, spritesInLayer);
			}
			spritesInLayer.add(sprite);
		}
		return layers;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public List<E> getSprites() {
		return sprites;
	}

	public List<Sprite> getAllSprites() {
		List<Sprite> allSprites = new ArrayList<Sprite>();
		for (Sprite sprite : sprites) {
			if (sprite instanceof SpriteCollection) {
				SpriteCollection spriteCollection = (SpriteCollection) sprite;
				allSprites.addAll(spriteCollection.getAllSprites());
			} else {
				allSprites.add(sprite);
			}
		}
		return allSprites;
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		for (Sprite sprite : sprites) {
			sprite.keyReleased(keyEvent);
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		for (Sprite sprite : sprites) {
			sprite.keyPressed(keyEvent);
		}

	}

	@Override
	public Canvas getCanvas() {
		return canvas;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public void move(int offsetX, int offsetY) {
		List<Sprite> sprites = getAllSprites();
		for (Sprite sprite : sprites) {
			if (sprite != null) {
				sprite.move(offsetX, offsetY);
			}
		}
	}

}
