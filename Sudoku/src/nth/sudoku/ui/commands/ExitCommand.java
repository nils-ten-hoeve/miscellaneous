package nth.sudoku.ui.commands;


public class ExitCommand implements Command {

	public void execute(String command) {
		System.out.println("Sudoku ended.");
		System.exit(0);
	}

	public String getHelp() {
		return "exit               : Stops this program.";
	}

	public boolean isValidCommand(String command) {
		return command.equals("exit");
	}

}
