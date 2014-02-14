package nth.sprite;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public class Text implements Sprite {

	private boolean visible=true;
	private final String text;
	private int x;
	private int y;
	private int layer;
	private final Canvas canvas;
	
	public Text(Canvas canvas, String text, int x, int y) {
		this.canvas = canvas;
		this.text = text;
		setX(x);
		setY(y);
	}

	private void setY(int y) {
		this.y = y;
	}

	private void setX(int x) {
		this.x = x;
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics graphics, ImageObserver imageObserver) {
		graphics.drawString(text, x, y);

	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public void move(int offsetX, int offsetY) {
		x+=offsetX;
		y+=offsetY;

	}

	@Override
	public Canvas getCanvas() {
		return canvas;
	}

}
