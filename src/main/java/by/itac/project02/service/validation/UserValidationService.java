package by.itac.project02.service.validation;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.UserDAO;

public interface UserValidationService {
	boolean inputRegistrationDataValidation(UserData user, UserDAO userDAO) throws UserValidationException;

	boolean inputAithorizationDataValidation(String login, String password, UserDAO userDAO) throws UserValidationException;

	boolean userIDValidation(int userID) throws UserValidationException;
}
