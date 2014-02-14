package nth.portfoliochart;

import java.awt.Color;

public class Supplier {

	private final String name;
	private final Color color;
	private final int estimatedCapitalSalesRevanue2012;

	public Supplier(String name, Color color, int estimatedCapitalSalesRevanue2012) {
		this.name = name;
		this.color = color;
		this.estimatedCapitalSalesRevanue2012 = estimatedCapitalSalesRevanue2012;
	}

	public String getName() {
		return name;
	}


	public Color getColor() {
		return color;
	}

	public int getEstimatedCapitalSalesRevanue2012() {
		return estimatedCapitalSalesRevanue2012;
	}

	
	
}
