package by.itac.project02.bean;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "info_about_updated_news")
public class InfoAboutUpdatedNews implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_info_about_updated_news")
	private int newsUpdateID;

	@Column(name = "date_of_update")
	private LocalDate dateOfNewsUpdate;

	@ManyToOne(fetch = FetchType.LAZY,
			cascade = { CascadeType.DETACH, CascadeType.MERGE,
					CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "users_id")
	private UserData reporter;
	
	@ManyToOne(fetch = FetchType.LAZY,
			cascade = { CascadeType.ALL })
	@JoinColumn(name = "news_id_news")
	private NewsData updatedNews;
	
	{
		this.dateOfNewsUpdate = LocalDate.now();		
	}
	
	
	public InfoAboutUpdatedNews() {}

	public InfoAboutUpdatedNews(int newsUpdateID, LocalDate dateOfNewsUpdate, UserData reporter, NewsData updatedNews) {
		this.newsUpdateID = newsUpdateID;
		this.dateOfNewsUpdate = dateOfNewsUpdate;
		this.reporter = reporter;
		this.updatedNews = updatedNews;
	}

	public int getNewsUpdateID() {
		return newsUpdateID;
	}

	public void setNewsUpdateID(int newsUpdateID) {
		this.newsUpdateID = newsUpdateID;
	}

	public LocalDate getDateOfNewsUpdate() {
		return dateOfNewsUpdate;
	}

	public void setDateOfNewsUpdate(LocalDate dateOfNewsUpdate) {
		this.dateOfNewsUpdate = dateOfNewsUpdate;
	}

	public UserData getReporter() {
		return reporter;
	}

	public void setReporter(UserData reporter) {
		this.reporter = reporter;
	}

	public NewsData getUpdatedNews() {
		return updatedNews;
	}

	public void setUpdatedNews(NewsData updatedNews) {
		this.updatedNews = updatedNews;
	}
	
	
	

}
