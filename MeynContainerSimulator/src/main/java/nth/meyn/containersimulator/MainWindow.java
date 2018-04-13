package nth.meyn.containersimulator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.timefactor.TimeFactor;

public class MainWindow extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
			throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		primaryStage.setTitle("Meyn Container Simulation");

		Layout layout = new Layout(new TimeFactor(5));

		primaryStage.setScene(new Scene(layout, 300, 250));
		primaryStage.setMaximized(true);
		primaryStage.show();

		// TranslateTransition transition=new TranslateTransition();
		// transition.setNode(group);
		// transition.setDuration(Duration.seconds(12.0));
		// transition.setToX(300);
		// transition.setToY(300);
		// transition.onFinishedProperty(test);
		// transition.play();
		createTimeLine(layout.getUpdatables());

	}

	private void createTimeLine(List<Updatable> updatables) {
		Timeline timeLine = new Timeline();
		timeLine.setCycleCount(Animation.INDEFINITE);

		KeyFrame stateMachineUpdater = new KeyFrame(Duration.millis(200),
				new EventHandler<ActionEvent>() {

					public void handle(ActionEvent event) {
						for (Updatable updatable : updatables) {
							updatable.update();
						}
					}
				});

		timeLine.getKeyFrames().add(stateMachineUpdater);
		timeLine.play();
	}

	public void test() {

	}
}