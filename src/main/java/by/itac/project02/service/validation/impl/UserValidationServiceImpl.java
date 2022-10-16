package by.itac.project02.service.validation.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;
import by.itac.project02.service.validation.UserValidationException;
import by.itac.project02.service.validation.UserValidationService;
import by.itac.project02.util.Constant;
import by.itac.project02.util.InputUserDataError;

@Service
public class UserValidationServiceImpl implements UserValidationService {

	private static final InputUserDataError noError = InputUserDataError.NO_ERROR;

	private static final String PASSWORD_REGEX = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}";
	private static final String EMAIL_REGEX = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
	private static final Logger log = LogManager.getRootLogger();

	@Override
	@Transactional
	public boolean inputRegistrationDataValidation(UserData user, UserDAO userDAO) throws UserValidationException {

		List<String> errorList = Collections.synchronizedList(new ArrayList<String>());

		error(errorList, checkLogin(user.getLogin(), userDAO));
		error(errorList, checkPassword(user.getPassword(), user));
		error(errorList, checkEmail(user.getUserDetail().getEmail(), userDAO));

		if (!errorList.isEmpty()) {
			throw new UserValidationException(errorList, "User not added");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean inputAithorizationDataValidation(String login, String password, UserDAO userDAO)
			throws UserValidationException {

		if (!isLogin(login, userDAO)) {
			return false;
		} else {
			if (!isCorrectPassword(login, password, userDAO)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean userIDValidation(int userID) throws UserValidationException {
		return userID == Constant.NO_NUMBER;
	}

	private boolean isCorrectPassword(String login, String password, UserDAO userDAO) throws UserValidationException {
		try {

			List<UserData> user = userDAO.takePassword(login);
			String hashPassword = user.get(0).getPassword();
			String salt = hashPassword.substring(0, 29);
			return hashPassword.equals(BCrypt.hashpw(password, salt));
		} catch (UserDAOException e) {
			log.log(Level.ERROR, "Password validation failed", e);
			throw new UserValidationException(e);
		}
	}

	private boolean isLogin(String login, UserDAO userDAO) throws UserValidationException {
		try {
			return userDAO.isLogin(login).size() == Constant.ONE_NUMBER;

		} catch (UserDAOException e) {
			log.log(Level.ERROR, "Login validation failed", e);
			throw new UserValidationException(e);
		}
	}

	private InputUserDataError checkPassword(String password, UserData user) {
		if (!isValidPassword(password)) {
			return InputUserDataError.PASSWORD_CREATE_ERROR;
		}

		String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		user.setPassword(hashPassword);

		return noError;
	}

	private boolean isValidPassword(String password) {
		return password.matches(PASSWORD_REGEX);
	}

	private InputUserDataError checkLogin(String login, UserDAO userDAO) throws UserValidationException {
		if (login.length() < 6) {
			return InputUserDataError.LOGIN_MIN_LENGTH;
		} else if (isLogin(login, userDAO)) {
			return InputUserDataError.LOGIN_EXISTS;
		}
		return noError;
	}

	private boolean isEmail(String email, UserDAO userDAO) throws UserDAOException {
		return (userDAO.isEmail(email).size() == Constant.ONE_NUMBER);
	}

	private InputUserDataError checkEmail(String email, UserDAO userDAO) throws UserValidationException {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(EMAIL_REGEX);
		matcher = pattern.matcher(email);

		try {
			if (("").equals(email)) {
				return InputUserDataError.EMAIL_INCORRECT;
			} else {
				if (!matcher.matches()) {
					return InputUserDataError.EMAIL_INCORRECT;
				}

				if (isEmail(email, userDAO)) {
					return InputUserDataError.EMAIL_EXISTS;
				}
			}

			return noError;

		} catch (UserDAOException e) {
			throw new UserValidationException(e);
		}
	}

	private List<String> error(List<String> errorList, InputUserDataError error) {
		if (!error.equals(noError)) {
			errorList.add(error.getTitle());
		}
		return errorList;
	}
}
