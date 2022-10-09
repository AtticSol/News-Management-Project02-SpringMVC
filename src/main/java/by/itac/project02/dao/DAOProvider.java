package by.itac.project02.dao;

import by.itac.project02.dao.impl.NewsDAOImpl;
import by.itac.project02.dao.impl.UserDAOImpl;

public class DAOProvider {
	
	private static final DAOProvider instance = new DAOProvider();
	private final NewsDAO newsDao = new NewsDAOImpl();
	private final UserDAO userDAO = new UserDAOImpl();
	
	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}
	
	public NewsDAO getNewsDAO() {
		return newsDao;
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
}
