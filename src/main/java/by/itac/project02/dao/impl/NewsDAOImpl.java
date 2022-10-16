package by.itac.project02.dao.impl;


import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger log = LogManager.getRootLogger();

	private static final String LATEST_NEWS = "from NewsData order by newsID desc";
//	private static final String LATEST_NEWS = "from NewsData order by newsid desc";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
//	public List<NewsData> latestsList(int count) throws NewsDAOException {
	public List<NewsData> latestsList(int count) {

			Session currentSession = sessionFactory.getCurrentSession();
			Query<NewsData> theQuery = currentSession.createQuery(LATEST_NEWS, NewsData.class);
			theQuery.setMaxResults(count);
			List<NewsData> latestNews = theQuery.getResultList();
			return latestNews;

//		} catch (Exception e) {
//			log.log(Level.ERROR, "Error latest news getting", e);
//			throw new NewsDAOException("Error latest news getting", e);
//		}

	}

	private static final String NEWS_FOR_ONE_PAGE = "from NewsData order by newsID desc";

	@Override
	public List<NewsData> newsListForOnePage(int skip, int count) throws NewsDAOException {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<NewsData> theQuery = currentSession.createQuery(NEWS_FOR_ONE_PAGE, NewsData.class);
		theQuery.setFirstResult(skip);
		theQuery.setMaxResults(count);
		return theQuery.getResultList();

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error one page news getting", e);
//			throw new NewsDAOException("Error one page news getting", e);
//		}

	}

	private static final String COUNT_OF_NEWS = "select count(*) from NewsData";

	@Override
	public int countOfNews() throws NewsDAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return ((Number) currentSession.createQuery(COUNT_OF_NEWS).uniqueResult()).intValue();

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error count of news getting", e);
//			throw new NewsDAOException("Error count of news getting", e);
//		}
	}

	@Override
	public int addNews(NewsData news, int reporterID) throws NewsDAOException {

		Session currentSession = sessionFactory.getCurrentSession();
		UserData reporter = currentSession.get(UserData.class, reporterID);
		news.setReporter(reporter);
		currentSession.save(news);
		return news.getNewsID();

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Saving news failed", e);
//			throw new NewsDAOException("Saving news failed", e);
//		}

	}

	@Override
	public void updateNews(InfoAboutUpdatedNews info, int reporterID, NewsData news) throws NewsDAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		UserData reporter = currentSession.get(UserData.class, reporterID);
		news.setReporter(reporter);
		info.setReporter(reporter);
		currentSession.saveOrUpdate(news);
		currentSession.save(info);

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Editing news failed", e);
//			throw new NewsDAOException("Editing news failed", e);
//		}

	}

	@Override
	public void deleteNews(int[] id) throws NewsDAOException {
		try {

			Session currentSession = sessionFactory.getCurrentSession();
			NewsData news;
			for (int i = 0; i < id.length; i++) {
				news = currentSession.get(NewsData.class, id[i]);
				currentSession.remove(news);
			}
		} catch (Exception e) {
			throw new NewsDAOException();
		}

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Deleting news failed", e);
//			throw new NewsDAOException("Deleting news failed", e);
//		}
	}

	@Override
	public NewsData findById(int idNews) throws NewsDAOException {

		Session currentSession = sessionFactory.getCurrentSession();
		return currentSession.get(NewsData.class, idNews);

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Finding news failed", e);
//			throw new NewsDAOException("Finding news failed", e);
//		}
	}
}
