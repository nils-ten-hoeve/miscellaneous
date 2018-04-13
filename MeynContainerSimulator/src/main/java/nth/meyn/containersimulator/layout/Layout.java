package nth.meyn.containersimulator.layout;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import nth.meyn.containersimulator.Updatable;
import nth.meyn.containersimulator.destalloc.DestinationAllocator;
import nth.meyn.containersimulator.eventlog.EventLog;
import nth.meyn.containersimulator.routemanager.RouteManager;
import nth.meyn.containersimulator.stack.transfer.StackTransfer;
import nth.meyn.containersimulator.stack.transfer.StackTransfers;
import nth.meyn.containersimulator.timefactor.TimeFactor;
import nth.meyn.containersimulator.unit.StackDestination;
import nth.meyn.containersimulator.unit.StackPosition;
import nth.meyn.containersimulator.unit.StackPositions;
import nth.meyn.containersimulator.unit.StackSource;
import nth.meyn.containersimulator.unit.cas.Cas;
import nth.meyn.containersimulator.unit.cas.starter.CasStarter;
import nth.meyn.containersimulator.unit.conveyor.Conveyor;
import nth.meyn.containersimulator.unit.shiftframe.position.ShiftFrame;
import nth.meyn.containersimulator.unit.shiftframe.position.ShiftFramePosition;
import nth.meyn.containersimulator.unit.stack.finish.StackFinish;
import nth.meyn.containersimulator.unit.stack.supply.StackSupply;
import nth.meyn.containersimulator.unit.turntable.TurnTable;
import nth.meyn.containersimulator.unit.turntable.TurnTableStackDestination;
import nth.meyn.containersimulator.unit.turntable.TurnTableStackSource;

public class Layout extends ScrollPane {

	private static final String CNV2 = "CNV2";
	private static final String CAS1 = "CAS1";
	private static final String CAS2 = "CAS2";
	private static final String CAS3 = "CAS3";
	private static final String CAS4 = "CAS4";
	private static final String CAS5 = "CAS5";
	private static final String CAS6 = "CAS6";
	private static final String CNV1 = "CNV1";
	private static final String FINISH = "Finish";
	private static final String SKATES = "Skates";
	private static final String TT1 = "TT1";
	private static final String TT2 = "TT2";
	private static final String TT3 = "TT3";
	private final Pane pane;
	private final List<Updatable> updatables;
	private final StackPositions stackPositions;
	private final StackTransfers stackTransfers;
	private final RouteManager routeManager;
	private final EventLog eventLog;
	private final TimeFactor timeFactor;
	private final CasStarter casStarter;

	public Layout(TimeFactor timeFactor)
			throws NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		pane = new Pane();
		setContent(pane);

		this.timeFactor = timeFactor;

		this.eventLog = new EventLog(timeFactor);

		this.stackPositions = createStackPositions();

		this.stackTransfers = createStackTransfers(stackPositions);

		this.routeManager = new RouteManager(stackTransfers);

		DestinationAllocator destinationAllocator = new DestinationAllocator(routeManager,
				stackPositions.get(CNV1));

		this.casStarter = new CasStarter(stackPositions, timeFactor, Duration.ofSeconds(60),
				Duration.ofSeconds(10));

		createTurnPositions(stackTransfers);

		updatables = new ArrayList<>();
		updatables.addAll(stackPositions.getUpdatables());
		updatables.addAll(stackTransfers);
		updatables.add(casStarter);
		updatables.add(destinationAllocator);
	}

	private void createTurnPositions(StackTransfers stackTransfers) {
		createFromTurnTablePositions(stackTransfers);
		createToTurnTablePositions(stackTransfers);
	}

	private void createFromTurnTablePositions(StackTransfers stackTransfers) {
		for (StackTransfer stackTransfer : stackTransfers) {
			StackSource source = stackTransfer.getStackSource();
			if (source instanceof TurnTableStackSource) {
				TurnTableStackSource turnTableStackSource = (TurnTableStackSource) source;
				TurnTable turnTable = turnTableStackSource.getTurnTable();
				turnTable.addFromTurnTablePositions(stackTransfer);
			}
		}
	}

	private void createToTurnTablePositions(StackTransfers stackTransfers) {
		for (StackTransfer stackTransfer : stackTransfers) {
			StackDestination destination = stackTransfer.getStackDestination();
			if (destination instanceof TurnTableStackDestination) {
				TurnTableStackDestination turnTableStackDestination = (TurnTableStackDestination) destination;
				TurnTable turnTable = turnTableStackDestination.getTurnTable();
				turnTable.addToTurnTablePositions(stackTransfer);
			}
		}
	}

	private StackPositions createStackPositions() {

		StackPositions stackPositions = new StackPositions(this);

		Conveyor conveyor1 = new Conveyor(this, CNV1, 110, 120, 90);
		stackPositions.add(conveyor1);

		StackSupply skates = new StackSupply(this, SKATES, 10, 120, 90, conveyor1);
		stackPositions.add(skates);

		TurnTable turnTable1 = new TurnTable(this, TT1, 205, 105, 90);
		stackPositions.add(turnTable1);


		Cas cas5 = new Cas(this, CAS5, 210, 220, 0);
		stackPositions.add(cas5);

		Cas cas6 = new Cas(this, CAS6, 210, 10, 0);
		stackPositions.add(cas6);

		
		
		TurnTable turnTable2 = new TurnTable(this, TT2, 315, 105, 90);
		stackPositions.add(turnTable2);


		Cas cas3 = new Cas(this, CAS3, 320, 220, 0);
		stackPositions.add(cas3);

		Cas cas4 = new Cas(this, CAS4, 320, 10, 0);
		stackPositions.add(cas4);

		TurnTable turnTable3 = new TurnTable(this, TT3, 425, 105, 90);
		stackPositions.add(turnTable3);


		Cas cas1 = new Cas(this, CAS1, 420, 220, 0);
		stackPositions.add(cas1);

		Cas cas2 = new Cas(this, CAS2, 420, 10, 0);
		stackPositions.add(cas2);
		
		
		Conveyor conveyor2 = new Conveyor(this, CNV2, 530, 120, 90);
		stackPositions.add(conveyor2);

		
		
		
		StackFinish stackFinish = new StackFinish(this, FINISH, 630, 120, 90);
		stackPositions.add(stackFinish);

//		List<StackPosition> shiftFramePositions = new ArrayList<>();
//		for (int i = 0; i < 4; i++) {
//			ShiftFramePosition sfpos = new ShiftFramePosition("Sf1.pos" + i, 400 + 60 * i, 400, 90);
//			stackPositions.add(sfpos);
//			shiftFramePositions.add(sfpos);
//		}
//
//		ShiftFrame shiftFrame = new ShiftFrame(this, "Sf1", 400, 400, 90, shiftFramePositions);
//		stackPositions.add(shiftFrame);

		return stackPositions;
	}

	private StackTransfers createStackTransfers(StackPositions stackPositions) {
		StackTransfers stackTransfers = new StackTransfers();
		stackTransfers.add(new StackTransfer(this, stackPositions, SKATES, CNV1));
		stackTransfers.add(new StackTransfer(this, stackPositions, CNV1, TT1));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT1, CAS5));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS5, TT1));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT1, CAS6));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS6, TT1));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT1, TT2));
		
		stackTransfers.add(new StackTransfer(this, stackPositions, TT2, CAS3));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS3, TT2));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT2, CAS4));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS4, TT2));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT2, TT3));
		
		stackTransfers.add(new StackTransfer(this, stackPositions, TT3, CAS1));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS1, TT3));

		stackTransfers.add(new StackTransfer(this, stackPositions, TT3, CAS2));
		stackTransfers.add(new StackTransfer(this, stackPositions, CAS2, TT3));
		
		stackTransfers.add(new StackTransfer(this, stackPositions, TT3, CNV2));
		stackTransfers.add(new StackTransfer(this, stackPositions, CNV2, FINISH));
		return stackTransfers;
	}

	public void addPresentation(Node node) {
		pane.getChildren().add(node);
		node.toBack();
	}

	public List<Updatable> getUpdatables() {
		return updatables;
	}

	public StackPositions getStackPositions() {
		return stackPositions;
	}

	public List<StackTransfer> getStackTransfers() {
		return stackTransfers;
	}

	public RouteManager getRouteManager() {
		return routeManager;
	}

	public EventLog getEventLog() {
		return eventLog;
	}

	public TimeFactor getTimeFactor() {
		return timeFactor;
	}

}
