package by.itac.project02.dao;

import java.util.List;

import by.itac.project02.bean.UserData;
import by.itac.project02.bean.UserDetail;

public interface UserDAO {

	List<UserData> userID(String login) throws UserDAOException;

	UserData role(int userID) throws UserDAOException;

	int registration(UserData user) throws UserDAOException;

	List<UserData> isLogin(String login) throws UserDAOException;
	
	List<UserData> takePassword(String login) throws UserDAOException;
	
	List<UserDetail> isEmail(String email) throws UserDAOException;

}
