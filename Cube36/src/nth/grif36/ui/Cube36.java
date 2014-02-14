package nth.grif36.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import nth.grid36.grid.Grid;
import nth.grid36.solver.Solver;

public class Cube36 {

	private static JTextArea textArea;

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Cube36");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		textArea.setFont(new Font("Courier", Font.PLAIN, 12));
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		
		// Add contents to the window.
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);

		// Display the window.
		frame.setPreferredSize(new Dimension(200,200));
		frame.pack();
		frame.setVisible(true);
		

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Grid grid = new Grid();
				Solver solver = new Solver();
				solver.solve(grid, textArea);
			}
		});
		thread.start();
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}