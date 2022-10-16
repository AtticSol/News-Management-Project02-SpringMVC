package by.itac.project02.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.UserService;
import by.itac.project02.service.validation.UserValidationException;
import by.itac.project02.service.validation.UserValidationService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private UserValidationService userValidationService;

	@Override
	@Transactional
	public int userID(String login, String password) throws ServiceException, UserValidationException {

		if (!userValidationService.inputAithorizationDataValidation(login, password, userDAO)) {
			throw new UserValidationException("Error validation");
		}

		try {
			List<UserData> user = userDAO.userID(login);

			if (user.size() != 1) {
				throw new ServiceException("Error getting id. Login is not unique.");
			}

			return user.get(0).getId();
		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public String role(int userID) throws ServiceException, UserValidationException {

		if (userValidationService.userIDValidation(userID)) {
			throw new UserValidationException("Error validation");
		}

		try {
			return userDAO.role(userID).getRole();
		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public int registration(UserData user) throws ServiceException, UserValidationException {

		if (!userValidationService.inputRegistrationDataValidation(user, userDAO)) {
			throw new UserValidationException("Error validation");
		}

		try {
			return userDAO.registration(user);

		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

}
