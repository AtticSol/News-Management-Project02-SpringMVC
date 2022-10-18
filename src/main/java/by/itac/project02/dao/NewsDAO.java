package by.itac.project02.dao;

import java.util.List;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;

public interface NewsDAO {

	List<NewsData> latestsList(int count) throws NewsDAOException;

	List<NewsData> newsListForOnePage(int skip, int count) throws NewsDAOException;

	int countOfNews() throws NewsDAOException;

	int addNews(NewsData news, int reporterID) throws NewsDAOException;

	NewsData findById(int idNews) throws NewsDAOException;
	
	void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws NewsDAOException;

	void deleteNews(int[] idNews) throws NewsDAOException;

}
