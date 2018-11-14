package nth.packml.system;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nth.packml.gui.PackMLStateMachine;

public class SystemFactory {

	public static ObservableList<System> create(PackMLStateMachine stateMachine) {
		ObservableList<System> systems = FXCollections.observableArrayList(new PackMLSystem(stateMachine),
				new ThighDeboningSolution(stateMachine)
		// , new Rapid(stateMachine) ,
		// new Rehanger(stateMachine),
		// new EviscerationLine(stateMachine),
		// new DrawerSystem(stateMachine)
		);
		return systems;
	}
}
