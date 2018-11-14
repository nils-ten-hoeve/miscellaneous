package nth.packml.gui.displaybuttonpane;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.statepane.BackgroundFactory;
import nth.packml.mode.Mode;
import nth.packml.mode.ModeRequest;

public class DisplayButton extends Button {

	private static final int ROUNDING = 5;
	private static final Color OFF_COLOR = Color.BLACK;
	private final Background onBackGround;
	private final Background offBackGround;
	private final Transition blinkTransition;

	public DisplayButton(String text, DisplayStateProperty displayButtonStateProperty, Color onColor, double width) {
		super(text);
		setPrefHeight(40);
		setPrefWidth(width);
		onBackGround = BackgroundFactory.create(onColor, ROUNDING);
		offBackGround = BackgroundFactory.create(OFF_COLOR, ROUNDING);
		blinkTransition = createBlinkTransition();
		displayButtonStateProperty.addListener(e -> {
			onStateChange(displayButtonStateProperty);
		});
		onStateChange(displayButtonStateProperty);
	}

	public DisplayButton(PackMLStateMachine stateMachine, Mode mode) {
		this(mode.getDisplayText(), createDisplayStateProperty(stateMachine, mode), Color.BLUE, 80);
		ModeRequest modeRequest = ModeRequest.valueOf(mode.toString());
		setOnAction(e -> stateMachine.fireRequest(modeRequest));
	}

	private static DisplayStateProperty createDisplayStateProperty(PackMLStateMachine stateMachine, Mode mode) {
		SimpleObjectProperty<Mode> modeProperty = stateMachine.getModeProperty();
		SimpleObjectProperty<Mode> waitingModeProperty = stateMachine.getWaitingModeProperty();
		DisplayStateProperty displayStateProperty = new DisplayStateProperty(modeProperty.isEqualTo(mode),
				modeProperty.isNotEqualTo(mode).and(waitingModeProperty.isEqualTo(mode)));
		return displayStateProperty;
	}

	private void onStateChange(DisplayStateProperty displayButtonStateProperty) {
		switch (displayButtonStateProperty.get()) {
		case ON:
			blinkTransition.stop();
			setTextFill(Color.WHITE);
			setBackground(onBackGround);
			break;
		case BLINKING:
			blinkTransition.play();
			break;
		default:
			blinkTransition.stop();
			setTextFill(Color.WHITE);
			setBackground(offBackGround);
			break;
		}

	}

	private Transition createBlinkTransition() {
		Transition blinkTransition = new Transition() {
			{
				setCycleDuration(javafx.util.Duration.millis(1000));
			}

			@Override
			protected void interpolate(double frac) {
				if (frac <= 0.1) {
					setBackground(onBackGround);
				} else if (frac >= 0.9) {
					setBackground(offBackGround);
				}

			}
		};
		blinkTransition.setCycleCount(Animation.INDEFINITE);
		return blinkTransition;
	}

}
