package nth.sudoku.ui.commands;

import nth.sudoku.grid.Grid;
import nth.sudoku.ui.Sudoku;

public class GenerateCommand implements Command {

	private static final String GENERATE = "generate";

	public void execute(String command) {
		String numbers=command.trim().toLowerCase().replace(GENERATE, "").trim();
		int size = Integer.parseInt(numbers.substring(0,1));
		int dificulty = Integer.parseInt(numbers.substring(1));
		Grid grid=new Grid(size);//TODO Grid grid=GridGenerator.generate(size,dificulty);
		Sudoku.setGrid(grid);
	}

	public String getHelp() {
		return "generate{size}{dif}: creates a new sudoku that needs to be completed. {size}= 3,4 or 5, {dif} 1=easy 10=difficult. In example: generate32";
	}

	public boolean isValidCommand(String command) {
		return command.startsWith(GENERATE);
	}
}
