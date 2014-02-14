package nth.sudoku.solver;

import java.util.ArrayList;
import java.util.List;

import nth.sudoku.grid.Grid;
import nth.sudoku.grid.NextStep;

public class Solver {
	//private static final Rule[] RULES = {new EliminateCandidates(), new NakedSingle(), new HiddenSingle(), new NakedPair(), new HiddenPair()};
	private static final Rule[] RULES = {new EliminateCandidates(), new NakedSingle(), new HiddenSingle(), new NakedPair(), new HiddenPair()};

	// TODO Add NakedPair, HiddenPair .. NakedQuads and HiddenQuads, XWing, YWing and Swordfisch. See Backgound\How to solve Su Doku.doc

	public static NextStep findNextStep(Grid grid) {
		for (Rule rule : RULES) {
			NextStep solution = rule.findNextStep(grid);
			if (solution != null) {
				System.out.println(solution);
				return solution;
			}
		}
		return null; // Could not find a new solution
	}

	public static List<NextStep> findAllSteps(Grid grid) {
		List<NextStep> solutions = new ArrayList<NextStep>();
		NextStep solution = null;
		do {
			solution = findNextStep(grid);
			if (solution != null) {
				solutions.add(solution);
			}
		} while (solution != null);
		// TODO if (!grid.isFinished) throw new RuntimeException("Could not find a new solution");// TODO
		return solutions;
	}

	public static void elimetateCandidates(Grid grid) {
		EliminateCandidates basicRules = new EliminateCandidates();
		basicRules.findNextStep(grid);// we do nothing with the results. We just want to eliminate the obvious candidates
	}

}
