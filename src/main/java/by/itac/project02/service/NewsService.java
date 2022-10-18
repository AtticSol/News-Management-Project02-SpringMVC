package by.itac.project02.service;

import java.util.List;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;

public interface NewsService {
	int save(NewsData news, int reporterID) throws ServiceException;

	NewsData findById(int idNews) throws ServiceException;

	List<NewsData> latestList(int count) throws ServiceException;

	List<NewsData> newsListByPageNumber(int pageNumber) throws ServiceException;

	List<Integer> pageList() throws ServiceException;

	void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws ServiceException;

	void deleteNews(String[] idNewsArr) throws ServiceException;

}
