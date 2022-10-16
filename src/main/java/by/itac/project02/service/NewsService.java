package by.itac.project02.service;

import java.util.List;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;
import by.itac.project02.service.validation.NewsValidationException;

public interface NewsService {
	int save(NewsData news, int reporterID) throws ServiceException, NewsValidationException;

	NewsData findById(int idNews) throws ServiceException, NewsValidationException;

	List<NewsData> latestList(int count) throws ServiceException, NewsValidationException;

	List<NewsData> newsListByPageNumber(int pageNumber) throws ServiceException, NewsValidationException;

	List<Integer> pageList() throws ServiceException;

	void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws ServiceException, NewsValidationException;

	void deleteNews(String[] idNewsArr) throws ServiceException, NewsValidationException;

}
