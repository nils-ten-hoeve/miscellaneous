package nth.notifier.propertygrid;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PropertyCategoryRow extends JPanel {

	private static final long serialVersionUID = -6750529615073737210L;

	public PropertyCategoryRow(String text) {
		setBackground(ColorUtil.getMediumDarkColor());
		WrapingLabel label = new WrapingLabel(text);
		label.setForeground(ColorUtil.getDark());
		label.setAlignmentX(SwingConstants.LEFT);
		setPreferredSize(label.getPreferredSize());//set height to label height
		add(label);
	}

}
