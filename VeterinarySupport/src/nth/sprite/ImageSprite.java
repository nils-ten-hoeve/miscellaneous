package nth.sprite;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

public class ImageSprite implements Sprite {

	private int x=0;
	private int y=0;
	private int layer=0;
	private Image image;
	private boolean visible=true;
	private final Canvas canvas;

	public ImageSprite(Canvas canvas, String imageFileName) {
		this.canvas = canvas;
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imageFileName));
		image = imageIcon.getImage();
	}
	
	
	public ImageSprite(Canvas canvas, String imageFileName, int width, int height) {
		this(canvas, imageFileName);
		image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	}

	@Override
	public void process() {
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
	}

	@Override
	public void paint(Graphics graphics, ImageObserver imageObserver) {
		graphics.drawImage(image, x, y, imageObserver);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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
		setX(getX()+offsetX);
		setY(getY()+offsetY);	
	}

}
