package nth.sudoku.ui.commands;

import nth.sudoku.ui.Sudoku;

public class HelpCommand implements Command {

	public void execute(String commandExpression) {
		System.out.println("Available commands:");
		for (Command command: Sudoku.COMMANDS) {
			System.out.println(command.getHelp());
		}
	}

	public String getHelp() {
		return "help               : Shows all possible commands";
	}

	public boolean isValidCommand(String command) {
		return command.equals("help");
	}

}
