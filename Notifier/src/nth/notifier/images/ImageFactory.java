package nth.notifier.images;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Map;

import nth.notifier.time.TimeStatus;

public class ImageFactory {
	private static final int YPOS = 11;
	private static final int WIDTH = 16;
	private static final Color BACK_COLOR = new Color(12, 144, 235);
	private static Map<String, Image> images;
	// private static Font FONT = new Font("Times New Roman", Font.PLAIN, 9);
	private static Font FONT = new Font("Universe", Font.PLAIN, 9);

	static {
		// get all image names
		// List<String> imageNames = new ArrayList<String>();
		// for (TimeStatus timeStatus : TimeStatus.values()) {
		// for (EmailStatus emailStatus : EmailStatus.values()) {
		// imageNames.add(getImageName(timeStatus, emailStatus));
		// }
		// }
		// // load images in a hashmap
		// images = new HashMap<String, Image>();
		// for (String imageName : imageNames) {
		// URL url = ImageFactory.class.getResource(imageName);
		// try {
		// Image image = new ImageIcon(url).getImage();
		// images.put(imageName, image);
		// } catch (Exception e) {
		// System.out.println("Could not open: "+imageName );
		// }
		// }
	}

	public static Image getImage(TimeStatus timeStatus, String timeToGo) {

		BufferedImage buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = buffer.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		FontRenderContext fc = graphics.getFontRenderContext();

		// prepare some output
		buffer = new BufferedImage(WIDTH, 16, BufferedImage.TYPE_INT_RGB);
		graphics = buffer.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.setFont(FONT);
		// actually do the drawing

		graphics.setColor(BACK_COLOR);
		graphics.fillRect(0, 0, WIDTH, 16);

		switch (timeStatus) {
		case INVALID_START_TIME:
			graphics.setColor(Color.ORANGE);
			timeToGo = "??";
			break;
		case WORKING:
			graphics.setColor(Color.WHITE);
			break;
		case ALMOST_TIME:
			graphics.setColor(Color.ORANGE);
			break;
		case PAST_TIME:
		case WAY_PAST_TIME:
			graphics.setColor(Color.ORANGE);
			timeToGo = "!!";
			break;
		}
		Rectangle2D bounds = FONT.getStringBounds(timeToGo, fc);
		int x = (WIDTH - (int) Math.ceil(bounds.getWidth())) / 2;
		if (timeToGo.contains(":")) {
			graphics.drawString(timeToGo.substring(0, timeToGo.indexOf(":")), x+1, YPOS);
			graphics.drawString(timeToGo.substring(timeToGo.indexOf(":")), x + 4, YPOS);
		} else {
			graphics.drawString(timeToGo, x, YPOS);
		}

		
		return buffer;
	}

	public static Image getImage(TimeStatus timeStatus) {
		String imageName = getImageName(timeStatus);
		if (images.containsKey(imageName)) {
			return images.get(imageName);
		} else {
			return images.get("Default.gif");
		}
	}

	private static String getImageName(TimeStatus timeStatus) {
		StringBuffer imageName = new StringBuffer();
		switch (timeStatus) {
		case INVALID_START_TIME:
			imageName.append("NoTime");
			break;
		case ALMOST_TIME:
			imageName.append("AlmostTime");
			break;
		case PAST_TIME:
			imageName.append("PastTime");
			break;
		}

		if (imageName.length() == 0) {
			imageName.append("Default");
		}
		imageName.append(".gif");
		return imageName.toString();
	}

}
