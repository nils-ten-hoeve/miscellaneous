package nth.grid36.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Grid {

	private HashMap<Integer, Cell> cells = new HashMap<Integer, Cell>();
	private final int LENGTH=6;
	private final int AREA = LENGTH*LENGTH;

	/**
	 * creates a empty grid
	 */
	public Grid() {
		List<String> heights = new ArrayList<String>();
		heights.add("125463");
		heights.add("536142");
		heights.add("463%21");//% fits 5 high towers of all colors or a 6 high orange tower 
		heights.add("214356");
		heights.add("352^14");//^ fits 6 high towers of all colors or a 5 high yellow tower
		heights.add("641235");
		
		
		for (int row = 0; row < LENGTH; row++) {
			for (int column = 0; column < LENGTH; column++) {
				int key = getCellKey(column, row);
				String ch = heights.get(row).substring(column,column+1);
				int height;
				List<Tower> possibleTowers=new ArrayList<Tower>();
				if (ch.equals("%")) {
					//% fits 5 high towers of all colors or a 6 high orange tower
					height=5;
					possibleTowers.add(new Tower(Color.ORANGE,6));
				} else if (ch.equals("^")) {
					//^ fits 6 high towers of all colors or a 5 high yellow tower
					height=6;
					possibleTowers.add(new Tower(Color.YELLOW,5));
				} else {
					height=Integer.parseInt(ch);
				}
				for (Color color:Color.values()) {
					possibleTowers.add(new Tower(color,height));
				}
				Cell cell = new Cell( column, row,possibleTowers);
				cells.put(key, cell);
			}
		}
	}

	private int getCellKey(int column, int row) {
		return row * LENGTH + column;
	}


	public int getLength() {
		return LENGTH;
	}

	public boolean isFull() {
		for (Cell cell:getCells()){
			if (cell.getValue() == Cell.EMPTY_VALUE) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer report = new StringBuffer();
		for (int row = 0; row < LENGTH; row++) {
			for (int column = 0; column < LENGTH; column++) {
				Cell cell = getCell(column, row);
				report.append(cell.getValue());
				report.append(" ");

			}
			report.append("  ");
			report.append(row + 1);
			report.append("\n");
		}
		for (int x = 0; x < LENGTH; x++) {
			report.append(new Character((char) ('A' + x)).toString());
			report.append("  ");
		}
		return report.toString();
	}

	public Cell getCell(int column, int row) {
		int key = getCellKey(column, row);
		return getCell(key);

	}

	public Cell getCell(int index) {
		return cells.get(index);
	}

	public List<Tower> getAllTowersInGrid() {
		List<Tower> towers=new ArrayList<Tower>();
		for (Cell cell:cells.values()) {
			if (!cell.hasNoValue()) {
				towers.add(cell.getValue());
			}
		}
		return towers;
	}
	
	public Collection<Cell> getCells() {
		return cells.values();
	}
	
	public List<Cell> getCellsInRow(int row) {
		ArrayList<Cell> cellsInRow= new ArrayList<Cell>();
		for (int column = 0; column < LENGTH; column++) {
			cellsInRow.add(getCell(column, row));
		}
		return cellsInRow;
	}
	
	public List<Cell> getCellsInColumn(int column) {
		ArrayList<Cell> cellsInColumn= new ArrayList<Cell>();
		for (int row = 0; row < LENGTH; row++) {
			cellsInColumn.add(getCell(column, row));
		}
		return cellsInColumn;
	}
	
	
	
	/**
	 * @return all cell groups of cells. A group is a collection of cells that represent a row, column or region 
	 */
	public List<List<Cell>> getAllCellGroups() {
		List<List<Cell>> groups = new ArrayList<List<Cell>>();
		for (int row=0;row<LENGTH;row++) {
			groups.add(getCellsInRow(row));
		}
		for (int column=0;column<LENGTH;column++) {
			groups.add(getCellsInColumn(column));
		}
		return groups;
	}

	public int getArea() {
		return AREA;
	}

}
