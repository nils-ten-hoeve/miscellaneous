package nth.sudoku.ui.commands;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.solver.Solver;
import nth.sudoku.ui.Sudoku;

public class CandidatesCommand implements Command {

	private static final String CANDIDATES = "candidates";

	
	public void execute(String command) {
		String cooridinate=command.substring(CANDIDATES.length());
		Grid grid=Sudoku.getGrid();
		Solver.elimetateCandidates(grid);
		Cell cell = grid.getCell(cooridinate);
		System.out.println(cell.getCandidates());
	}

	public String getHelp() {
		return "candidates{location}: shows posible values. In example: candidatesA1";
	}

	public boolean isValidCommand(String command) {
		if (! command.startsWith(CANDIDATES)) {
			return false;
		}
		String cooridinate=command.substring(CANDIDATES.length());
		Cell cell=Sudoku.getGrid().getCell(cooridinate);
		return cell!=null;//found coordinate?
	}
}
