package nth.sudoku.ui.commands;

public interface Command {
	public String getHelp();

	public void execute(String command);

	public boolean isValidCommand(String command);
}
