package nth.hsl.a3report;

import java.util.List;

public class Remark {

	private String remark;
	private String remarkType;
	private String source;
	private List<String> votedBy;
	
	
	
	public Remark(String remark, String remarkType, String source, List<String> votedBy) {
		super();
		this.remark = remark;
		this.remarkType = remarkType;
		this.source = source;
		this.votedBy = votedBy;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemarkType() {
		return remarkType;
	}
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<String> getVotedBy() {
		return votedBy;
	}
	public void setVotedBy(List<String> votedBy) {
		this.votedBy = votedBy;
	}

	

}
