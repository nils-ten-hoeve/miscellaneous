package nth.grid36.grid;

public class Tower {

	private Color color;
	private int height;

	public Tower(Color color, int height) {
		this.color = color;
		this.height = height;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		if (color == null || height < 1) {
			return "  ";
		} else {
			return color.toString().substring(0, 1) + height;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tower) {
			Tower tower = (Tower) obj;
			return tower.getColor() == color && tower.getHeight() == height;
		} else {
			return false;
		}
	}

}
