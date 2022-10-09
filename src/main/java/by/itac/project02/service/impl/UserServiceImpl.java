package by.itac.project02.service.impl;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.DAOProvider;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.UserService;
import by.itac.project02.service.validation.UserValidationException;
import by.itac.project02.service.validation.UserValidationService;
import by.itac.project02.service.validation.ValidationProvider;

public class UserServiceImpl implements UserService {

	private final UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
	private final UserValidationService userValidationService = ValidationProvider.getInstance().getUserValidationService();

	@Override
	public int userID(String login, String password) throws ServiceException, UserValidationException {

		if (!userValidationService.inputAithorizationDataValidation(login, password)) {
			throw new UserValidationException("Error validation");
		}

		try {
			return userDAO.userID(login);
		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public String role(int userID) throws ServiceException, UserValidationException {
		
		if(userValidationService.userIDValidation(userID)) {
			throw new UserValidationException("Error validation");
		}
		
		try {
			return userDAO.role(userID);
		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int registration(UserData user) throws ServiceException, UserValidationException {

		if (!userValidationService.inputRegistrationDataValidation(user)) {
			throw new UserValidationException("Error validation");
		}
		
		try {
			return userDAO.registration(user);

		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

}
