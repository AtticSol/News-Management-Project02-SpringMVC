package by.itac.project02.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "news")
public class NewsData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_news")
	private int newsID;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "brief")
	private String briefNews;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "date")
	private LocalDate newsDate;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = {CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "users_id")
	private UserData reporter;
	
	@OneToMany(fetch = FetchType.LAZY,
			mappedBy = "updatedNews",
			cascade = {CascadeType.ALL})
	private List<InfoAboutUpdatedNews> updatedNews;
	
	public List<InfoAboutUpdatedNews> getUpdatedNews() {
		return updatedNews;
	}

	public void setUpdatedNews(List<InfoAboutUpdatedNews> updatedNews) {
		this.updatedNews = updatedNews;
	}

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

	public UserData getReporter() {
		return reporter;
	}

	public void setReporter(UserData reporter) {
		this.reporter = reporter;
	}

	@Override
	public int hashCode() {
		return Objects.hash(briefNews, content, newsDate, newsID, reporter, title, updatedNews);
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
				&& Objects.equals(newsDate, other.newsDate) && newsID == other.newsID
				&& Objects.equals(reporter, other.reporter) && Objects.equals(title, other.title)
				&& Objects.equals(updatedNews, other.updatedNews);
	}

	@Override
	public String toString() {
		return "NewsData [newsID=" + newsID + ", title=" + title + ", briefNews=" + briefNews + ", content=" + content
				+ ", newsDate=" + newsDate + ", reporter=" + reporter + ", updatedNews=" + updatedNews + "]";
	}

}
