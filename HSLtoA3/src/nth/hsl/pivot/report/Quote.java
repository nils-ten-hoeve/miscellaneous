package nth.hsl.pivot.report;

public class Quote {

	private String productGroup;
	private String product;
	private String remark;
	private String remarkType;
	private Integer sequanceNr;
	private String source;

	public Quote(String productGroup, String product, String remark, String remarkType, Integer sequanceNr, String source) {
		super();
		this.productGroup = productGroup;
		this.product = product;
		this.remark = remark;
		this.remarkType = remarkType;
		this.sequanceNr = sequanceNr;
		this.source = source;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public String getProduct() {
		if (product == null) {
			return "";
		}
		int pos=product.indexOf("-");
		if (pos>0) {
			return product.substring(pos+1);
		}
		return product;
	}

	public String getRemark() {
		return remark;
	}

	public String getRemarkType() {
		return remarkType;
	}

	public Integer getSequanceNr() {
		return sequanceNr;
	}

	public String getSource() {
		return source;
	}

}
