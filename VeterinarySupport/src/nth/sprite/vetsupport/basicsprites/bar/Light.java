package nth.sprite.vetsupport.basicsprites.bar;

import javax.swing.ImageIcon;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public class Light extends ImageSprite {

	private Color color;

	public enum Color {OFF, GREEN,ORANGE, RED};
	
	public Light(Canvas canvas, Color color) {
		super(canvas, "light_green.png");
		setX(getImage().getWidth(canvas)/2*-1);
		setY(Constants.Y_LOW_SELECTION_BAR_LIGHT);
		setColor(color);
		setLayer(2);//above bar
	}

	public void setColor(Color color) {
		this.color = color;
		String imageFileName = "light_"+color.toString().toLowerCase()+".png";
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(imageFileName));
		setImage(imageIcon.getImage());
	}

	public Color getColor() {
		return color;
	}

}
