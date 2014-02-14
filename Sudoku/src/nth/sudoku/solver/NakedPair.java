package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class NakedPair extends Rule {

	@Override
	public String getDescription() {
		StringBuffer description = new StringBuffer();
		description.append("Naked pair: Is two identical candidates in a group (row, column, or region)./n");
		description.append("This combination of candidates will eliminate those two numbers from all other cells in the row, column, or region the pair reside in./n");
		description.append("In example: (46) 7 (46) 9 2 5 (3468) 1 (3468)  numbers between brackets are candidates/n ");
		description.append("It is safe to eliminate the four and six from the two quads of 3,4,6, and 8. Doing so, leaves two 3,8 pairs.");
		return description.toString();
	}

	@Override
	public NextStep findNextStep(Grid grid) {
		// iterate trough all groups
		List<List<Cell>> cellGroups = grid.getAllCellGroups();
		for (List<Cell> cellGroup : cellGroups) {
			for (Cell cell : cellGroup) {
				List<Character> candidates = cell.getCandidates();
				if (candidates.size() == 2) {
					List<Cell> remainingCellsInGroup = new ArrayList<Cell>();
					List<Character> pair = candidates;
					for (Cell otherCell : cellGroup) {
						if (cell != otherCell && pair.equals(otherCell.getCandidates())) {
							remainingCellsInGroup.addAll(cellGroup);
							remainingCellsInGroup.remove(cell);
							remainingCellsInGroup.remove(otherCell);
							List<Cell> remainingCellsWithCandidates = findCellsThatContainCandidates(remainingCellsInGroup, pair);
							if (remainingCellsWithCandidates.size()>0) {
								for (Cell cellWithCandidates:remainingCellsWithCandidates) {
									cellWithCandidates.getCandidates().removeAll(pair);
								}
								return new NextStep(remainingCellsWithCandidates, "Removing candidate values:" + pair, "Naked pair values:" + pair + " in cells:" + cell + " and " + otherCell);
							}
						}
					}
				}
			}
		}
		return null;
	}

	private List<Cell> findCellsThatContainCandidates(List<Cell> cellGroup, List<Character> candidates) {
		List<Cell> foundCells = new ArrayList<Cell>();
		for (Cell cell : cellGroup) {
			boolean found = false;
			for (Character candidate : candidates) {
				if (cell.getCandidates().contains(candidate)) {
					found = true;
				}
			}
			if (found) {
				foundCells.add(cell);
			}
		}
		return foundCells;
	}
}
