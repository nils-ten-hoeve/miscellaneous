package nth.sudoku.ui.commands;

import java.util.List;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.Cell;
import nth.sudoku.ui.Sudoku;

public class CheckCommand implements Command {

	public void execute(String command) {
		Grid grid = Sudoku.getGrid();
		List<Cell> invalidLocations = grid.getInvalidValues();
		if (invalidLocations.size() == 0) {
			if(grid.isFinished()) {
				System.out.println("Finished!");
			} else {
				System.out.println("Not completed yet");
			}
		} else {
			System.out.println("Invalid locations: ");
			for (Cell location : invalidLocations) {
				System.out.print(location);
				System.out.print(" ");
			}
		}
	}

	public String getHelp() {
		return "check              : Checks the Sudoku completed and valid";
	}

	public boolean isValidCommand(String command) {
		return command.equals("check");
	}

}
