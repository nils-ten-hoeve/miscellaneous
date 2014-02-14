package nth.sprite.vetsupport.basicsprites.callout;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.ImageObserver;
import java.util.List;
import java.util.StringTokenizer;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;

//http://harryjoy.com/2012/07/28/chat-bubble-in-java-swing/
public class CallOut implements Sprite {

	private static final Color BACK_GROUND_COLOR = new Color(0, 120, 91);
	private static final int TextLineOffset = 25;
	private static final Font FONT = new Font("Arial", Font.PLAIN, 20);
	private int radius = 10;
	private int strokeThickness = 3;
	private int padding = strokeThickness / 2;
	private int x;
	private int y;
	private int width;
	private int height;
	private final String text;
	private boolean visible = true;
	private final Scene scene;
	private final ArrowLocation arrowLocation;
	private final int arrowX;
	private final int arrowY;
	private final Color backGroundColor;

	public enum ArrowLocation {
		LEFT, TOP, RIGHT, BOTTOM, NO_ARROW
	}

	public CallOut(Scene scene, int x, int y, String text) {
		this(scene, x, y, ArrowLocation.NO_ARROW, 0, 0, text, BACK_GROUND_COLOR);
	}

	public CallOut(Scene scene, int x, int y, String text, Color backGroundColor) {
		this(scene, x, y, ArrowLocation.NO_ARROW, 0, 0, text, backGroundColor);
	}

	public CallOut(Scene scene, int x, int y, ArrowLocation arrowLocation, int arrowX, int arrowY, String text) {
		this(scene, x, y, arrowLocation, arrowX, arrowY, text, BACK_GROUND_COLOR);
	}

	public CallOut(Scene scene, int x, int y, ArrowLocation arrowLocation, int arrowX, int arrowY, String text, Color backGroundColor) {
		this.scene = scene;
		this.x = x;
		this.y = y;
		this.arrowLocation = arrowLocation;
		this.arrowX = arrowX;
		this.arrowY = arrowY;
		this.backGroundColor = backGroundColor;
		this.text = text + "\n \nPress a key to continue...";

		// only first call out should be visible
		this.visible = true;
		List<Sprite> sprites = scene.getAllSprites();
		for (Sprite sprite : sprites) {
			if (sprite instanceof CallOut) {
				this.visible = false;
			}
		}

	}

	@Override
	public void process() {
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (visible) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
				keyEvent.setKeyCode(0);// consume
				showPreviousCallOut();
			} else if (keyEvent.getKeyCode() > 0) {
				keyEvent.setKeyCode(0);// consume
				showNextCallOut();
			}
		}

	}

	private void showNextCallOut() {
		List<Sprite> sprites = scene.getAllSprites();

		int index = sprites.indexOf(this);

		// find next call out
		CallOut nextCallOut = null;
		for (Sprite sprite : sprites) {
			if (sprite instanceof CallOut && sprites.indexOf(sprite) > index) {
				nextCallOut = (CallOut) sprite;
				break;
			}
		}

		if (nextCallOut != null) {
			setVisible(false);
			nextCallOut.setVisible(true);
		} else {
			showFirstCallOut();
			scene.getStory().goToNextScene();
		}
	}

	private void showFirstCallOut() {
		List<Sprite> sprites = scene.getAllSprites();
		boolean isFirst = true;
		for (Sprite sprite : sprites) {
			if (sprite instanceof CallOut) {
				((CallOut) sprite).setVisible(isFirst);
				isFirst = false;
			}
		}

	}

	private void showPreviousCallOut() {
		List<Sprite> sprites = scene.getAllSprites();

		int index = sprites.indexOf(this);

		// find previous call out
		CallOut previousCallOut = null;
		for (Sprite sprite : sprites) {
			if (sprite instanceof CallOut && sprites.indexOf(sprite) < index) {
				previousCallOut = (CallOut) sprite;
			}
		}

		if (previousCallOut != null) {
			setVisible(false);
			previousCallOut.setVisible(true);
		} else {
			showFirstCallOut();
			scene.getStory().goToPreviousScene();
		}
	}

	@Override
	public void paint(Graphics graphics, ImageObserver imageObserver) {
		final Graphics2D g2d = (Graphics2D) graphics;
		g2d.setFont(FONT);
		setHeightAndWidth(g2d);

		// balloon
		g2d.setColor(backGroundColor);
		int x = this.x + padding + strokeThickness;
		int y = this.y + padding + strokeThickness;
		int bottomLineY = height - strokeThickness;
		g2d.fillRect(x, y, width, bottomLineY);
		g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
		g2d.setStroke(new BasicStroke(strokeThickness));
		RoundRectangle2D.Double rect = new RoundRectangle2D.Double(x, y, width, bottomLineY, radius, radius);

		Polygon arrow = new Polygon();
		switch (arrowLocation) {
		case LEFT:
			arrow.addPoint(x, y + (height / 4));
			arrow.addPoint(x, y + (height / 4) + 20);
			arrow.addPoint(arrowX, arrowY);
			break;
		case RIGHT:
			arrow.addPoint(x + width, y + (height / 4));
			arrow.addPoint(x + width, y + (height / 4) + 20);
			arrow.addPoint(arrowX, arrowY);
			break;
		case TOP:
			arrow.addPoint(x + (width / 4), y);
			arrow.addPoint(x + (width / 4) + 20, y);
			arrow.addPoint(arrowX, arrowY);
			break;
		case BOTTOM:
			arrow.addPoint(x + (width / 4), y + height);
			arrow.addPoint(x + (width / 4) + 20, y + height);
			arrow.addPoint(arrowX, arrowY);
			break;
		default:
			break;
		}

		Area area = new Area(rect);
		area.add(new Area(arrow));
		g2d.draw(area);
		// g2d.fillPolygon(arrow);
		// text
		y += 20;
		x += 20;
		g2d.setColor(Color.WHITE);
		StringTokenizer tokenizer = new StringTokenizer(text, "\n");
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken();
			y += TextLineOffset;
			g2d.drawString(line, x, y);
		}
	}

	private void setHeightAndWidth(Graphics2D g2d) {
		height = 0;
		width = 0;
		FontMetrics fontMetrics = g2d.getFontMetrics();
		StringTokenizer tokenizer = new StringTokenizer(text, "\n");
		while (tokenizer.hasMoreTokens()) {
			height += TextLineOffset;
			String line = tokenizer.nextToken();
			int lineWidth = fontMetrics.stringWidth(line);
			if (lineWidth > width) {
				width = lineWidth;
			}
		}
		height += 50;
		width += 60;
	}

	@Override
	public int getLayer() {
		return 100; // always on top
	}

	@Override
	public void move(int offsetX, int offsetY) {
		x += offsetX;
		y += offsetY;
	}

	@Override
	public Canvas getCanvas() {
		return scene.getCanvas();
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
