package nth.sprite.vetsupport.panconveyorinspection;

import java.awt.Color;
import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;
import nth.sprite.vetsupport.basicsprites.bar.SelectionBar;
import nth.sprite.vetsupport.basicsprites.callout.CallOut;
import nth.sprite.vetsupport.basicsprites.hand.Hand;
import nth.sprite.vetsupport.basicsprites.shackleline.LineDivider1;
import nth.sprite.vetsupport.basicsprites.shackleline.LineDivider2;

public class PanConveyorInspectionWithMultipleStations extends Scene {

	public PanConveyorInspectionWithMultipleStations(Canvas canvas) {
		super(canvas);
		List<Sprite> sprites = getSprites();
		sprites.add(new SelectionBar(canvas));
		sprites.add(new LineDivider1(canvas));
		sprites.add(new LineDivider2(canvas));
		EvisLine evisLine = new EvisLine(canvas,false);
		sprites.add(evisLine);
		sprites.add(new Hand(canvas,evisLine, 400));
		sprites.add(new CallOut(this,100,5, "Pan Conveyor Inspection With Multiple Inspection Stations ", Color.DARK_GRAY));
		sprites.add(new CallOut(this,450,10, "Grading bar also works with\nmultiple inspecton stations"));
		
		
		
		
		
		
	}
	
}
