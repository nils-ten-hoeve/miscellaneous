package nth.packml.gui;

import java.io.InputStream;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import nth.packml.gui.displaybuttonpane.DisplayButtonPane;
import nth.packml.gui.inputpane.InputPane;
import nth.packml.gui.outputpane.OutputPane;
import nth.packml.gui.statepane.StatePane;
import nth.packml.gui.toolbar.TopToolBar;
import nth.packml.mode.ModeRequest;
import nth.packml.state.State;
import nth.packml.state.listener.DelayedStageRequestFactory;

public class MeynPackML extends Application {

	private static final String OMRON_NA_DISPLAY_PNG = "OmronNaDisplay.png";
	private final PackMLStateMachine stateMachine;

	public static void main(String[] args) {
		launch(args);
	}

	public MeynPackML() {
		stateMachine = new PackMLStateMachine(this);
		addDelayedStateRequests();

	}

	private void addDelayedStateRequests() {
		List<ChangeListener<State>> listeners = DelayedStageRequestFactory.create(stateMachine);
		for (ChangeListener<State> listener : listeners) {
			stateMachine.getStateProperty().addListener(listener);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Meyn and Pack-ML example");

		ImageView omronDisplayImage = createOmronDisplayImage();
		StatePane statePane = new StatePane(stateMachine);
		DisplayButtonPane displayButtonPane = new DisplayButtonPane(stateMachine);
		InputPane inputPane = new InputPane(stateMachine);
		OutputPane outputPane = new OutputPane(stateMachine);
		TopToolBar topToolBar = new TopToolBar(stateMachine);

		StackPane root = new StackPane();
		root.getChildren().addAll(omronDisplayImage, topToolBar, displayButtonPane, inputPane, outputPane, statePane);
		StackPane.setAlignment(omronDisplayImage, Pos.TOP_LEFT);

		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		primaryStage.setResizable(false);

		stateMachine.fireRequest(ModeRequest.PRODUCTION);
	}

	private ImageView createOmronDisplayImage() {
		InputStream resource = getClass().getResourceAsStream(OMRON_NA_DISPLAY_PNG);
		ImageView displayImage = new ImageView();
		displayImage.setFitHeight(800);
		displayImage.setPreserveRatio(true);
		displayImage.setSmooth(true);
		displayImage.setCache(true);
		displayImage.setImage(new Image(resource));
		return displayImage;
	}

	public PackMLStateMachine getStateMachine() {
		return stateMachine;
	}

}
