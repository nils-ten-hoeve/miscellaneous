package nth.sprite.vetsupport.basicsprites.camera;

import nth.sprite.Canvas;
import nth.sprite.ImageSprite;
import nth.sprite.vetsupport.Constants;

public class Camera extends ImageSprite{

	public Camera(Canvas canvas, int x, int y) {
		super(canvas, "camera.png", Constants.WIDTH_CAMERA, Constants.HEIGHT_CAMERA);
		setX(x);
		setY(y);
	}


}
