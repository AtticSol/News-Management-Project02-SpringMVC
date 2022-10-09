package by.itac.project02.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.itac.project02.bean.UserData;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;
import by.itac.project02.dao.connection.ConnectionPool;
import by.itac.project02.dao.connection.ConnectionPoolException;
import by.itac.project02.util.Constant;

public class UserDAOImpl implements UserDAO {
	private static final Logger log = LogManager.getRootLogger();
	private static final String ID_COLUMN = "id";
	private static final String ROLE_COLUMN = "role";
	private static final String LOGIN_COLUMN = "login";
	private static final String PASSWORD_COLUMN = "password";
	private static final String EMAIL_COLUMN = "email";

	private static final String GET_ID_BY_LOGIN_SQL_REQUEST = "SELECT id FROM users WHERE login=?";

	@Override
	public int userID(String login) throws UserDAOException {
		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(GET_ID_BY_LOGIN_SQL_REQUEST)) {

			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			rs.next();

			return rs.getInt(ID_COLUMN);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error user id searching", e);
			throw new UserDAOException("Error user id searching", e);
		}
	}

	private static final String GET_ROLE_BY_ID_SQL_REQUEST = "SELECT role FROM users WHERE id=?";

	@Override
	public String role(int userID) throws UserDAOException {
		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(GET_ROLE_BY_ID_SQL_REQUEST)) {

			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			rs.next();

			return rs.getString(ROLE_COLUMN);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error user role searching", e);
			throw new UserDAOException("Error user role searching", e);
		}

	}

	private static final String ADD_USER_SQL_REQUEST = "INSERT INTO users(login, password, role) VALUES(?,?,?)";
	private static final String ADD_USER_DETAILS_SQL_REQUEST = "INSERT INTO user_details(users_id, name, email) VALUES(LAST_INSERT_ID(),?,?)";
	private static final String USER_ID_SQL_REQUEST = "SELECT LAST_INSERT_ID() FROM users";

	@Override
	public int registration(UserData user) throws UserDAOException {
		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement psAddUser = con.prepareStatement(ADD_USER_SQL_REQUEST);
				PreparedStatement psAddDetails = con.prepareStatement(ADD_USER_DETAILS_SQL_REQUEST);
				PreparedStatement psSelectId = con.prepareStatement(USER_ID_SQL_REQUEST)) {

			return registrationDataTransaction(psAddUser, psAddDetails, psSelectId, con, user);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Registration failed", e);
			throw new UserDAOException("Registration failed", e);
		}

	}

	private int registrationDataTransaction(PreparedStatement psAddUser, PreparedStatement psAddDetails,
			PreparedStatement psSelectId, Connection con, UserData user) throws SQLException {

		con.setAutoCommit(false);

		psAddUser.setString(1, user.getLogin());
		psAddUser.setString(2, user.getPassword());
		psAddUser.setString(3, user.getRole());

		psAddDetails.setString(1, user.getName());
		psAddDetails.setString(2, user.getEmail());

		try {
			psAddUser.executeUpdate();
			psAddDetails.executeUpdate();
			ResultSet rs = psSelectId.executeQuery();
			con.commit();

			rs.next();

			return rs.getInt(1);

		} catch (SQLException e) {
			log.log(Level.INFO, "Registration data transaction falied", e);
			con.rollback();
		}

		return Constant.NO_NUMBER;

	}

	private static final String GET_LOGIN_SQL_REQUEST = "SELECT login FROM users";

	@Override
	public boolean isLogin(String login) throws UserDAOException {

		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(GET_LOGIN_SQL_REQUEST)) {

			while (rs.next()) {
				if ((rs.getString(LOGIN_COLUMN)).equals(login)) {
					return true;
				}
			}

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error login searching", e);
			throw new UserDAOException("Error login searching", e);
		}
		return false;
	}

	private static final String GET_PASSWORD_SQL_REQUEST = "SELECT password FROM users WHERE login=?";
	
	@Override
	public String takePassword(String login) throws UserDAOException {
		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(GET_PASSWORD_SQL_REQUEST)) {

			ps.setString(1, login);
			ResultSet rs = ps.executeQuery();
			rs.next();

			return rs.getString(PASSWORD_COLUMN);

		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error taking hash password", e);
			throw new UserDAOException("Error taking hash password", e);
		}
	}

	private static final String GET_EMAIL_SQL_REQUEST = "SELECT * FROM user_details";

	@Override
	public boolean isEmail(String email) throws UserDAOException {
		try (Connection con = ConnectionPool.getInstanceCP().takeConnection();
				PreparedStatement ps = con.prepareStatement(GET_EMAIL_SQL_REQUEST);
				ResultSet rs = ps.executeQuery(GET_EMAIL_SQL_REQUEST)) {

			while (rs.next()) {
				if ((rs.getString(EMAIL_COLUMN)).equals(email)) {
					return true;
				}
			}
		} catch (SQLException | ConnectionPoolException e) {
			log.log(Level.ERROR, "Error email searching", e);
			throw new UserDAOException("Error email searching", e);
		}
		return false;
	}

}
