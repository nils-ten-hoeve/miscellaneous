package nth.sudoku.solver;

import java.util.List;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public abstract class Rule {

	
	abstract public String getDescription();
	
	abstract public NextStep findNextStep(Grid grid);

}
