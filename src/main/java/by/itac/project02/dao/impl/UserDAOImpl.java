package by.itac.project02.dao.impl;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.itac.project02.bean.UserData;
import by.itac.project02.bean.UserDetail;
import by.itac.project02.dao.UserDAO;
import by.itac.project02.dao.UserDAOException;

@Repository
public class UserDAOImpl implements UserDAO {	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger log = LogManager.getRootLogger();
	
	private static final String GET_USER_BY_LOGIN = "from UserData where login = :paramLogin";

	@Override
	public List<UserData> userID(String login) throws UserDAOException {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserData> theQuery = currentSession.createQuery(GET_USER_BY_LOGIN, UserData.class);
		theQuery.setParameter("paramLogin", login);
		return theQuery.getResultList();
		
//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error user id searching", e);
//			throw new UserDAOException("Error user id searching", e);
//		}		
	}

	@Override
	public UserData role(int userID) throws UserDAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		return  currentSession.get(UserData.class, userID);

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error user role searching", e);
//			throw new UserDAOException("Error user role searching", e);
//		}

	}

	@Override
	public int registration(UserData user) throws UserDAOException {
		Session currentSession = sessionFactory.getCurrentSession();
		currentSession.saveOrUpdate(user);		
		return user.getId();
		
//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Registration failed", e);
//			throw new UserDAOException("Registration failed", e);
//		}	
	}


	@Override
	public List<UserData> isLogin(String login) throws UserDAOException {

		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserData> theQuery = currentSession.createQuery(GET_USER_BY_LOGIN, UserData.class);
		theQuery.setParameter("paramLogin", login);
		return theQuery.getResultList();
		
//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error login searching", e);
//			throw new UserDAOException("Error login searching", e);
//		}
		
	}
	
	@Override
	public List<UserData> takePassword(String login) throws UserDAOException {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserData> theQuery = currentSession.createQuery(GET_USER_BY_LOGIN, UserData.class);
		theQuery.setParameter("paramLogin", login);
		return theQuery.getResultList();

//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error taking hash password", e);
//			throw new UserDAOException("Error taking hash password", e);
//		}
	}

	private static final String GET_USER_BY_EMAIL= "from UserDetail where email = :paramEmail";

	@Override
	public List<UserDetail> isEmail(String email) throws UserDAOException {
		
		Session currentSession = sessionFactory.getCurrentSession();
		Query<UserDetail> theQuery = currentSession.createQuery(GET_USER_BY_EMAIL, UserDetail.class);
		theQuery.setParameter("paramEmail", email);
		return theQuery.getResultList();
		
//		} catch (SQLException | ConnectionPoolException e) {
//			log.log(Level.ERROR, "Error email searching", e);
//			throw new UserDAOException("Error email searching", e);
//		}
	}

}
