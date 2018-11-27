package nth.packml.gui.displaybuttonpane;

import java.io.InputStream;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import nth.packml.gui.MeynPackML;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.statepane.BackgroundFactory;
import nth.packml.mode.Mode;
import nth.packml.mode.ModeRequest;

public class DisplayButton extends Button {

	private final Background onBackGround;
	private final Background offBackGround;
	private final Transition blinkTransition;

	public DisplayButton(String text, DisplayStateProperty displayButtonStateProperty) {
		super(text);
		setPrefHeight(40);
		setPrefWidth(50);
		setPadding(new Insets(0));
		setTextFill(Style.TEXT_COLOR);
		Font newFont = new Font(25);
		setFont(newFont);
		onBackGround = BackgroundFactory.create(Style.ON_COLOR, Style.ROUNDING);
		offBackGround = BackgroundFactory.create(Style.OFF_COLOR, Style.ROUNDING);
		blinkTransition = createBlinkTransition();
		displayButtonStateProperty.addListener(e -> onStateChange(displayButtonStateProperty));
		onStateChange(displayButtonStateProperty);
	}

	public DisplayButton(PackMLStateMachine stateMachine, Mode mode) {
		this(mode.getDisplayText(), createDisplayStateProperty(stateMachine, mode));
		ModeRequest modeRequest = ModeRequest.valueOf(mode.toString());
		setOnAction(e -> stateMachine.fireRequest(modeRequest));
		setTooltip(new Tooltip("Right click for more information."));
		addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onMouseClick(e, mode));
		InputStream resourceAsStream = MeynPackML.class.getResourceAsStream("meyniconfont.ttf");
		Font font = Font.loadFont(resourceAsStream, 25);
		setFont(font);
		setText(mode.getMeynIconFontCharacter());
	}

	public void onMouseClick(MouseEvent event, Mode mode) {
		if (event.getButton().name().equals("SECONDARY")) {
			showInfoDialog(mode);
		}
	}

	private void showInfoDialog(Mode mode) {
		StringBuilder stateDescription = new StringBuilder();
		stateDescription.append(mode.getDisplayText());
		stateDescription.append(" Mode:\n\n");
		stateDescription.append(mode.getDescription());
		Alert alert = new Alert(AlertType.INFORMATION, stateDescription.toString());
		alert.showAndWait();
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
			setBackground(onBackGround);
			break;
		case BLINKING:
			blinkTransition.play();
			break;
		default:
			blinkTransition.stop();
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
