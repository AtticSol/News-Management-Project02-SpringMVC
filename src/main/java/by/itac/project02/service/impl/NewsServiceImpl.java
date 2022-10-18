package by.itac.project02.service.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;
import by.itac.project02.controller.atribute.Constant;
import by.itac.project02.dao.NewsDAO;
import by.itac.project02.dao.NewsDAOException;
import by.itac.project02.service.NewsService;
import by.itac.project02.service.ServiceException;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsDAO newsDAO;

	@Override
	@Transactional
	public int save(NewsData news, int reporterID) throws ServiceException {
		try {
			return newsDAO.addNews(news, reporterID);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public NewsData findById(int id) throws ServiceException {
		try {
			return newsDAO.findById(id);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<NewsData> latestList(int count) throws ServiceException {
		try {
			return newsDAO.latestsList(count);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	@Transactional
	public List<NewsData> newsListByPageNumber(int pageNumber) throws ServiceException {
		try {
			int countOfAllNews;
			int maxNewsNumberPerPage;

			countOfAllNews = newsDAO.countOfNews();
			maxNewsNumberPerPage = Constant.MAX_NEWS_NUMBER_PER_PAGE;

			if (countOfAllNews < maxNewsNumberPerPage) {
				maxNewsNumberPerPage = countOfAllNews;
			}

			int skip = (pageNumber - 1) * maxNewsNumberPerPage;

			return newsDAO.newsListForOnePage(skip, maxNewsNumberPerPage);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<Integer> pageList() throws ServiceException {

		try {
			CopyOnWriteArrayList<Integer> pageList = new CopyOnWriteArrayList<>();
			double newsNumber;
			double pageNumber;

			newsNumber = newsDAO.countOfNews();
			pageNumber = newsNumber / Constant.MAX_NEWS_NUMBER_PER_PAGE;

			int i = 0;
			while (i < pageNumber) {
				pageList.add(i + 1);
				i++;
			}
			return pageList;

		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}

	}

	@Override
	@Transactional
	public void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws ServiceException {

		try {
			newsDAO.updateNews(info, reporterID, news);

		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public void deleteNews(String[] idNewsArrStr) throws ServiceException {

		int[] idNewsArrInt = new int[idNewsArrStr.length];

		int i = 0;
		for (String idNews : idNewsArrStr) {
			idNewsArrInt[i] = Integer.parseInt(idNews);
			i++;
		}

		try {
			newsDAO.deleteNews(idNewsArrInt);
		} catch (NewsDAOException e) {
			throw new ServiceException(e);
		}
	}

}
