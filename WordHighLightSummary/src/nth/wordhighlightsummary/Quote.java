package nth.wordhighlightsummary;


public class Quote {

	private String text;
	private String source;

	public Quote(String source) {
		super();
		this.text = "";
		this.source = source;
	}

	public String getText() {
		return normilizeText(text);
	}

	private String normilizeText(String text2) {
		StringBuffer reply = new StringBuffer();
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (((int) ch) >= 32) {
				reply.append(ch);
			}
		}
		return reply.toString().trim().replace("\r\n", " ").replace("\r", " ").replace("\n", " ").replace("  ", " ");
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void appendText(String text) {
		this.text = this.text + text;
	}

}
