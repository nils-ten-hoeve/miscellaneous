package nth.android.mysettings.ui.viewer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.drawable.Drawable;

public class IconText extends Drawable {

	private Paint paint;
	private final String text;
	private final int textSize;

	public IconText(String text, int textSize) {
		this.text = text;
		this.textSize = textSize;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setTextAlign(Align.CENTER);
		paint.setShadowLayer(3, 1, 1, Color.GRAY);
		paint.setAntiAlias(true);
		paint.setTextSize(textSize);
		paint.setFakeBoldText(true);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawText(text, getBounds().centerX(), (getBounds().height()/2)+(textSize/2)-5, paint);

	}

	@Override
	public int getOpacity() {
		return 255;
	}

	@Override
	public void setAlpha(int arg0) {
	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
	}

	public String getText() {
		return text;
		
	}

}
