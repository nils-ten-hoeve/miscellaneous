package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class HiddenSingle extends Rule {

	@Override
	public String getDescription() {
		StringBuffer description = new StringBuffer();
		description.append("Hidden singles: A cell value is found when a candidate has one place they can go./n ");
		description.append("Extra candidates in the cell hide the single solution/n ");
		description.append("In example: 1 8 (267) (26) 3 (26) (256) 9 (45)  numbers between brackets are candidates/n ");
		description.append("The 3rd cell can only contain a 7, the 9th can only contain a 4");
		return description.toString();
	}

	@Override
	public NextStep findNextStep(Grid grid) {
		List<Character> validValues = grid.getValidValues();
		// iterate trough all groups
		List<List<Cell>> cellGroups = grid.getAllCellGroups();
		for (List<Cell> cellGroup : cellGroups) {
			// check if there are unique candidates for each value
			for (Character value : validValues) {
				List<Cell> foundCells = findCellsWithCandidate(cellGroup, value);
				if (foundCells.size()==1) {
					Cell cell = foundCells.get(0);
					cell.setValue(value);//set cell value
					return new NextStep(foundCells.get(0), value, this, cellGroup);
				}
			}
		}
		return null; // no solution found
	}

	private List<Cell> findCellsWithCandidate(List<Cell> cellGroup, Character candidate) {
		List<Cell>foundCells=new ArrayList<Cell>();
		for (Cell cell:cellGroup) {
			if (cell.getCandidates().contains(candidate)) {
				foundCells.add(cell);
			}
		}
		return foundCells;
		
	}

}
