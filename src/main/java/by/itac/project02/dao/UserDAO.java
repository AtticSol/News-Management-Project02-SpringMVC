package by.itac.project02.dao;

import by.itac.project02.bean.UserData;

public interface UserDAO {

	int userID(String login) throws UserDAOException;

	String role(int userID) throws UserDAOException;

	int registration(UserData user) throws UserDAOException;

	boolean isLogin(String login) throws UserDAOException;
	
	String takePassword(String login) throws UserDAOException;

	boolean isEmail(String email) throws UserDAOException;

}
