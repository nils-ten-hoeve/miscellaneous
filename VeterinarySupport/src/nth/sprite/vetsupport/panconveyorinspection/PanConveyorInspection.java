package nth.sprite.vetsupport.panconveyorinspection;

import java.awt.Color;
import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;
import nth.sprite.vetsupport.basicsprites.bar.SelectionBar;
import nth.sprite.vetsupport.basicsprites.callout.CallOut;
import nth.sprite.vetsupport.basicsprites.callout.CallOut.ArrowLocation;
import nth.sprite.vetsupport.basicsprites.hand.Hand;

public class PanConveyorInspection extends Scene {

	public PanConveyorInspection(Canvas canvas) {
		super(canvas);
		List<Sprite> sprites = getSprites();
		sprites.add(new SelectionBar(canvas));
		EvisLine evisLine = new EvisLine(canvas);
		sprites.add(evisLine);
		sprites.add(new Hand(canvas, evisLine, 400));
		sprites.add(new CallOut(this, 200, 5, "Pan Conveyor Inspection Support System", Color.DARK_GRAY));
		sprites.add(new CallOut(this, 250, 50, ArrowLocation.BOTTOM, 300, 350, "Grading bar:\n- Easy to operate\n- Hygenic design\n- Water proof"));
		sprites.add(new CallOut(this, 400, 30, ArrowLocation.BOTTOM, 500, 350, "Lights:\n Red=Condamed\n Orange=Rework\n Green=O.K."));
		sprites.add(new CallOut(this, 400, 30, ArrowLocation.BOTTOM, 500, 350, "Touch a light on top of the bar\nto condamn a bird\n(change to a red light)"));
		sprites.add(new CallOut(this, 400, 30, ArrowLocation.BOTTOM, 500, 350, "Touch a light on bottom of the bar\nto rework a bird\n(change to an orange light)"));
		sprites.add(new CallOut(this, 350, 30, ArrowLocation.BOTTOM, 500, 350, "Touch a light on top and bottom of the bar\nto correct a bird\n(change back to a green light)"));
		sprites.add(new CallOut(this, 250, 150, "Do you need more than 3 colors?\nI.E. to register register different deceases?\nIf so, which deceases and which colours?", Color.RED));
	}

}
