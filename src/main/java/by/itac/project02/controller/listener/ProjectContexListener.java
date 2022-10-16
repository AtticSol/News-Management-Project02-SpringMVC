package by.itac.project02.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import by.itac.project02.dao.connection.ConnectionPool;
//import by.itac.project02.dao.connection.ConnectionPoolException;

public class ProjectContexListener implements ServletContextListener {

	private static final Logger log = LogManager.getRootLogger();

	public ProjectContexListener() {
	}

	@Override
	public void contextInitialized(ServletContextEvent e) {
//		try {
//			ConnectionPool.getInstanceCP();
//		} catch (ConnectionPoolException ex) {
//			log.log(Level.ERROR, "ConnectionPool is not initialized", ex);
//			throw new RuntimeException();
//		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
//		try {
//			ConnectionPool.getInstanceCP().dispose();
//		} catch (ConnectionPoolException ex) {
//			log.log(Level.ERROR, "ConnectionPool is not destroyed", ex);
//			throw new RuntimeException();
//		}
	}


}
