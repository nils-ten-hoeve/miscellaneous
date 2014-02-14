package nth.notifier.propertygrid;

import java.awt.Dimension;

import javax.swing.JPanel;

public class PropertySeperatorRow extends JPanel {

	private static final long serialVersionUID = 1902884200557920802L;

	public PropertySeperatorRow() {
		setMaximumSize(new Dimension(Integer.MAX_VALUE,1));
		setBackground(ColorUtil.getMiddleColor());
	}
}
