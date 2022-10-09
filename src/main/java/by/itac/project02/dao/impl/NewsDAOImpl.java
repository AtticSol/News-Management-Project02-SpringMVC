package by.itac.project02.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itac.project02.bean.NewsData;
import by.itac.project02.dao.NewsDAO;
import by.itac.project02.dao.NewsDAOException;
import by.itac.project02.dao.connection.ConnectionPool;
import by.itac.project02.dao.connection.ConnectionPoolException;

public class NewsDAOImpl implements NewsDAO {
	private static final Logger log = LogManager.getRootLogger();
	private static final String ID_NEWS_COLUMN = "idnews";
	private static final String TITLE_COLUMN = "title";
	private static final String BRIEF_COLUMN = "brief";
	private static final String CONTENT_COLUMN = "content";
	private static final String DATE_CREATION_COLUMN = "news_creation_date";
	private static final String DATE_UPDATE_COLUMN = "news_update_date";
	private static final String TOTAL_COUNT_OF_NEWS = "total";

	private static final String LATEST_NEWS_SQL_REQUEST = "SELECT * FROM news ORDER BY idnews DESC LIMIT ?";

	@Override
	public List<NewsData> latestsList(int count) throws NewsDAOException {
		List<NewsData> latestNews = new ArrayList<NewsData>();

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(LATEST_NEWS_SQL_REQUEST)) {

			ps.setInt(1, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				latestNews.add(new NewsData(rs.getInt(ID_NEWS_COLUMN), rs.getString(TITLE_COLUMN), rs.getString(BRIEF_COLUMN),
								rs.getString(CONTENT_COLUMN), rs.getDate(DATE_CREATION_COLUMN).toLocalDate()));
			}

			return latestNews;
		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error latest news getting", e);
			throw new NewsDAOException("Error latest news getting", e);
		}
	}

	private static final String NEWS_FOR_ONE_PAGE_SQL_REQUEST = "SELECT * FROM news ORDER BY idnews DESC LIMIT ?, ?";

	@Override
	public List<NewsData> newsListForOnePage(int skip, int count) throws NewsDAOException {
		List<NewsData> result = new ArrayList<NewsData>();

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(NEWS_FOR_ONE_PAGE_SQL_REQUEST)) {

			ps.setInt(1, skip);
			ps.setInt(2, count);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				result.add(new NewsData(rs.getInt(ID_NEWS_COLUMN), rs.getString(TITLE_COLUMN), rs.getString(BRIEF_COLUMN),
						rs.getString(CONTENT_COLUMN), rs.getDate(DATE_CREATION_COLUMN).toLocalDate()));
			}

			return result;
		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error one page news getting", e);
			throw new NewsDAOException("Error one page news getting", e);
		}
	}

	private static final String COUNT_OF_NEWS_SQL_REQUEST = "SELECT COUNT(*) AS ? FROM news";

	@Override
	public int countOfNews() throws NewsDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(COUNT_OF_NEWS_SQL_REQUEST)) {

			ps.setString(1, TOTAL_COUNT_OF_NEWS);
			ResultSet rs = ps.executeQuery();

			rs.next();
			return rs.getInt(TOTAL_COUNT_OF_NEWS);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error count of news getting", e);
			throw new NewsDAOException("Error count of news getting", e);
		}

	}

	private static final String ADD_NEWS_SQL_REQUEST = "INSERT INTO news(title, brief, content, news_creation_date, reporter_id) VALUES(?,?,?,?,?)";
	private static final String GET_NEWS_ID_SQL_REQUEST = "SELECT LAST_INSERT_ID() FROM news";

	@Override
	public int addNews(NewsData news, int reporterID) throws NewsDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement psInsert = con.prepareStatement(ADD_NEWS_SQL_REQUEST);
				PreparedStatement psSelect = con.prepareStatement(GET_NEWS_ID_SQL_REQUEST)) {

			psInsert.setString(1, news.getTitle());
			psInsert.setString(2, news.getBriefNews());
			psInsert.setString(3, news.getContent());
			psInsert.setDate(4, Date.valueOf(news.getNewsDate()));
			psInsert.setInt(5, reporterID);
			psInsert.executeUpdate();

			ResultSet rs = psSelect.executeQuery();
			rs.next();

			return rs.getInt(1);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Saving news failed", e);
			throw new NewsDAOException("Saving news failed", e);
		}

	}

	private static final String UPDATE_NEWS_BY_ID_SQL_REQUEST = "UPDATE news SET title=?, brief=?, content=? WHERE idnews=?";
	private static final String DETAILS_OF_UPDATED_NEWS_SQL_REQUEST = "INSERT INTO details_of_updated_news (idnews, reporter_id, news_update_date) VALUES(?,?,?)";

	@Override
	public void updateNews(NewsData news, int reporterID) throws NewsDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps1 = con.prepareStatement(UPDATE_NEWS_BY_ID_SQL_REQUEST);
				PreparedStatement ps2 = con.prepareStatement(DETAILS_OF_UPDATED_NEWS_SQL_REQUEST)) {

			con.setAutoCommit(false);

			ps1.setString(1, news.getTitle());
			ps1.setString(2, news.getBriefNews());
			ps1.setString(3, news.getContent());
			ps1.setInt(4, news.getNewsID());

			ps2.setInt(1, news.getNewsID());
			ps2.setInt(2, reporterID);
			ps2.setDate(3, Date.valueOf(news.getNewsDate()));

			try {
				ps1.executeUpdate();
				ps2.executeUpdate();
				con.commit();
			} catch (SQLException e) {
				log.log(Level.INFO, "Updating news transaction failed", e);
				con.rollback();
			}

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Editing news failed", e);
			throw new NewsDAOException("Editing news failed", e);
		}

	}

	private static final String DELETE_NEWS_BY_ID_SQL_REQUEST = "DELETE FROM news WHERE idnews=?";

	@Override
	public void deleteNews(int[] idNews) throws NewsDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(DELETE_NEWS_BY_ID_SQL_REQUEST)) {

			for (int id : idNews) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Deleting news failed", e);
			throw new NewsDAOException("Deleting news failed", e);
		}
	}

	private static final String FIND_NEWS_BY_ID_SQL_REQUEST = "SELECT * FROM news WHERE idnews=?";

	@Override
	public NewsData findById(int idNews) throws NewsDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(FIND_NEWS_BY_ID_SQL_REQUEST)) {

			ps.setInt(1, idNews);
			ResultSet rs = ps.executeQuery();

			rs.next();

			return new NewsData(rs.getInt(ID_NEWS_COLUMN), rs.getString(TITLE_COLUMN), rs.getString(BRIEF_COLUMN),
					rs.getString(CONTENT_COLUMN), rs.getDate(DATE_CREATION_COLUMN).toLocalDate());

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Finding news failed", e);
			throw new NewsDAOException("Finding news failed", e);
		}
	}
}
