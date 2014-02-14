package nth.sudoku.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nth.sudoku.grid.Grid;
import nth.sudoku.ui.commands.CandidatesCommand;
import nth.sudoku.ui.commands.CheckCommand;
import nth.sudoku.ui.commands.Command;
import nth.sudoku.ui.commands.ExitCommand;
import nth.sudoku.ui.commands.EnterCommand;
import nth.sudoku.ui.commands.HelpCommand;
import nth.sudoku.ui.commands.GenerateCommand;
import nth.sudoku.ui.commands.NextStepCommand;
import nth.sudoku.ui.commands.SetCommand;
import nth.sudoku.ui.commands.SolveCommand;
import nth.sudoku.ui.commands.TestGridCommand;

public class Sudoku {

	public static Command[] COMMANDS = new Command[] {new HelpCommand(), new GenerateCommand(), new EnterCommand(), new TestGridCommand(),new SetCommand(), new CheckCommand(), new NextStepCommand(), new CandidatesCommand(), new SolveCommand(), new ExitCommand()};
	private static Grid grid;

	public static void main(String[] args) {
		// Grid grid = new Grid(3);
		// grid.setValue(new Location("A1"), '4');
		// grid.setValue(new Location("B3"), '3');
		// // grid.setValue(new Location("3"), '3');
		// // grid.setValue(new Location("z3"), '3');
		// // grid.setValue(new Location("d10"), '3');
		// // grid.setValue(new Location("d9"), 'A');
		// System.out.println(new Location("B3"));
		// System.out.println(grid.getValue(new Location("B3")));

		System.out.println("Sudoku");
		System.out.println("======");
		new HelpCommand().execute("");// print help on commands
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader keyBoardReader = new BufferedReader(isr);

		String commandText = "";
		while (true) {
			if (grid != null) {
				System.out.println(grid);
			}
			System.out.println();
			System.out.print(">");
			try {
				commandText = keyBoardReader.readLine().trim().toLowerCase();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Command command = getCommand(commandText);
			command.execute(commandText);
		}
	}

	private static Command getCommand(String commandText) {
		for (Command command : COMMANDS) {
			if (command.isValidCommand(commandText)) {
				return command;
			}
		}
		System.out.println("Invalid command: " + commandText);
		return new HelpCommand();
	}

	public static void setGrid(Grid grid) {
		Sudoku.grid = grid;
	}

	public static Grid getGrid() {
		return grid;
		
		
		
		
	}

}
