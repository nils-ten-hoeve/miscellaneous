package nth.sprite.vetsupport.panconveyorinspection;

import java.awt.Color;
import java.util.List;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Sprite;
import nth.sprite.Text;
import nth.sprite.vetsupport.basicsprites.bar.SelectionBar;
import nth.sprite.vetsupport.basicsprites.callout.CallOut;
import nth.sprite.vetsupport.basicsprites.callout.CallOut.ArrowLocation;
import nth.sprite.vetsupport.basicsprites.camera.Camera;
import nth.sprite.vetsupport.basicsprites.camera.Sensor;
import nth.sprite.vetsupport.basicsprites.computer.Desktop;
import nth.sprite.vetsupport.basicsprites.computer.LotActivation;
import nth.sprite.vetsupport.basicsprites.computer.M4000;
import nth.sprite.vetsupport.basicsprites.computer.Server;
import nth.sprite.vetsupport.basicsprites.hand.SimpleHand;
import nth.sprite.vetsupport.basicsprites.line.Line;
import nth.sprite.vetsupport.basicsprites.shackle.EvisShackle;
import nth.sprite.vetsupport.basicsprites.unloader.Unloader;

public class TrackingSystem extends Scene {

	public TrackingSystem(Canvas canvas) {
		super(canvas);
		List<Sprite> sprites = getSprites();
		sprites.add(new Text(canvas,"Lot Activation Points",50,135));
		sprites.add(new LotActivation(canvas,30,20));
		
		sprites.add(new Text(canvas,"Camera systems",180,135));
		sprites.add(new Camera(canvas,180,40));
		
		sprites.add(new Text(canvas,"Grading Bars",310,135));
		sprites.add(new SelectionBar(canvas,280,130,40));
		sprites.add(new SimpleHand(canvas,320,50));
		
		sprites.add(new Text(canvas,"Tracking Bars",480,135));
		sprites.add(new SelectionBar(canvas,450,130,40));
		sprites.add(new EvisShackle(canvas,465,-100));
		sprites.add(new EvisShackle(canvas,515,-100));
		
		sprites.add(new Text(canvas,"Unloaders",620,135));
		sprites.add(new Unloader(canvas,620,20));
		
		sprites.add(new Text(canvas,"Sensors",710,135));
		sprites.add(new Sensor(canvas,710,50));
		
		sprites.add(new Text(canvas,"Industrial Network",380,190));
		sprites.add(new Line(canvas,100,170,630,3));
		sprites.add(new Line(canvas,100,140,3,30));
		sprites.add(new Line(canvas,230,140,3,30));
		sprites.add(new Line(canvas,340,140,3,30));
		sprites.add(new Line(canvas,520,140,3,30));
		sprites.add(new Line(canvas,650,140,3,30));
		sprites.add(new Line(canvas,727,140,3,30));
		sprites.add(new Line(canvas,320,170,3,30));
		
		sprites.add(new Text(canvas,"Tracking System (M4000 like)",235,370));
		sprites.add(new M4000(canvas,280,200));
		
		sprites.add(new Text(canvas,"Database and Report Server",460,370));
		sprites.add(new Server(canvas,470,310));
		
		sprites.add(new Text(canvas,"Office Network",420,420));
		sprites.add(new Line(canvas,100,400,630,3));
		sprites.add(new Line(canvas,320,375,3,25));
		sprites.add(new Line(canvas,530,375,3,25));
		sprites.add(new Line(canvas,150,400,3,30));
		sprites.add(new Line(canvas,400,400,3,30));
		sprites.add(new Line(canvas,650,400,3,30));
		
		sprites.add(new Text(canvas,"Desktop computers for flock administration and managing reports and settings",210,530));
		sprites.add(new Desktop(canvas,100,420));
		sprites.add(new Desktop(canvas,350,420));
		sprites.add(new Desktop(canvas,600,420));
		
		
		sprites.add(new CallOut(this,250,5, "Tracking System", Color.DARK_GRAY));
		sprites.add(new CallOut(this,150,250, ArrowLocation.BOTTOM, 150,450, "Expected flocks are entered into the system"));
		sprites.add(new CallOut(this,150,250, ArrowLocation.TOP, 100,100,"Flocks are activated at hanging with a touch screen or button"));
		sprites.add(new CallOut(this,370,250, ArrowLocation.LEFT, 300,330, "Industrual Tracking Computer\n- Tracks individual products in shackles\n- Tracks Lots\n- Tracks grading\n- Stores results in a database (optional)\n- Controlls lights on tracking and grading bars\n- Controlls unloaders\n- Compensates chain wair"));
		sprites.add(new CallOut(this,100,200, ArrowLocation.RIGHT, 500,330,"Optional Server:\n- SQL database\n- Reporting software"));
		sprites.add(new CallOut(this,10,200, "Data that can be stored in database:\n(per flock and or time period)\n- Bird counts\n- One leggers\n- Feet-Carcas-Giblet Defects\n- Rework\n- Line efficientcy\n- Flock administration\n- ..."));
		sprites.add(new CallOut(this,100,200, "Would this work for you?\nWhat type of reports do you realy need (examples?)?", Color.RED));
	}
	
}
