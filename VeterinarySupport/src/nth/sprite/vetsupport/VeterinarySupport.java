package nth.sprite.vetsupport;

import java.util.ArrayList;

import javax.swing.JFrame;

import nth.sprite.Canvas;
import nth.sprite.Scene;
import nth.sprite.Story;
import nth.sprite.vetsupport.panconveyorinspection.PanConveyorInspection;
import nth.sprite.vetsupport.panconveyorinspection.PanConveyorInspectionWithMultipleStations;
import nth.sprite.vetsupport.panconveyorinspection.PanConveyorInspectionWithUnloaders;
import nth.sprite.vetsupport.panconveyorinspection.PanConveyorInspectionWithVision;
import nth.sprite.vetsupport.panconveyorinspection.TrackingSystem;

public class VeterinarySupport extends JFrame {

	private static final long serialVersionUID = -5639850068871313915L;

	public VeterinarySupport() {
		setSize(800, 570);
		setVisible(true);
		// initialize canvas
		Canvas canvas = new Canvas();
		add(canvas);
		repaint();
		// create scenes
		ArrayList<Scene> scenes = new ArrayList<Scene>();

		PanConveyorInspection panConveyorInspection = new PanConveyorInspection(canvas);
		scenes.add(panConveyorInspection);
		
		PanConveyorInspectionWithMultipleStations panConveyorInspectionWithMultipleStations=new PanConveyorInspectionWithMultipleStations(canvas);
		scenes.add(panConveyorInspectionWithMultipleStations);

		PanConveyorInspectionWithUnloaders panConveyorInspectionWithUnloaders=new PanConveyorInspectionWithUnloaders(canvas);
		scenes.add(panConveyorInspectionWithUnloaders);

		PanConveyorInspectionWithVision panConveyorInspectionWithVision=new PanConveyorInspectionWithVision(canvas);
		scenes.add(panConveyorInspectionWithVision);

		TrackingSystem trackingSystem=new TrackingSystem(canvas);
		scenes.add(trackingSystem);
		
		// create story and add it to the canvas
		Story story = new Story(canvas, scenes);
		canvas.setSpriteCollection(story);

		// initialize frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Veterinary Support (Idea by Nils ten Hoeve)");
		setResizable(false);
		canvas.requestFocusInWindow();//needed for keyboard support
		

	}

	public static void main(String[] args) {
		new VeterinarySupport();
	}
}