package nth.hsl.a3report;

import java.util.List;

public class Product {
	private String productGroup;
	private String product;
	private List<Remark> remarks;
	private List<Rank> ranks;

	
	public Product(String productGroup, String product, List<Remark> remarks, List<Rank> ranks) {
		this.productGroup = productGroup;
		this.product = product;
		this.remarks = remarks;
		this.ranks = ranks;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	
	public List<Rank> getRanks() {
		return ranks;
	}

	public void setRanks(List<Rank> ranks) {
		this.ranks = ranks;
	}

	public List<Remark> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<Remark> remarks) {
		this.remarks = remarks;
	}

	
}
