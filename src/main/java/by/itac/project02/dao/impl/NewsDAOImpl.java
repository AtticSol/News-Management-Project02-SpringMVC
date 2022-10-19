package by.itac.project02.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;
import by.itac.project02.bean.UserData;
import by.itac.project02.dao.NewsDAO;
import by.itac.project02.dao.NewsDAOException;

@Repository
public class NewsDAOImpl implements NewsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final String LATEST_NEWS = "from NewsData order by newsID desc";

	@Override
	public List<NewsData> latestsList(int count) throws NewsDAOException {
		try {

			Session currentSession = sessionFactory.getCurrentSession();
			Query<NewsData> theQuery = currentSession.createQuery(LATEST_NEWS, NewsData.class);
			theQuery.setMaxResults(count);
			List<NewsData> latestNews = theQuery.getResultList();
			return latestNews;

		} catch (Exception e) {
			throw new NewsDAOException("Error latest news getting", e);
		}

	}

	private static final String NEWS_FOR_ONE_PAGE = "from NewsData order by newsID desc";

	@Override
	public List<NewsData> newsListForOnePage(int skip, int count) throws NewsDAOException {
		try {

			Session currentSession = sessionFactory.getCurrentSession();
			Query<NewsData> theQuery = currentSession.createQuery(NEWS_FOR_ONE_PAGE, NewsData.class);
			theQuery.setFirstResult(skip);
			theQuery.setMaxResults(count);
			return theQuery.getResultList();

		} catch (Exception e) {
			throw new NewsDAOException("Error one page news getting", e);
		}

	}

	private static final String COUNT_OF_NEWS = "select count(*) from NewsData";

	@Override
	public int countOfNews() throws NewsDAOException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			return ((Number) currentSession.createQuery(COUNT_OF_NEWS).uniqueResult()).intValue();

		} catch (Exception e) {
			throw new NewsDAOException("Error count of news getting", e);
		}
	}

	@Override
	public int addNews(NewsData news, int reporterID) throws NewsDAOException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			UserData reporter = currentSession.get(UserData.class, reporterID);
			news.setReporter(reporter);
			currentSession.save(news);
			return news.getNewsID();

		} catch (Exception e) {
			throw new NewsDAOException("Saving news failed", e);
		}

	}

	@Override
	public void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws NewsDAOException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			UserData reporter = currentSession.get(UserData.class, reporterID);
			news.setReporter(reporter);
			info.setReporter(reporter);
			currentSession.saveOrUpdate(news);
			currentSession.save(info);

		} catch (Exception e) {
			throw new NewsDAOException("Editing news failed", e);
		}

	}

	@Override
	public void deleteNews(int[] id) throws NewsDAOException {
		try {
			Session currentSession = sessionFactory.getCurrentSession();
			NewsData news;
			for (int idNews : id) {
				news = currentSession.get(NewsData.class, idNews);
				currentSession.remove(news);
			}
		} catch (Exception e) {
			throw new NewsDAOException("Deleting news failed", e);
		}
	}

	@Override
	public NewsData findById(int idNews) throws NewsDAOException {
		try {

			Session currentSession = sessionFactory.getCurrentSession();
			return currentSession.get(NewsData.class, idNews);

		} catch (Exception e) {
			throw new NewsDAOException("Finding news failed", e);
		}
	}
}
