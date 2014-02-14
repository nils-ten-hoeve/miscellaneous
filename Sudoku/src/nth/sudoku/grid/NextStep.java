package nth.sudoku.grid;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.solver.NakedSingle;
import nth.sudoku.solver.Rule;
import nth.sudoku.solver.Rule;

public class NextStep {

	private final List<Cell> cells;
	private final String action;
	private final String reason;

	public NextStep(Cell cell, Character value, Rule rule, List<Cell> cellGroup) {
		this.cells = new ArrayList<Cell>();
		this.cells.add(cell);
		this.action=Character.toString(value);
		this.reason=camelCaseToNormal(rule.getClass().getSimpleName())+" in cells: "+cellGroup;
	}

	public NextStep(List<Cell> cells, String action, String reason) {
		this.cells = cells;
		this.action = action;
		this.reason = reason;
	}

	@Override
	public String toString() {
		StringBuffer reply = new StringBuffer();
		if (cells.size()==1) {
			reply.append("Cell=");
			reply.append(cells.get(0));
		} else {
			reply.append("Cells=");
			reply.append(cells);
		}
		reply.append(", Action=");
		reply.append(action);
		
		reply.append(", Reason=");
		reply.append(reason);
		return reply.toString();
	}

	public static String camelCaseToNormal(String camelCase) {
		StringBuffer reply = new StringBuffer();

		for (int i = 0; i < camelCase.length(); i++) {
			char ch = camelCase.charAt(i);
			if (i == 0) {
				reply.append(Character.toUpperCase(ch));
			} else {
				char previousChar = camelCase.charAt(i - 1);
				if (Character.isLowerCase(previousChar) && Character.isUpperCase(ch)) {
					reply.append(" ");
					boolean nextCharIsUpper = (i < camelCase.length()) ? false : Character.isUpperCase(camelCase.charAt(i + 1));
					if (nextCharIsUpper) {
						reply.append(ch);
					} else {
						reply.append(Character.toLowerCase(ch));
					}
				} else {
					reply.append(ch);
				}
			}
		}
		return reply.toString();
	}

}
