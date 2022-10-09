package by.itac.project02.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class NewsData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer newsID;
	private String title;
	private String briefNews;
	private String content;
	private LocalDate newsDate;
	
	{
		this.newsDate = LocalDate.now();
	}

	public NewsData() {	}

	public NewsData(int newsID, String title, String briefNews, String content, LocalDate newsDate) {
		this.newsID = newsID;
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
	}

	public NewsData(String title, String briefNews, String content, LocalDate newsDate) {
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
	}

	public NewsData(String title, String briefNews, String content) {
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
	}

	public Integer getNewsID() {
		return newsID;
	}

	public void setNewsID(Integer newsID) {
		this.newsID = newsID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBriefNews() {
		return briefNews;
	}

	public void setBriefNews(String briefNews) {
		this.briefNews = briefNews;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;

	}

	public LocalDate getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(LocalDate newsDate) {
		this.newsDate = newsDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(briefNews, content, newsID, newsDate, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NewsData other = (NewsData) obj;
		return Objects.equals(briefNews, other.briefNews) && Objects.equals(content, other.content)
				&& Objects.equals(newsID, other.newsID) && Objects.equals(newsDate, other.newsDate)
				&& Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return "NewsData [newsID=" + newsID + ", title=" + title + ", briefNews=" + briefNews + ", content=" + content
				+ ", newsDate=" + newsDate + "]";
	}

}
