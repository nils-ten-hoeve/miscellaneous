package nth.packml.gui.displaybuttonpane;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;

public class DisplayStateProperty extends SimpleObjectProperty<DisplayButtonState> {

	private static final DisplayButtonState INITIAL_VALUE = DisplayButtonState.OFF;

	public DisplayStateProperty(BooleanBinding onBinding, BooleanBinding blinkBinding) {
		set(INITIAL_VALUE);
		onBinding.addListener(e -> onChange(onBinding, blinkBinding));
		blinkBinding.addListener(e -> onChange(onBinding, blinkBinding));
	}

	private void onChange(BooleanBinding onBinding, BooleanBinding blinkBinding) {
		if (onBinding.get()) {
			set(DisplayButtonState.ON);
		} else if (blinkBinding.get()) {
			set(DisplayButtonState.BLINKING);
		} else {
			set(INITIAL_VALUE);
		}
	}
}
