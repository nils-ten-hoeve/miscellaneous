package nth.notifier.propertygrid;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PropertyRow extends JPanel {
	private static final long serialVersionUID = -1361779111256295050L;
	private final FieldWidth fieldWidth;
	private WrapingLabel label;
	private final Component field;
	private JPanel fieldAndValidatorPanel;
	private Component validator;
	private FocusListener focusListener;

	public enum FieldWidth {
		half, full
	};

	public enum FieldHeight {
		low, high
	};

	// public PropertyRow(String propertyName, String propertyDescription,Component field, FieldWidth fieldWidth, FieldHeight fieldHeight, Component validator) {
	// this(propertyName, propertyDescription,field, fieldWidth, validator);
	// int height=(FieldHeight.low.equals(fieldHeight)) ? PropertyGridLayout.DEFAULT_LOW_FIELD_HEIGHT : PropertyGridLayout.DEFAULT_HIGH_FIELD_HEIGHT;
	// getField().setPreferredSize(new Dimension(Integer.MAX_VALUE,height));
	// }

	public PropertyRow(String propertyName, String propertyDescription, final Component field, FieldWidth fieldWidth, Component validator) {
		this.field = field;
		this.fieldWidth = fieldWidth;
		this.validator = validator;
		setLayout(null); // NOTE no layout manager! Layout is handled by the PropertyGridLayout
		setBackground(ColorUtil.getLightColor());
		label = new WrapingLabel(propertyName);
		label.setToolTipText(propertyDescription);
		label.setForeground(ColorUtil.getDark());
		// label.setDisplayedMnemonic() is set by propertyGrid.addPropertyRow
		label.setLabelFor(field);
		add(label);

		// make sure that the field is visible in the scroll pane when it (or one of its children) receives focus
		registerFocusListerner(field);

		fieldAndValidatorPanel = new JPanel(null);
		fieldAndValidatorPanel.setBackground(ColorUtil.getLightColor());
		fieldAndValidatorPanel.add(field);
		fieldAndValidatorPanel.add(getValidator());
		add(fieldAndValidatorPanel);
	}

	private void registerFocusListerner(Component component) {
		component.addFocusListener(getFocusListerner());
		if (component instanceof Container) {
			Container container = (Container) component;
			for (Component child:container.getComponents()) {
				registerFocusListerner(child);//Recursive
			}
		}
	}

	private FocusListener getFocusListerner() {
		if (focusListener == null) {
			focusListener = new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {
				}

				@Override
				public void focusGained(FocusEvent e) {
					SwingUtil.scrollToComponent((Component) e.getSource());
				}
			};
		}
		return focusListener;
	}

	public FieldWidth getFieldWidth() {
		return fieldWidth;
	}

	public JLabel getLabel() {
		return label;
	}

	public Component getField() {
		return field;
	}

	public JPanel getFieldAndValidatorPanel() {
		return fieldAndValidatorPanel;
	}

	public Component getValidator() {
		if (validator == null) {
			validator = new JLabel();
		}
		return validator;
	}

}
