package by.itac.project02.service.impl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public int userID(String login, String password) throws ServiceException {

		try {

			List<UserData> user = userDAO.takePassword(login);
			String hashPassword = user.get(0).getPassword();
			String salt = hashPassword.substring(0, 29);
			if (!hashPassword.equals(BCrypt.hashpw(password, salt))) {
				throw new ServiceException("Error validation");
			}

			user = userDAO.userID(login);

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
	public String role(int userID) throws ServiceException {
		try {
			return userDAO.role(userID).getRole();
		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public int registration(UserData user) throws ServiceException {

		String hashPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashPassword);

		try {
			return userDAO.registration(user);

		} catch (UserDAOException e) {
			throw new ServiceException(e);
		}
	}

}
