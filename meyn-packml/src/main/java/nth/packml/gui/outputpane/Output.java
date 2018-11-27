package nth.packml.gui.outputpane;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import nth.packml.gui.displaybuttonpane.Style;
import nth.packml.gui.statepane.BackgroundFactory;

public class Output extends HBox {

	private final BooleanProperty onProperty;
	private final Label light;
	private final Color onColor;

	public Output(String name, Color onColor, SimpleObjectProperty<Boolean> valueProperty) {
		this.onColor = onColor;
		light = createLight();
		getChildren().add(light);

		Label text = createText(name);
		getChildren().add(text);

		onProperty = createOnProperty(valueProperty);
		onProperty.addListener(e -> onOnChange());
		onOnChange();
	}

	private Label createText(String name) {
		Label text = new Label(name);
		text.setPadding(new Insets(0, 0, 0, 5));
		return text;
	}

	private Label createLight() {
		Label light = new Label();
		light.setPrefHeight(20);
		light.setPrefWidth(20);
		return light;
	}

	private void onOnChange() {
		if (onProperty.get()) {
			light.setBackground(BackgroundFactory.create(onColor, 10));
		} else {
			light.setBackground(BackgroundFactory.create(Style.OFF_COLOR, 10));
		}
	}

	private BooleanProperty createOnProperty(SimpleObjectProperty<Boolean> valueProperty) {
		return BooleanProperty.booleanProperty(valueProperty);
	}

}
