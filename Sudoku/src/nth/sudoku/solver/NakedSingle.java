package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class NakedSingle extends Rule {

	@Override
	public String getDescription() {
		StringBuffer description=new StringBuffer();
		description.append("Naked singles: A cell value is found when a cell has only one possible candidate/n");
		description.append("In example: 5 3 (689) (679) (9) (79) (4689) 1 2  numbers between brackets are candidates/n ");
		description.append("The 5th cell can only contain a 9");
		return description.toString();
	}

	@Override
	public NextStep findNextStep(Grid grid) {
		ArrayList<NextStep> solutions = new ArrayList<NextStep>();
		// iterate trough all groups
		List<List<Cell>> cellGroups = grid.getAllCellGroups();
		for (List<Cell> cellGroup : cellGroups) {
			for (Cell cell : cellGroup) {
				List<Character> candidates = cell.getCandidates();
				if (candidates.size() == 1) {
					cell.setValue(candidates.get(0));//set the cell value
					return new NextStep(cell, candidates.get(0), this, cellGroup);
				}
			}
		}
		return null; //no solution found
	}

}
