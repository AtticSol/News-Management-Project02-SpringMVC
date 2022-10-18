package by.itac.project02.service;

import by.itac.project02.bean.UserData;

public interface UserService {

	int userID(String login, String password) throws ServiceException;

	String role(int userID) throws ServiceException;

	int registration(UserData user) throws ServiceException;

}
