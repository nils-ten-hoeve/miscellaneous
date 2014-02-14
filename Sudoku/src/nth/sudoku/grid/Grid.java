package nth.sudoku.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Grid {

	private final int size;
	private ArrayList<Character> validCharacters;
	private HashMap<Integer, Cell> cells = new HashMap<Integer, Cell>();
	private final int length;
	private int area;

	/**
	 * creates a empty sudoku
	 */
	public Grid(int size) {
		if (size < 2 || size > 5) {
			throw new IllegalArgumentException("Size must be between 3 (3*3) and 5 (5*5)");
		}
		this.size = size;
		this.length = size * size;
		this.area = size * size * size * size;
		for (int row = 0; row < length; row++) {
			for (int column = 0; column < length; column++) {
				int key = getCellKey(column, row);
				Cell cell = new Cell(getValidValues(), column, row);
				cells.put(key, cell);
			}
		}
	}

	private int getCellKey(int column, int row) {
		return row * length + column;
	}

	// /**
	// * creates a uncompleted sudoku
	// *
	// * @param dificulty
	// * 1=easy 10=difficult
	// */
	// public Grid(int size, int dificulty) {
	// // TODO
	// this(size);
	// if (dificulty < 1 || dificulty > 10) {
	// throw new IllegalArgumentException("Dificulty must be between 1 (easy) and 10 (difficult)");
	// }
	//
	// populateCell(0);
	//
	// int nrOfEmptyCells = 0;
	// int minimumOfEmptyCells=getArea()*(10-dificulty)/10;
	// // TODO improve the following:
	//
	// // generate a list of random locations
	// List<Cell> locations = new ArrayList<Cell>();
	// for (int x = 0; x < getWidth(); x++) {
	// for (int y = 0; y < getWidth(); y++) {
	// locations.add(new Cell(x, y));
	// }
	// }
	// List<Cell> randomLocations = new ArrayList<Cell>();// TODO random list?
	// for (int v = 0; v < getWidth(); v++) {
	// int i = new Random().nextInt(locations.size());
	// randomLocations.add(locations.get(i));
	// locations.remove(i);
	// }
	// // remove cell as long as long as it is solvalble
	// for (Cell location : randomLocations) {
	// Character value = getValue(location);
	// setValue(location, EMPTY_VALUE);
	// nrOfEmptyCells++;
	// if (isSolvable()) {
	// // if (nrOfEmptyCells >= minimumOfEmptyCells) {
	// // break;
	// // }
	// } else {
	// setValue(location, value);// put value back
	// nrOfEmptyCells--;
	// }
	// }
	// }

	// /**
	// * recusrsive function to populate the grid. When the grid isn't valid a different random number is tried in that cell. When all values have been tried in that cell the recursion backs up one, and other values are tried in the previous cell. The algorithm carries on going forwards and backwards like that until it reaches the 81st cell, at which point the grid is complete.
	// *
	// * @param randomValues
	// *
	// * @return true is the board is valid or complete
	// */
	// private boolean populateCell(int index) {
	// if (index == getArea()) {
	// return true; // the board is full!!
	// }
	// int width = getWidth();
	// // get squance of random values for this cell
	// List<Character> validValues = new ArrayList<Character>(getValidCharacters());
	// ArrayList<Character> randomValues = new ArrayList<Character>();
	// for (int v = 0; v < getWidth(); v++) {
	// int i = new Random().nextInt(validValues.size() - 1);
	// randomValues.add(validValues.get(i));
	// validValues.remove(i);
	// }
	// // try setting each possible value in cell
	// for (int v = 0; v < width; v++) {
	// // set this test value
	// Cell location = new Cell(index % width, index / width);
	// setValue(location, EMPTY_VALUE);// requred for validation
	// Character newValue = randomValues.get(v);
	//
	// // is this board still valid?
	// if (isValidNewValue(location, newValue)) {
	// values.put(index, newValue);
	// // it is so try to populate next cell
	// if (populateCell(index + 1)) {
	// // if this cell returned true, then the board is valid
	// return true;
	// }
	// }
	// }
	// // rollback this cell
	// return false;
	// }

	public int getSize() {
		return size;
	}

	public int getLength() {
		return length;
	}

	public List<Character> getValidValues() {
		if (validCharacters == null) {
			validCharacters = new ArrayList<Character>();
			char startChar;
			if (size == 3) {
				startChar = '1';
			} else {
				startChar = 'A';
			}
			for (int i = 0; i < getLength(); i++) {
				validCharacters.add(new Character((char) (startChar + i)));
			}
			validCharacters.add(Cell.EMPTY_VALUE);
		}
		return validCharacters;
	}

	public boolean isFull() {
		for (Cell cell:getCells()){
			if (cell.getValue() == Cell.EMPTY_VALUE) {
				return false;
			}
		}
		return true;
	}

	public boolean isFinished() {
		return isFull() && isValid();
	}

	private boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Cell> getInvalidValues() {
		// TODO!
		List<Cell> invalidLocations = new ArrayList<Cell>();
		// for (int x = 0; x < getLength(); x++) {
		// for (int y = 0; y < getLength(); y++) {
		// Cell location = new Cell(x, y);
		// if (!isValidValue(location)) {
		// invalidLocations.add(location);
		// }
		// }
		// }
		return invalidLocations;
	}

	// private boolean isValidValue(Cell location) {
	// Character value = getValue(location);
	// return isEmpty(location) || (isUniqueInHorizontalRow(location, value) && isUniqueInVerticalRow(location, value) && isUniqueInSquare(location, value));
	// }

	// public boolean isValidNewValue(Cell location, Character value) {
	// return isEmpty(location) && isUniqueInHorizontalRow(location, value) && isUniqueInVerticalRow(location, value) && isUniqueInSquare(location, value);
	// }

	// private boolean isUniqueInSquare(Cell location1, Character value) {
	// int xOffset = (location1.getX() / size) * size;
	// int yOffset = (location1.getY() / size) * size;
	// for (int p = 0; p < getWidth(); p++) {
	// int x2 = xOffset + p % size;
	// int y2 = yOffset + p / size;
	// Cell location2 = new Cell(x2, y2);
	// if (getValue(location2).equals(value) && !location1.equals(location2)) {
	// return false;
	// }
	// }
	// return true;
	// }
	//
	// private boolean isUniqueInHorizontalRow(Cell location1, Character value) {
	// int x = location1.getX();
	// for (int y = 0; y < getWidth(); y++) {
	// Cell location2 = new Cell(x, y);
	// if (getValue(location2).equals(value) && !location1.equals(location2)) {
	// return false;
	// }
	// }
	// return true;
	// }
	//
	// private boolean isUniqueInVerticalRow(Cell location1, Character value) {
	// int y = location1.getY();
	// for (int x = 0; x < getWidth(); x++) {
	// Cell location2 = new Cell(x, y);
	// if (getValue(location2).equals(value) && !location1.equals(location2)) {
	// return false;
	// }
	// }
	// return true;
	// }

	// private boolean isEmpty(Cell location) {
	// return getValue(location).equals(EMPTY_VALUE);
	// }

	@Override
	public String toString() {
		StringBuffer report = new StringBuffer();
		for (int row = 0; row < getLength(); row++) {
			if (row % size == 0) {
				report.append("\n");
			}
			for (int column = 0; column < getLength(); column++) {
				if (column % size == 0) {
					report.append("  ");
				}
				Character value = getCell(column, row).getValue();
				report.append(value);
				report.append(" ");

			}
			report.append("  ");
			report.append(row + 1);
			report.append("\n");
		}
		report.append("\n");
		for (int x = 0; x < getLength(); x++) {
			if (x % size == 0) {
				report.append("  ");
			}
			report.append(new Character((char) ('A' + x)).toString());
			report.append(" ");
		}
		return report.toString();
	}

	// public Solution getNextSolution() {
	// List<Character> validCharacters = getValidCharacters();
	// for (int v = 0; v < (validCharacters.size() - 1); v++) {
	// Character value = validCharacters.get(v);
	// for (int index = 0; index < getWidth(); index++) {
	// for (Rule locationFinder : LOCATION_FINDERS) {
	// Cell foundLocation = locationFinder.findLocation(this, index, value);
	// if (foundLocation != Cell.NOT_FOUND) {
	// setValue(foundLocation, value);
	// return new Solution(foundLocation, value, locationFinder);
	// }
	// }
	// }
	// }
	// throw new RuntimeException("Could not find a new solution!");
	// }
	//
	// public List<Solution> getAllSolutions() {
	// boolean finished = false;
	// List<Solution> solutions = new ArrayList<Solution>();
	// while (!finished) {
	// try {
	// Solution solution = getNextSolution();
	// solutions.add(solution);
	// setValue(solution.getLocation(), solution.getValue());
	// } catch (Exception e) {
	// finished = true;
	// }
	// }
	// return solutions;
	// }
	//
	// public boolean isValid() {
	// return getInvalidValues().size() == 0;
	// }
	//
	// public boolean isSolvable() {
	// Grid clone = new Grid(size, values);
	// clone.getAllSolutions();
	// return (clone.isFinished());
	// }

	public Cell getCell(int column, int row) {
		int key = getCellKey(column, row);
		return cells.get(key);

	}

	public Collection<Cell> getCells() {
		return cells.values();
	}

	public Cell getCell(String cooridinate) {
		cooridinate=cooridinate.toUpperCase();
		Collection<Cell> cells = getCells();
		for (Cell cell : cells) {
			if (cell.getCoordinate().equals(cooridinate)) {
				return cell;
			}
		}
		return null;//cell not found
	}

	
	public List<Cell> getCellsInRow(int row) {
		ArrayList<Cell> cellsInRow= new ArrayList<Cell>();
		for (int column = 0; column < length; column++) {
			cellsInRow.add(getCell(column, row));
		}
		return cellsInRow;
	}
	
	public List<Cell> getCellsInColumn(int column) {
		ArrayList<Cell> cellsInColumn= new ArrayList<Cell>();
		for (int row = 0; row < length; row++) {
			cellsInColumn.add(getCell(column, row));
		}
		return cellsInColumn;
	}
	
	/**
	 * @return a collection of cells from a region. A 9x9 sudoku has 9x none overlapping 3x3 regions
	 */
	public List<Cell> getCellsInRegion(int region) {
		ArrayList<Cell> cellsInSquare= new ArrayList<Cell>();
		int startRow = (region % size) * size;
		int endRow = startRow + size;
		int startColumn = (region / size) * size;
		int endColumn = startColumn + size;
		for (int row = startRow; row < endRow; row++) {
			for (int column = startColumn; column < endColumn; column++) {
				cellsInSquare.add(getCell(column,row));
			}
		}
		return cellsInSquare;
	}
	
	/**
	 * @return all cell groups of cells. A group is a collection of cells that represent a row, column or region 
	 */
	public List<List<Cell>> getAllCellGroups() {
		List<List<Cell>> groups = new ArrayList<List<Cell>>();
		for (int row=0;row<length;row++) {
			groups.add(getCellsInRow(row));
		}
		for (int column=0;column<length;column++) {
			groups.add(getCellsInColumn(column));
		}
		for (int region=0;region<length;region++) {
			groups.add(getCellsInRegion(region));
		}
		return groups;
	}

}
