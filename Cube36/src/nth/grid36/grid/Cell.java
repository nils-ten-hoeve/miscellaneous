package nth.grid36.grid;

import java.util.ArrayList;
import java.util.List;

public class Cell {

	public static final Tower EMPTY_VALUE = new Tower(null,0);
	private final int row;
	private final int column;
	private Tower value;
	private List<Tower> candidates;
	private List<Tower> possibleTowers;

	// public Cell(String text) {
	//
	// }

	public Cell( int column, int row, List<Tower> possibleTowers) {
		this.possibleTowers=possibleTowers;
		this.candidates = new ArrayList<Tower>(possibleTowers);
		this.row = row;
		this.column = column;
		this.value = EMPTY_VALUE;
	}

	

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}


	public void setValue(Tower value) {
		if (value!=EMPTY_VALUE && !possibleTowers.contains(value) ) {
			throw new IllegalArgumentException("The value must be one of the following values:" + possibleTowers);
		}
		this.value = value;
	}

	public Tower getValue() {
		return value;
	}

	public List<Tower> getCandidates() {
		return candidates;
	}



	public List<Tower> getPosibleValues() {
		return possibleTowers;
	}



	public boolean hasNoValue() {
		return value==EMPTY_VALUE;
	}



	public void reset() {
		setValue(EMPTY_VALUE);
		candidates.clear();
		candidates.addAll(possibleTowers);
		
	}

}
