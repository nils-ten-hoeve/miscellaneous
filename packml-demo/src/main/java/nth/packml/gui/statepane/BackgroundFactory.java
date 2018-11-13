package nth.packml.gui.statepane;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BackgroundFactory {

	public static Background create(Color color, int rounded) {
		return new Background(new BackgroundFill(color, new CornerRadii(rounded), Insets.EMPTY));
	}

}
