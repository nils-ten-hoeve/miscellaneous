package nth.sprite;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

public interface Sprite {

	public void process();

	public void keyReleased(KeyEvent keyEvent);

	public void keyPressed(KeyEvent keyEvent);

	public void paint(Graphics graphics, ImageObserver imageObserver);

	public boolean isVisible();
	
	/**
	 * 
	 * @return layer level. 0= on top, 0=default
	 */
	public int getLayer(); 
	
	public void move(int offsetX, int offsetY);
	
	public Canvas getCanvas();
}
