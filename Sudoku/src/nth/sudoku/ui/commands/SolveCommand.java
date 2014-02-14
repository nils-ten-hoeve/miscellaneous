package nth.sudoku.ui.commands;

import java.util.List;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;
import nth.sudoku.solver.Solver;
import nth.sudoku.ui.Sudoku;

public class SolveCommand implements Command {

	public void execute(String command) {
		Grid grid = Sudoku.getGrid();
		List<NextStep> nextSteps = Solver.findAllSteps(grid);
		for (NextStep nextStep : nextSteps) {
			System.out.println(nextStep);
		}
	}

	public String getHelp() {
		return "solve              : Solve sudoku";
	}

	public boolean isValidCommand(String command) {
		return command.equals("solve");
	}

}
