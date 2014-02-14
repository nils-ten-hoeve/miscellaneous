package nth.portfoliochart;

import java.util.ArrayList;
import java.util.List;

public class ProductGroup {
	private String name;
	private List<Supplier> suppliers;
	private final double potentialGrowthRateMeynForNext5Years;

	public ProductGroup(String name, double potentialGrowthRateMeynForNext5Years) {
		super();
		this.name = name;
		this.suppliers = new ArrayList<Supplier>();
		this.potentialGrowthRateMeynForNext5Years = potentialGrowthRateMeynForNext5Years;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Supplier> getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(List<Supplier> suppliers) {
		this.suppliers = suppliers;
	}

	public double getPotentialGrowthRateMeynForNext5Years() {
		return potentialGrowthRateMeynForNext5Years;
	}

	public int getTotalCapitalSales() {
		int total = 0;
		for (Supplier supplier : suppliers) {
			total += supplier.getEstimatedCapitalSalesRevanue2012();
		}
		return total;
	}

	public int getMeynCapitalSales() {
		int total = 0;
		for (Supplier supplier : suppliers) {
			if (supplier.getName().equals(ProductGroupFactory.MEYN)) {
				total += supplier.getEstimatedCapitalSalesRevanue2012();
			}
		}

		return total;

	}

}
