package nth.packml.gui.toolbar;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nth.packml.gui.statepane.BackgroundFactory;

public class ToolBar extends Pane {
	private static final Color MEYN_DARK_GREEN = Color.web("rgba(0,120,91,1.0)");

	public ToolBar() {
		setMaxHeight(40);
		setMaxWidth(1135);
		setTranslateY(-308);
		setTranslateX(-6);
		setBackground(BackgroundFactory.create(MEYN_DARK_GREEN,0));
	}
}
