package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class HiddenPair extends Rule {

	@Override
	public String getDescription() {
		StringBuffer description = new StringBuffer();
		description.append("Hidden pair: Is two identical candidates in a group (row, column, or region)./n");
		description.append("They are 'hidden' because the other candidates in the two cells make their presence harder to spot./n");
		description.append("This combination of candidates will eliminate other candidates in these cells./n");
		description.append("In example: 8 (17) (279) (3457) (3457) (347) (12359) (135) 6  numbers between brackets are candidates/n ");
		description.append("In the example there is a hidden pair 2 and 9. It allows us to elimenate the other candidates in column 3 and column 7");
		return description.toString();
	}

	@Override
	public NextStep findNextStep(Grid grid) {

		int length = grid.getLength();
		// iterate trough all groups
		List<List<Cell>> cellGroups = grid.getAllCellGroups();
		for (List<Cell> cellGroup : cellGroups) {
			for (int i = 0; i < length - 1; i++) {
				Cell cell1 = cellGroup.get(i);
				List<List<Character>> pairs = getPairs(cell1);
				for (List<Character> pair : pairs) {
					List<Cell> remainingCells = new ArrayList<Cell>();
					for (int j = i + 1; j < length; j++) {
						remainingCells.add(cellGroup.get(j));
					}
					List<Cell> cellsWithPair = findCellsWithPair(remainingCells, pair);
					if (cellsWithPair.size() == 1) {
						Cell cell2 = cellsWithPair.get(0);
						// do we have other candidates to remove?
						if (cell1.getCandidates().size() > 2 || cell2.getCandidates().size() > 2) {
							// remove candidates
							cell1.getCandidates().clear();
							cell1.getCandidates().addAll(pair);
							cell2.getCandidates().clear();
							cell2.getCandidates().addAll(pair);
							List<Cell> cells = new ArrayList<Cell>();
							cells.add(cell1);
							cells.add(cell2);
							return new NextStep(cells, "Removing candidates other than:" + pair, "Hidden pair in cells:" + cells);
						}
					}
				}
			}
		}
		return null;
	}

	private List<Cell> findCellsWithPair(List<Cell> cellGroup, List<Character> pair) {
		List<Cell> foundCells = new ArrayList<Cell>();
		for (Cell cell : cellGroup) {
			if (cell.getCandidates().containsAll(pair)) {
				foundCells.add(cell);
			}
		}
		return foundCells;
	}

	private List<List<Character>> getPairs(Cell cell) {
		List<List<Character>> pairs = new ArrayList<List<Character>>();
		List<Character> candidates = cell.getCandidates();
		for (int i = 0; i < candidates.size() - 1; i++) {
			for (int j = i + 1; j < candidates.size(); j++) {
				List<Character> pair = new ArrayList<Character>();
				pair.add(candidates.get(i));
				pair.add(candidates.get(j));
				pairs.add(pair);
			}
		}
		return pairs;
	}
}
