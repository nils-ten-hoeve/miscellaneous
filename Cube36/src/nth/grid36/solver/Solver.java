package nth.grid36.solver;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import nth.grid36.grid.Cell;
import nth.grid36.grid.Grid;
import nth.grid36.grid.Tower;

public class Solver {
	public void solve(Grid grid, JTextArea textArea) {
		int solutionCount = 0;
		int cellIndex = 0;
		StringBuffer report = new StringBuffer();

		do {
			do {
				Cell cell = grid.getCell(cellIndex);
				// update candidates
				List<Tower> candidates = cell.getCandidates();
				List<Tower> valuesInOtherCells = grid.getAllTowersInGrid();
				cell.getCandidates().removeAll(valuesInOtherCells);

				// remove all candidates with a color that is already used in same row or column
				int row = cellIndex / grid.getLength();
				int col = cellIndex % grid.getLength();

				List<Cell> otherCels = grid.getCellsInRow(row);
				otherCels.addAll(grid.getCellsInColumn(col));
				for (Cell otherCell : otherCels) {
					if (!otherCell.hasNoValue()) {
						ArrayList<Tower> falseCandidates = new ArrayList<Tower>();
						for (Tower candidate : cell.getCandidates()) {
							if (candidate.getColor() == otherCell.getValue().getColor()) {
								falseCandidates.add(candidate);
							}
						}
						cell.getCandidates().removeAll(falseCandidates);
					}
				}

				if (candidates.size() > 0) {
					// try next value
					Tower firstCandidate = cell.getCandidates().get(0);
					cell.setValue(firstCandidate);
					// try next cell
					cellIndex++;
				} else {
					// no more candidates so we need to role back
					cell.reset();

					if (cellIndex < 1) {
						textArea.setText("Done: \n\n" + report);
						return; // done
					}
					cellIndex--;
					// previous cell had a false value so remove it from its candidates
					Cell previousCell = grid.getCell(cellIndex);
					Tower invalidPreviousCellValue = previousCell.getValue();
					previousCell.getCandidates().remove(invalidPreviousCellValue);
					// try previous cell

				}
				textArea.setText(grid.toString() + report);

			} while (cellIndex < (grid.getArea()));
			// add solution to the report
			report.append("\n\nSolution ");
			report.append(++solutionCount);
			report.append("\n==========\n");
			report.append(grid);

			// find next solution
			cellIndex--;
			Cell previousCell = grid.getCell(cellIndex);
			Tower invalidPreviousCellValue = previousCell.getValue();
			previousCell.getCandidates().remove(invalidPreviousCellValue);
			// try previous cell
		} while (true);

	}
}
