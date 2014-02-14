package nth.portfoliochart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class DrawGraph extends JPanel {
	private static final int MAX_SCORE = 20;
	private static final int PREF_W = 800;
	private static final int PREF_H = 650;
	private static final int BORDER_SIZE = 30;
	private static final int AXIS_LINE_LENGTH = 3;
	private static final int Y_HATCH_CNT = 11;
	private static final int HORIZONTAL_AXES_WIDTH = 150;
	private static final int VERTICAL_AXES_HEIGHT = 150;
	private static final Color LABEL_COLOR1 = Color.WHITE;
	private static final Color BUBBLE_COLOR = Color.GRAY;
	private static final Color LABEL_COLOR2 = Color.BLACK;
	private static final int X_HORIZONTAL_AXIS_LABELS = BORDER_SIZE + HORIZONTAL_AXES_WIDTH;
	private List<ProductGroup> productGroups;


	public DrawGraph(List<ProductGroup> productGroups ) {
		this.productGroups = productGroups;
		setBackground(Color.white);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int widthMinusBorder = getWidth() - 2 * BORDER_SIZE;
		int widthMinusAxis = widthMinusBorder - HORIZONTAL_AXES_WIDTH;

		int heightMinusBorder = getHeight() - 2 * BORDER_SIZE;
		int heightMinusAxis = heightMinusBorder - VERTICAL_AXES_HEIGHT;

		
		int totalCapitalSales = 0;
		for (ProductGroup productGroup : productGroups) {
			totalCapitalSales += productGroup.getTotalCapitalSales();
		}

		double xScale = ((double) widthMinusAxis / totalCapitalSales);
		double yScale = ((double) heightMinusAxis / 10);

				// y axis
		g2.drawLine(BORDER_SIZE + HORIZONTAL_AXES_WIDTH, getHeight() - BORDER_SIZE - VERTICAL_AXES_HEIGHT, BORDER_SIZE + HORIZONTAL_AXES_WIDTH, BORDER_SIZE);
		// x axis
		g2.drawLine(BORDER_SIZE + HORIZONTAL_AXES_WIDTH, getHeight() - BORDER_SIZE - VERTICAL_AXES_HEIGHT, getWidth() - BORDER_SIZE, getHeight() - BORDER_SIZE - VERTICAL_AXES_HEIGHT);

		// create hatch marks for y axis.
		for (int i = 0; i < Y_HATCH_CNT; i++) {
			int x0 = BORDER_SIZE + HORIZONTAL_AXES_WIDTH;
			int x1 = x0 - AXIS_LINE_LENGTH;
			int y0 = BORDER_SIZE + (int) (((Y_HATCH_CNT - i - 1) * yScale));
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);

			drawCenteredText(g2, i * 10 + "%", x1, y0, Allignment.RIGHT);
		}

		int Y_PRODUCT_GROUP = getHeight() - BORDER_SIZE - VERTICAL_AXES_HEIGHT + 20;
		int Y_TOTAL_CAPITAL_SALES = Y_PRODUCT_GROUP + 20;
		int Y_MEYN_CAPITAL_SALES = Y_TOTAL_CAPITAL_SALES + 20;
		int Y_MARKET_POTENTIAL = Y_MEYN_CAPITAL_SALES + 40;

		// Labels X axis
		drawCenteredText(g2, "Product Group", X_HORIZONTAL_AXIS_LABELS, Y_PRODUCT_GROUP, Allignment.RIGHT);
		drawCenteredText(g2, "Total Capital Sales 2012", X_HORIZONTAL_AXIS_LABELS, Y_TOTAL_CAPITAL_SALES, Allignment.RIGHT);
		drawCenteredText(g2, "MEYN Capital Sales 2012", X_HORIZONTAL_AXIS_LABELS, Y_MEYN_CAPITAL_SALES, Allignment.RIGHT);
		drawCenteredText(g2, "Potential growth for Meyn in 5Y", X_HORIZONTAL_AXIS_LABELS, Y_MARKET_POTENTIAL, Allignment.RIGHT);

		int x = BORDER_SIZE + HORIZONTAL_AXES_WIDTH + 1;
		for (ProductGroup productGroup : productGroups) {
			int y = getHeight() - BORDER_SIZE - VERTICAL_AXES_HEIGHT - 1;
			int groupTotalCapitalSales = productGroup.getTotalCapitalSales();
			int barWidth = (int) (groupTotalCapitalSales * xScale);
			int barCenter = x + (barWidth / 2);

			g2.setColor(Color.black);
			drawCenteredText(g2, productGroup.getName(), barCenter, Y_PRODUCT_GROUP, Allignment.CENTER);
			drawCenteredText(g2, productGroup.getTotalCapitalSales() / 1000000 + " M€", barCenter, Y_TOTAL_CAPITAL_SALES, Allignment.CENTER);
			drawCenteredText(g2, productGroup.getMeynCapitalSales() / 1000000 + " M€", barCenter, Y_MEYN_CAPITAL_SALES, Allignment.CENTER);

			// // nr of people
			int MAX_DIAMETER = 40;
			// int maxNrOfPeople = 10;// TODO!
			// int diameter = ((int)((double)productGroup.getNrOfResearchAndDevelopmentPeopleWorkingOnProjects() / maxNrOfPeople * MAX_DIAMETER));
			// g2.setColor(Color.black);
			// drawBubble(g2, barCenter,Y_CURRENT_RD_PEOPLE, diameter, ""+productGroup.getNrOfResearchAndDevelopmentPeopleWorkingOnProjects());

			// nr of growth potential
			int maxGrowthPotential = 20;// TODO!
			int diameter = ((int) ((double) productGroup.getPotentialGrowthRateMeynForNext5Years() * 100 / maxGrowthPotential * MAX_DIAMETER));
			g2.setColor(Color.black);
			if (productGroup.getPotentialGrowthRateMeynForNext5Years() != -1) {
				drawBubble(g2, barCenter, Y_MARKET_POTENTIAL, diameter, (int) (productGroup.getPotentialGrowthRateMeynForNext5Years() * 100) + "%");
			}

			// potential Growth

			// g2.drawString(productGroup.getPotentialGrowthRateMeynForNext5Years() + " %", x, y + 70);

			// line on x-axis
			g2.drawLine(x + barWidth, y, x + barWidth, y + AXIS_LINE_LENGTH);
			// }
			for (Supplier supplier : productGroup.getSuppliers()) {

				int cellHeight = (int) (((double) supplier.getEstimatedCapitalSalesRevanue2012() / groupTotalCapitalSales) * heightMinusAxis);

				g2.setColor(LABEL_COLOR1);
				g2.drawRect(x, y - cellHeight, barWidth, cellHeight);
				g2.setColor(supplier.getColor());
				g2.fillRect(x + 1, y - cellHeight + 1, barWidth - 1, cellHeight - 1);

				g2.setColor(LABEL_COLOR1);
				drawCenteredText(g2, supplier.getName(), barCenter, y - (cellHeight / 2), Allignment.CENTER);

				y -= cellHeight;
			}

			// TODO draw groupName, totalCapitalSales, and cagr labels

			x += barWidth;
		}
	}

	private void drawBubble(Graphics2D graphics2d, int x, int y, int diameter, String label) {
		graphics2d.setColor(BUBBLE_COLOR);
		drawCenteredCircle(graphics2d, x, y, diameter);
		graphics2d.setColor(LABEL_COLOR2);
		drawCenteredText(graphics2d, label, x, y, Allignment.CENTER);
	}

	private void drawCenteredCircle(Graphics2D graphics2d, int x, int y, int diameter) {
		graphics2d.fillOval(x - (diameter / 2), y - (diameter / 2), diameter, diameter);
	}

	enum Allignment {
		LEFT, CENTER, RIGHT
	}

	private void drawCenteredText(Graphics2D graphics2d, String text, int x, int y, Allignment horizontalAllignment) {
		FontMetrics fontMetrics = graphics2d.getFontMetrics();
		int textWidth = fontMetrics.stringWidth(text);
		int textHeight = fontMetrics.getHeight();
		switch (horizontalAllignment) {
		case LEFT:
			x = x;
			break;
		case CENTER:
			x -= (textWidth / 2);
			break;
		case RIGHT:
			x -= textWidth;
			break;
		}
		graphics2d.drawString(text, x, y + (textHeight / 3));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	private static void createAndShowGui() {
		List<ProductGroup> productGroups= ProductGroupFactory.createProductGroups();
		DrawGraph mainPanel = new DrawGraph(productGroups);

		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);

		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}