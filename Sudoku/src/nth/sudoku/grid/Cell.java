package nth.sudoku.grid;

import java.util.ArrayList;
import java.util.List;

public class Cell {


	public static final Character EMPTY_VALUE ='.';
	private final int row;
	private final int column;
	private Character value;
	private final List<Character> validValues;
	private List<Character> candidates;
	
//	public Cell(String text) {
//		
//	}

	public Cell(List<Character> validValues, int column, int row) {
		this.validValues = validValues;
		this.candidates = new ArrayList<Character>(validValues);
		this.candidates.remove(EMPTY_VALUE);
		this.row = row;
		this.column = column;
		this.value=EMPTY_VALUE;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public String getCoordinate() {
		return new Character((char) ('A' + column)).toString() + (row + 1);
	}
	
	

	@Override
	public String toString() {
		return getCoordinate();
	}

	public void setValue(char value) {
		if (!validValues.contains(value)) {
			throw new IllegalArgumentException("The value must be one of the following values:" + validValues);
		}
		this.value = value;
	}

	public Character getValue() {
		return value;
	}

	public List<Character> getCandidates() {
		if (value!=EMPTY_VALUE) {
			candidates.clear();//the cell has a value so we no longer have candidates
		}
		return candidates;
	}
}
