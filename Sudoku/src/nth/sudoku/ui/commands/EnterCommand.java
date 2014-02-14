package nth.sudoku.ui.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.Cell;
import nth.sudoku.ui.Sudoku;

public class EnterCommand implements Command {

	private static final String ENTER = "enter";

	public void execute(String command) {
		int size = Integer.parseInt(command.trim().toLowerCase().replace(ENTER, "").trim());
		Grid grid=new Grid(size);
		int length = grid.getLength();
		Sudoku.setGrid(grid);
		System.out.println("Enter the following cell values: "+grid.getValidValues());
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader keyBoardReader=new BufferedReader(isr);
		
		//enter values into grid
		for (int row=0;row<length;row++) {
			System.out.print("Line"+(row+1)+">");
			try {
				String values = keyBoardReader.readLine().replace(" ", "");
				for (int column=0;column<length&&column<values.length();column++) {
					Cell cell = grid.getCell(column, row);
					cell.setValue(values.charAt(column));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getHelp() {
		return "enter{size}        : creates a new sudoku by entering cell values. {size} =3,4 or 5. In example: new3";
	}

	public boolean isValidCommand(String command) {
		return command.startsWith(ENTER);
	}
}
