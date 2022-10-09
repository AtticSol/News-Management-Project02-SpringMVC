package by.itac.project02.service;

import by.itac.project02.bean.UserData;
import by.itac.project02.service.validation.UserValidationException;

public interface UserService {

	int userID(String login, String password) throws ServiceException, UserValidationException;

	String role(int userID) throws ServiceException, UserValidationException;

	int registration(UserData user) throws ServiceException, UserValidationException;

}
