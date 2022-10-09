package by.itac.project02.service.validation;

import by.itac.project02.bean.UserData;

public interface UserValidationService {
	boolean inputRegistrationDataValidation(UserData user) throws UserValidationException;

	boolean inputAithorizationDataValidation(String login, String password) throws UserValidationException;

	boolean userIDValidation(int userID) throws UserValidationException;
}
