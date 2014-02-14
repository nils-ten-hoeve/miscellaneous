package nth.hsl.a3report;

public class Rank {
	private String person;
	private Integer priority;
	
	
	public Rank(String person, Integer priority) {
		this.person = person;
		this.priority = priority;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	

}
