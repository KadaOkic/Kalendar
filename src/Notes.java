
public class Notes {

	private String date;
	private String text;

	public Notes() {

	}

	public Notes(String date, String text) {
		this.date = date;
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return this.getDate() + " " + this.getText();

	}
}
