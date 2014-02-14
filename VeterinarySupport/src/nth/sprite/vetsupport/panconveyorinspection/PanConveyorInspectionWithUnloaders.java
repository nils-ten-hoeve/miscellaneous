package nth.sprite.vetsupport.panconveyorinspection;

import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;
import nth.sprite.Text;
import nth.sprite.vetsupport.Constants;
import nth.sprite.vetsupport.basicsprites.bar.Light.Color;
import nth.sprite.vetsupport.basicsprites.bar.SelectionBar;
import nth.sprite.vetsupport.basicsprites.callout.CallOut;
import nth.sprite.vetsupport.basicsprites.callout.CallOut.ArrowLocation;
import nth.sprite.vetsupport.basicsprites.camera.Sensor;
import nth.sprite.vetsupport.basicsprites.hand.Hand;
import nth.sprite.vetsupport.basicsprites.unloader.Unloader;

public class PanConveyorInspectionWithUnloaders extends Scene {

	public PanConveyorInspectionWithUnloaders(Canvas canvas) {
		super(canvas);
		List<Sprite> sprites = getSprites();
		sprites.add(new SelectionBar(canvas,0,350,Constants.Y_LOW_SELECTION_BAR));
		sprites.add(new SelectionBar(canvas,320,canvas.getParent().getWidth()-320 ,Constants.Y_HIGH_SELECTION_BAR));
		
		EvisLine evisLine = new EvisLine(canvas, true, -80,300,true);
		sprites.add(evisLine);
		sprites.add(new Hand(canvas,evisLine, 100));

		sprites.add(new Unloader(canvas,evisLine,500,Color.RED));
		sprites.add(new Text(canvas,"Unloader for",490,Constants.Y_TEXT));
		sprites.add(new Text(canvas,"condemned birds",480,Constants.Y_TEXT+20));

		
		sprites.add(new Unloader(canvas,evisLine,650,Color.ORANGE));
		sprites.add(new Text(canvas,"Unloader for",640,Constants.Y_TEXT));
		sprites.add(new Text(canvas,"rework birds",640,Constants.Y_TEXT+20));

		sprites.add(new Sensor(canvas,750,325));
		
		sprites.add(new CallOut(this,200,5, "Pan Conveyor Inspection With Unloaders", java.awt.Color.DARK_GRAY));
		
		sprites.add(new CallOut(this,5,10, ArrowLocation.RIGHT, 530,Constants.Y_HIGH_SELECTION_BAR_LIGHT+10, "Tracking bar (without touch sensors)\n- Condamned and rework birds always visible\n- Easy to validate tracking system and unloaders"));
		sprites.add(new CallOut(this,10,375, ArrowLocation.RIGHT, 530,325, "Bird unloader to automatically unload\ncondamned birds in a bin"));
		sprites.add(new CallOut(this,100,350, ArrowLocation.BOTTOM, 680,325, "Bird unloader to automatically unload rework birds.\nRework birds can be dropped in a bin\nor (automatically) re-hung in a salvage line."));
		sprites.add(new CallOut(this,10,180, ArrowLocation.TOP, 750,335, "Optional sensor and alarm to detect birds\nthat where missed by the unloaders"));

		sprites.add(new CallOut(this,10,375, "Would this wor for you?\nWhat are the pro's and con's?", java.awt.Color.RED));
		sprites.add(new CallOut(this,350,375, ArrowLocation.RIGHT, 280,400, "Would you want to have a pack unloader?\nUnload packes of all condamned birds?", java.awt.Color.RED));
			
		
	}
	
}
