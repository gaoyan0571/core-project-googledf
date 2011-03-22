package vo;

public class InfoVO {

	private Long id;
	private String type;
	private String source_url;
	private String source;
	private String title;

	private String content;
	private String add_time;
	private String edit_time;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String sourceUrl) {
		source_url = sourceUrl;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String addTime) {
		add_time = addTime;
	}
	public String getEdit_time() {
		return edit_time;
	}
	public void setEdit_time(String editTime) {
		edit_time = editTime;
	}
	
}
