package nth.sudoku.ui.commands;

import java.util.Collection;
import java.util.List;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.Cell;
import nth.sudoku.ui.Sudoku;

public class SetCommand implements Command {

	public void execute(String command) {
		int equalsPosition = command.indexOf("=");
		String coordinate = command.substring(0, equalsPosition);
		char value = command.substring(equalsPosition + 1).charAt(0);
		Grid grid= Sudoku.getGrid();
		Cell cell = grid.getCell(coordinate);
		cell.setValue(value);//already checked on cell==null
	}

	public String getHelp() {
		return "{location}={value} : Sets a value at a location i.e. a1=. or d4=1 or k11=g";
	}

	public boolean isValidCommand(String command) {
		Grid grid = Sudoku.getGrid();
		int equalsPosition = command.indexOf("=");
		if (equalsPosition == -1) {
			return false;
		}
		List<Character> validValues = grid.getValidValues();
		String coordinate = command.substring(0, equalsPosition);
		char value = command.substring(equalsPosition + 1).charAt(0);
		Cell cell = grid.getCell(coordinate);
		if (cell == null) {
			return false;// could not find coordinate
		}
		return validValues.contains(value);// invalid value?
	}
}
