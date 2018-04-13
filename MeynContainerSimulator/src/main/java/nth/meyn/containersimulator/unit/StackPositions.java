package nth.meyn.containersimulator.unit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nth.meyn.containersimulator.PresentationOwner;
import nth.meyn.containersimulator.Updatable;
import nth.meyn.containersimulator.layout.Layout;
import nth.meyn.containersimulator.unit.cas.Cas;
import nth.meyn.containersimulator.unit.turntable.TurnTable;

public class StackPositions implements Iterable<StackPosition> {

	private List<StackPosition> stackPositions;
	private Layout layout;

	public StackPositions(Layout layout) {
		this.layout = layout;
		stackPositions = new ArrayList<>();
	}

	public void add(StackPosition newStackPosition) {
		String newName = newStackPosition.getName();

		try {
			get(newName);
			throw new RuntimeException("There already is an unit named: " + newName);
		} catch (Exception exception) {
		}

		stackPositions.add(newStackPosition);
		if (newStackPosition instanceof PresentationOwner) {
			PresentationOwner presentationOwner = (PresentationOwner) newStackPosition;
			layout.addPresentation(presentationOwner.getGuiPresentation());
		}
	}

	public StackPosition get(String nameToFind) {
		for (StackPosition stackPosition : stackPositions) {
			if (stackPosition.getName().equals(nameToFind)) {
				return stackPosition;
			}
		}
		throw new RuntimeException("Could not find a unit named: " + nameToFind);
	}

	public StackSource getStackSource(String sourceName) {
		StackPosition stackPosition = get(sourceName);
		if (stackPosition instanceof TurnTable) {
			TurnTable turnTable = (TurnTable) stackPosition;
			return turnTable.createStackSource();
		} else if (stackPosition instanceof StackSource) {
			return (StackSource) stackPosition;
		} else {
			throw new RuntimeException(
					"Unit: " + sourceName + " is not a " + StackSource.class.getSimpleName());
		}
	}

	public StackDestination getStackDestination(String destinationName) {
		StackPosition stackPosition = get(destinationName);
		if (stackPosition instanceof TurnTable) {
			TurnTable turnTable = (TurnTable) stackPosition;
			return turnTable.createStackDestination();
		} else if (stackPosition instanceof StackDestination) {
			return (StackDestination) stackPosition;
		} else {
			throw new RuntimeException(
					"Unit: " + destinationName + " is not a " + StackSource.class.getSimpleName());
		}
	}

	@Override
	public Iterator<StackPosition> iterator() {
		return stackPositions.iterator();
	}

	public List<Cas> getCasUnits() {
		List<Cas>  casUnits = new ArrayList<>();
		for (StackPosition stackPosition : stackPositions) {
			if (stackPosition instanceof Cas) {
				Cas cas = (Cas) stackPosition;
				casUnits.add(cas);
			}
		}
		return casUnits;
	}

	public List<Updatable> getUpdatables() {
		List<Updatable> updatables=new ArrayList<>();
		for (StackPosition stackPosition : stackPositions) {
			if (stackPosition instanceof Updatable) {
				Updatable updatable = (Updatable) stackPosition;
				updatables.add(updatable);
			}
		}
		return updatables;
	}
}
