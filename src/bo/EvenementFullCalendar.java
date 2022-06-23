package bo;

public class EvenementFullCalendar {

	private String start;
	private String end;
	private String title;
	private String url;
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public EvenementFullCalendar(String start, String end, String title, String url) {
		super();
		this.start = start;
		this.end = end;
		this.title = title;
		this.url = url;
	}
	public EvenementFullCalendar() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ReservationFullCalendar [start=" + start + ", end=" + end + ", title=" + title + ", url=" + url + "]";
	}
}
