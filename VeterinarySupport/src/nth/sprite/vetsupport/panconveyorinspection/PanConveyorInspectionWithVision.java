package nth.sprite.vetsupport.panconveyorinspection;

import java.awt.Color;
import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.SelectionBar;
import nth.sprite.vetsupport.basicsprites.callout.CallOut;
import nth.sprite.vetsupport.basicsprites.callout.CallOut.ArrowLocation;
import nth.sprite.vetsupport.basicsprites.camera.EvisCamera1;
import nth.sprite.vetsupport.basicsprites.camera.EvisCamera2;
import nth.sprite.vetsupport.basicsprites.camera.EvisCamera3;
import nth.sprite.vetsupport.basicsprites.hand.Hand;

public class PanConveyorInspectionWithVision extends Scene {

	public PanConveyorInspectionWithVision(Canvas canvas) {
		super(canvas);
		List<Sprite> sprites = getSprites();
		sprites.add(new SelectionBar(canvas,270,canvas.getParent().getWidth()-270 ,Constants.Y_LOW_SELECTION_BAR));
		sprites.add(new SelectionBar(canvas,0,290 ,Constants.Y_HIGH_SELECTION_BAR));
		
		EvisLine evisLine = new EvisLine(canvas, true, 250,canvas.getParent().getWidth(),false);
		sprites.add(evisLine);
		sprites.add(new Hand(canvas,evisLine, 600));
		
		sprites.add(new EvisCamera1(canvas,evisLine, 50, Constants.Y_LOW_SELECTION_BAR));
		sprites.add(new EvisCamera2(canvas,evisLine, 300, Constants.Y_HIGH_SELECTION_BAR));
		sprites.add(new EvisCamera3(canvas,evisLine, 450, Constants.Y_HAND+20));
		
		sprites.add(new CallOut(this,200,5, "Pan Conveyor Inspection With Pre-Grading", Color.GRAY));
		
		sprites.add(new CallOut(this,150,10, ArrowLocation.LEFT, 80,Constants.Y_LOW_SELECTION_BAR, "Optional pre-grading\nA digital camera to detect:\n- Empty shackles\n- Small birds\n- Red birds\n- Damaged birds\n- One leggers\n- ..."));
		sprites.add(new CallOut(this,400,10, ArrowLocation.LEFT, 330,Constants.Y_HIGH_SELECTION_BAR, "Optional pre-grading\nA digital camera to detect:\n- Un opened birds\n- Recidu in cavety\n- ..."));
		sprites.add(new CallOut(this,20,160, ArrowLocation.LEFT, 450,Constants.Y_HAND+20, "Optional pre-grading\nA digital camera to detect:\n- Packs of sick birds\n- Gall\n- ..."));
		
		sprites.add(new CallOut(this,20,30, ArrowLocation.RIGHT, 630,Constants.Y_HAND, "The inspector can always correct a pre-grade of a camera\n"));
		
		sprites.add(new CallOut(this,150,10, ArrowLocation.LEFT, 80,Constants.Y_LOW_SELECTION_BAR, "Would this camera be of help?\nWhat does it need to detect?\nWhere would this camera idealy located?", java.awt.Color.RED));
		sprites.add(new CallOut(this,400,10, ArrowLocation.LEFT, 330,Constants.Y_HIGH_SELECTION_BAR, "Would this camera be of help?\nWhat does it need to detect?\nWhere would this camera idealy located?", java.awt.Color.RED));
		sprites.add(new CallOut(this,20,160, ArrowLocation.LEFT, 450,Constants.Y_HAND+20, "Would this camera be of help?\nWhat does it need to detect?", java.awt.Color.RED));
		
		sprites.add(new CallOut(this,150,10, "Wich camera(s) would be most important and why?", java.awt.Color.RED));


		
	}
	
}
