package nth.sudoku.ui.commands;

import java.util.ArrayList;

import nth.sudoku.grid.Cell;
import nth.sudoku.grid.Grid;
import nth.sudoku.solver.HiddenPair;
import nth.sudoku.ui.Sudoku;

public class TestGridCommand implements Command {

	private static final String TESTGRID = "testgrid";

	public void execute(String command) {


		ArrayList<String> lines = new ArrayList<String>();
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		// lines.add(".........");
		
		// //naked and hidden singles
		// lines.add("7..1....2");
		// lines.add(".....6.8.");
		// lines.add("...8..1.9");
		// lines.add("..7..9.1.");
		// lines.add(".93...54.");
		// lines.add(".6.4..9..");
		// lines.add("3.8..4...");
		// lines.add(".4.3.....");
		// lines.add("1....5..3");

		// // naked couple
		// lines.add("6 5 7   4 1 3   8 9 2");
		// lines.add("1 9 4   6 2 8   5 7 3");
		// lines.add("3 2 8   5 7 9   4 6 1");
		// lines.add(". 8 9   2 3 6   1 . .");
		// lines.add(". 6 2   8 5 1   9 3 .");
		// lines.add("5 3 1   7 9 4   6 2 8");
		// lines.add(". 1 6   3 4 .   7 . .");
		// lines.add(". . 3   . 8 .   2 . 6"); // A8=9 omdat A4 en A5 een 4 of 7 is, blijft over 2,8,9 de 2 en 8 kunnen niet in deze rij, 9 blijft over
		// lines.add(". . 5   . 6 .   3 . .");
	
		
		 //Hidden Pairs
		 lines.add("*********");
		 lines.add("*********");
		 lines.add("*********");
		 lines.add("***8(17)(279)***");
		 lines.add("***(3457)(3457)(347)***");
		 lines.add("***(12359)(135)6***");
		 lines.add("*********");
		 lines.add("*********");
		 lines.add("*********");

		
		// YWing pattern
		// lines.add(".........");
		// lines.add("...1.7..8");
		// lines.add(".7.392541");
		// lines.add("..4....92");
		// lines.add("..5...6..");
		// lines.add("93....4..");
		// lines.add("192785.6.");
		// lines.add("5..4.3...");
		// lines.add(".........");

		Grid grid = new Grid(3);// TODO Grid grid=GridGenerator.generate(size,dificulty);

		int length = grid.getLength();
		// enter values into grid
		for (int row = 0; row < length; row++) {
			String line = lines.get(row).replace(" ", "");
			int column=0;
			boolean candidates=false;
			for (Character ch:line.toCharArray()) {
				if (ch=='(') {//start candidates
					candidates=true;
					grid.getCell(column,row).getCandidates().clear();
				} else if (ch==')') {//end candidates
					candidates=false;
					column++;
				} else if (ch=='*') {//clear candidates
					grid.getCell(column,row).getCandidates().clear();
					column++;
				} else  {// a number or dot
					if (candidates) {
						grid.getCell(column,row).getCandidates().add(ch);
					} else {
						grid.getCell(column,row).setValue(ch);
						column++;
					}
				}
			}
		}
		HiddenPair rule = new HiddenPair();
		System.out.print(rule.findNextStep(grid));
		
		Sudoku.setGrid(grid);
	}

	
	public String getHelp() {
		return "testgrid           : creates a sudoku for testing";
	}

	public boolean isValidCommand(String command) {
		return command.startsWith(TESTGRID);
	}
}
