package nth.sudoku.ui.commands;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;
import nth.sudoku.solver.Solver;
import nth.sudoku.ui.Sudoku;

public class NextStepCommand implements Command {

	public void execute(String command) {
		Grid grid = Sudoku.getGrid();
		NextStep nextStep = Solver.findNextStep(grid);
		System.out.println(nextStep);
	}

	public String getHelp() {
		return "next               : Get next step";
	}

	public boolean isValidCommand(String command) {
		return command.equals("next");
	}

}
