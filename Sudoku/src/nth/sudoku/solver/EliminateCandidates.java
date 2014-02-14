package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class EliminateCandidates extends Rule {

	@Override
	public String getDescription() {
		StringBuffer description=new StringBuffer();
		description.append("Every cell within a group (row, column or region) must contain a unique value./n");
		description.append("If a cell contains a value, it can not go in an other cell/n");
		description.append("We can than safely remove if from the candidates in the other cells/n");
		return description.toString();
	}
	
	@Override
	public NextStep findNextStep(Grid grid) {
		//iterate trough all groups
		List<List<Cell>> cellGroups = grid.getAllCellGroups();
		for (List<Cell> cellGroup : cellGroups) {
			//get all values in a group of cells
			ArrayList<Character> valuesInGroup = new ArrayList<Character>();
			for (Cell cell : cellGroup) {
				if (cell.getValue() != Cell.EMPTY_VALUE) {
					valuesInGroup.add(cell.getValue());
				}
			}
			// remove the values from the candidates
			for (Cell cell : cellGroup) {
				if (cell.getValue() == Cell.EMPTY_VALUE) {
					cell.getCandidates().removeAll(valuesInGroup);
				}
			}
		}
		return null;//This rule only eliminates candidates. Results are so obvious that we do not return them as a solution
	}

}
