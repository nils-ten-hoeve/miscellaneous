package nth.packml.system;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nth.packml.gui.PackMLStateMachine;
import nth.packml.gui.displaybuttonpane.DisplayButton;
import nth.packml.gui.displaybuttonpane.DisplayStateProperty;
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

	private Object createStarting() {
		// TODO Auto-generated method stub
		return null;
	}

	private SimpleObjectProperty<Boolean> createResetButtonLight() {
		SimpleObjectProperty<Boolean> resetButtonLight = new SimpleObjectProperty<>();
		SimpleObjectProperty<State> stateProperty = stateMachine.getStateProperty();
		resetButtonLight.bind(stateProperty.isEqualTo(State.ABORTED).or(stateProperty.isEqualTo(State.ABORTING)));
		return resetButtonLight;
	}

	private SimpleObjectProperty<Boolean> createStateProperty(State state) {
		SimpleObjectProperty<Boolean> running = new SimpleObjectProperty<Boolean>();
		running.bind(stateMachine.getStateProperty().isEqualTo(state));
		return running;
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
		ModeStateDescriptions modeStateDescriptions = new ModeStateDescriptions();
		modeStateDescriptions.add(Mode.MANUAL, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.MANUAL, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.MANUAL, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.MANUAL, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		modeStateDescriptions.add(Mode.MANUAL, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.MANUAL, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a startingState type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.MANUAL, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");

		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a startingState type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.PRODUCTION, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");

		modeStateDescriptions.add(Mode.CLEANING, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.CLEANING, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.CLEANING, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.CLEANING, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		modeStateDescriptions.add(Mode.CLEANING, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.CLEANING, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a startingState type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.CLEANING, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");

		modeStateDescriptions.add(Mode.MAINTENACE, State.ABORTING,
				"The ABORTED state can be entered at any time in response to the Abort command  or on the occurrence of a machine fault.  The aborting logic will bring the machine to a rapid, controlled safe stop.  Operation of the Emergency Stop will cause the machine to be tripped by its safety system it will also provide a signal to initiate the ABORTING State.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.ABORTED,
				"This state maintains machine status information relevant to the Abort condition. The Stop command will force transition to the Stopped state.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.CLEARING,
				"The procedural element has received a command to clear faults that may have occurred when ABORTING, and are present in the ABORTED state before proceeding to a STOPPED state.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.STOPPING,
				"This state executes the logic which brings the machine to a controlled and safe stop.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.IDLE,
				"This is a State which indicates that RESETING is complete. This state maintains the machine conditions which were achieved during the RESET state.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.STARTING,
				"This state provides the steps needed to start the machine and is a result of a startingState type command (local or remote). Following this command the machine will begin to “execute”.");
		modeStateDescriptions.add(Mode.MAINTENACE, State.EXECUTE,
				"Once the machine is processing materials it is deemed to be Executing or in the EXECUTE state. Execute refers to the mode in which the machine is in. If the machine is in the “Clean Out” mode then “execute” refers to the action of cleaning the machine.");

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
		}));

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
			stateMachine.fireRequest(ModeRequest.MAINTENACE);
		});
		inputs.add(keyInMaintenanceButton);

		inputs.add(new InputToggleButton("Emergency Stop button", emergencyStop));
		inputs.add(new InputToggleButton("Machine door opened", machineDoorOpen));
		inputs.add(new InputToggleButton("Carrier Transport Drive Tripped", carrierTransportTripped));
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
		outputs.add(new Output("Emergency stop", Color.RED, emergencyStop));
		outputs.add(new Output("Machine door open", Color.RED, machineDoorOpen));
		outputs.add(new Output("Carrier transport tripped", Color.RED, carrierTransportTripped));
		outputs.add(new Output("Start horn", Color.GREEN, startingState));
		outputs.add(new Output("Carrier transport drive", Color.GREEN, executeState));
		outputs.add(new Output("De-skinner", Color.GREEN, executeState));
		outputs.add(new Output("Pump", Color.GREEN, executeState));
		outputs.add(new Output("Reset Button", Color.BLUE, resetButtonLight));
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

		DisplayButton maintenanceButton = new DisplayButton(stateMachine, Mode.MAINTENACE);
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
		DisplayButton offButton = new DisplayButton("0", offDisplayStateProperty, Color.GRAY, 40);
		offButton.setOnAction(e -> onStopButton());
		return offButton;
	}

	private DisplayButton createOnButton(SimpleObjectProperty<State> stateProperty) {
		DisplayStateProperty onDisplayStateProperty = new DisplayStateProperty(stateProperty.isEqualTo(State.EXECUTE),
				stateProperty.isEqualTo(State.STARTING));
		DisplayButton onButton = new DisplayButton("1", onDisplayStateProperty, Color.GRAY, 40);
		onButton.setOnAction(e -> onStartButton());
		return onButton;
	}

}
