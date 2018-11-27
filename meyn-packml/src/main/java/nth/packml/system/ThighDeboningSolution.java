package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.DisplayButton;
import nth.packml.gui.displaybuttonpane.DisplayStateProperty;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.inputpane.InputButton;
import nth.packml.gui.inputpane.InputToggleButton;
import nth.packml.gui.outputpane.Output;
import nth.packml.mode.Mode;
import nth.packml.mode.ModeRequest;
import nth.packml.state.State;
import nth.packml.state.StateRequest;

public class ThighDeboningSolution extends System {

	private final PackMLStateMachine stateMachine;
	private final SimpleObjectProperty<Boolean> emergencyStop;
	private final SimpleObjectProperty<Boolean> machineDoorOpen;
	private final SimpleObjectProperty<Boolean> carrierTransportTripped;
	private final SimpleObjectProperty<Boolean> abortCondition;
	private final SimpleObjectProperty<Boolean> executeState;
	private final SimpleObjectProperty<Boolean> resetButtonLight;
	private InputToggleButton keyInProdOrManButton;
	private final SimpleObjectProperty<Boolean> startingState;

	public ThighDeboningSolution(PackMLStateMachine stateMachine) {
		this.stateMachine = stateMachine;
		emergencyStop = new SimpleObjectProperty<Boolean>(false);
		machineDoorOpen = new SimpleObjectProperty<Boolean>(false);
		carrierTransportTripped = new SimpleObjectProperty<Boolean>(false);
		resetButtonLight = createResetButtonLight();
		abortCondition = createAbortCondition();
		executeState = createStateProperty(State.EXECUTE);
		startingState = createStateProperty(State.STARTING);
	}

	private SimpleObjectProperty<Boolean> createResetButtonLight() {
		SimpleObjectProperty<Boolean> resetButtonLight = new SimpleObjectProperty<>();
		SimpleObjectProperty<State> stateProperty = stateMachine.getStateProperty();
		resetButtonLight.bind(stateProperty.isEqualTo(State.ABORTED).or(stateProperty.isEqualTo(State.ABORTING)));
		return resetButtonLight;
	}

	private SimpleObjectProperty<Boolean> createStateProperty(State state) {
		SimpleObjectProperty<Boolean> stateProperty = new SimpleObjectProperty<Boolean>();
		stateProperty.bind(stateMachine.getStateProperty().isEqualTo(state));
		return stateProperty;
	}

	private SimpleObjectProperty<Boolean> createAbortCondition() {
		SimpleObjectProperty<Boolean> abort = new SimpleObjectProperty<>();
		abort.bind(emergencyStop.isEqualTo(true).or(machineDoorOpen.isEqualTo(true))
				.or(carrierTransportTripped.isEqualTo(true)));
		abort.addListener(e -> {
			onAbort();
		});
		return abort;
	}

	private void onAbort() {
		stateMachine.fireRequest(StateRequest.ABORT);
	}

	@Override
	public String getName() {
		return "Theigh Deboning Solution";
	}

	@Override
	public ModeStateDescriptions getModeStateDescriptions() {
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptionsForMeyn();
		modeStateDescriptions.add(Mode.MANUAL, State.ABORTING);
		modeStateDescriptions.add(Mode.MANUAL, State.ABORTED);
		modeStateDescriptions.add(Mode.MANUAL, State.CLEARING);
		modeStateDescriptions.add(Mode.MANUAL, State.STOPPING);
		modeStateDescriptions.add(Mode.MANUAL, State.IDLE);
		modeStateDescriptions.add(Mode.MANUAL, State.STARTING);
		modeStateDescriptions.add(Mode.MANUAL, State.EXECUTE);

		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTED);
		modeStateDescriptions.add(Mode.PRODUCTION, State.CLEARING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.IDLE);
		modeStateDescriptions.add(Mode.PRODUCTION, State.STARTING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.EXECUTE);
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDING);
		modeStateDescriptions.add(Mode.PRODUCTION, State.SUSPENDED);
		modeStateDescriptions.add(Mode.PRODUCTION, State.UNSUSPENDING);

		modeStateDescriptions.add(Mode.CLEANING, State.ABORTING);
		modeStateDescriptions.add(Mode.CLEANING, State.ABORTED);
		modeStateDescriptions.add(Mode.CLEANING, State.CLEARING);
		modeStateDescriptions.add(Mode.CLEANING, State.STOPPING);
		modeStateDescriptions.add(Mode.CLEANING, State.IDLE);
		modeStateDescriptions.add(Mode.CLEANING, State.STARTING);
		modeStateDescriptions.add(Mode.CLEANING, State.EXECUTE);

		modeStateDescriptions.add(Mode.SERVICE, State.ABORTING);
		modeStateDescriptions.add(Mode.SERVICE, State.ABORTED);
		modeStateDescriptions.add(Mode.SERVICE, State.CLEARING);
		modeStateDescriptions.add(Mode.SERVICE, State.STOPPING);
		modeStateDescriptions.add(Mode.SERVICE, State.IDLE);
		modeStateDescriptions.add(Mode.SERVICE, State.STARTING);
		modeStateDescriptions.add(Mode.SERVICE, State.EXECUTE);

		return modeStateDescriptions;
	}

	@Override
	public List<Control> getInputs() {
		List<Control> inputs = new ArrayList<>();
		inputs.add(new InputButton("Start button anywhere", () -> {
			onStartButton();
		}));
		inputs.add(new InputButton("Stop button anywhere", () -> {
			onStopButton();
		}));
		inputs.add(new InputButton("Reset Button on display panel", () -> {
			onResetButton();
		}, resetButtonLight, Style.HIGHLIGHT_COLOR));

		ToggleGroup group = new ToggleGroup();

		InputToggleButton keyInCleaningButton = new InputToggleButton("Key in cleaning mode");
		keyInCleaningButton.setToggleGroup(group);
		keyInCleaningButton.setOnAction(e -> {
			stateMachine.fireRequest(ModeRequest.CLEANING);
		});
		inputs.add(keyInCleaningButton);

		keyInProdOrManButton = new InputToggleButton("Key in production or manual mode");
		keyInProdOrManButton.setToggleGroup(group);
		keyInProdOrManButton.setSelected(true);
		keyInProdOrManButton.setOnAction(e -> {
			stateMachine.fireRequest(ModeRequest.PRODUCTION);
		});
		inputs.add(keyInProdOrManButton);

		InputToggleButton keyInMaintenanceButton = new InputToggleButton("Key in maintenace mode");
		keyInMaintenanceButton.setToggleGroup(group);
		keyInMaintenanceButton.setOnAction(e -> {
			stateMachine.fireRequest(ModeRequest.SERVICE);
		});
		inputs.add(keyInMaintenanceButton);

		inputs.add(new InputToggleButton("Packing Line Error (Suspend)", () -> {
			stateMachine.fireRequest(StateRequest.SUSPEND);
		}, () -> {
			stateMachine.fireRequest(StateRequest.UNSUSPEND);
		}));

		inputs.add(new InputToggleButton("Emergency Stop button", emergencyStop, Style.ALARM_PRIORITY1_COLOR));
		inputs.add(new InputToggleButton("Machine door opened", machineDoorOpen, Style.ALARM_PRIORITY1_COLOR));
		inputs.add(new InputToggleButton("Carrier Transport Drive Tripped", carrierTransportTripped,
				Style.ALARM_PRIORITY1_COLOR));
		return inputs;
	}

	private void onResetButton() {
		if (!abortCondition.get()) {
			stateMachine.fireRequest(StateRequest.CLEAR);
		}
	}

	private void onStopButton() {
		stateMachine.fireRequest(StateRequest.STOP);
	}

	private void onStartButton() {
		stateMachine.fireRequest(StateRequest.START);
	}

	@Override
	public List<Output> getOutputs() {
		ArrayList<Output> outputs = new ArrayList<>();
		outputs.add(new Output("Emergency stop", Style.ALARM_PRIORITY1_COLOR, emergencyStop));
		outputs.add(new Output("Machine door open", Style.ALARM_PRIORITY1_COLOR, machineDoorOpen));
		outputs.add(new Output("Carrier transport tripped", Style.ALARM_PRIORITY1_COLOR, carrierTransportTripped));
		outputs.add(new Output("Start horn", Style.ON_COLOR, startingState));
		outputs.add(new Output("Carrier transport drive", Style.ON_COLOR, executeState));
		outputs.add(new Output("De-skinner", Style.ON_COLOR, executeState));
		outputs.add(new Output("Pump", Style.ON_COLOR, executeState));
		outputs.add(new Output("Reset Button", Style.HIGHLIGHT_COLOR, resetButtonLight));
		return outputs;
	}

	@Override
	public List<Control> getDisplayButtons() {
		ArrayList<Control> displayButtons = new ArrayList<>();
		SimpleObjectProperty<State> stateProperty = stateMachine.getStateProperty();
		displayButtons.add(createOnButton(stateProperty));

		displayButtons.add(createOffButton(stateProperty));

		displayButtons.add(createSeperator());

		DisplayButton manualButton = new DisplayButton(stateMachine, Mode.MANUAL);
		manualButton.setOnAction(e -> onManualButton());
		displayButtons.add(manualButton);

		DisplayButton productionButton = new DisplayButton(stateMachine, Mode.PRODUCTION);
		productionButton.setOnAction(e -> onProductionButton());
		displayButtons.add(productionButton);

		DisplayButton cleaningButton = new DisplayButton(stateMachine, Mode.CLEANING);
		cleaningButton.setOnAction(null);
		displayButtons.add(cleaningButton);

		DisplayButton maintenanceButton = new DisplayButton(stateMachine, Mode.SERVICE);
		maintenanceButton.setOnAction(null);
		displayButtons.add(maintenanceButton);

		displayButtons.add(createSeperator());

		displayButtons.add(createStateText());

		return displayButtons;
	}

	private Control createStateText() {
		SimpleObjectProperty<State> stateProperty = stateMachine.getStateProperty();
		Label label = new Label();
		label.setPrefHeight(40);
		label.setText(stateProperty.get().getDisplayText());
		label.setFont(new Font(15));
		stateProperty.addListener(e -> label.setText(stateProperty.get().getDisplayText()));
		return label;
	}

	private Control createSeperator() {
		Label seperator = new Label();
		seperator.setPrefWidth(15);
		return seperator;
	}

	private void onProductionButton() {
		if (keyInProdOrManButton.isSelected()) {
			stateMachine.fireRequest(ModeRequest.PRODUCTION);
		}
	}

	private void onManualButton() {
		if (keyInProdOrManButton.isSelected()) {
			stateMachine.fireRequest(ModeRequest.MANUAL);
		}
	}

	private DisplayButton createOffButton(SimpleObjectProperty<State> stateProperty) {
		DisplayStateProperty offDisplayStateProperty = new DisplayStateProperty(
				stateProperty.isEqualTo(State.STOPPED)
						.or(stateProperty.isEqualTo(State.ABORTED).or(stateProperty.isEqualTo(State.IDLE))
								.or(stateProperty.isEqualTo(State.CLEARING))),
				stateProperty.isEqualTo(State.STOPPING).or(stateProperty.isEqualTo(State.ABORTING)));
		DisplayButton offButton = new DisplayButton("0", offDisplayStateProperty);
		offButton.setOnAction(e -> onStopButton());
		return offButton;
	}

	private DisplayButton createOnButton(SimpleObjectProperty<State> stateProperty) {
		DisplayStateProperty onDisplayStateProperty = new DisplayStateProperty(stateProperty.isEqualTo(State.EXECUTE),
				stateProperty.isEqualTo(State.STARTING));
		DisplayButton onButton = new DisplayButton("1", onDisplayStateProperty);
		onButton.setOnAction(e -> onStartButton());
		return onButton;
	}

}
