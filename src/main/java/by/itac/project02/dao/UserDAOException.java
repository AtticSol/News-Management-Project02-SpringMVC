package by.itac.project02.dao;

public class UserDAOException extends Exception {

	private static final long serialVersionUID = 1401584324623758179L;

	public UserDAOException() {
		super();
	}

	public UserDAOException(String message) {
		super(message);
	}

	public UserDAOException(Exception e) {
		super(e);
	}

	public UserDAOException(String message, Exception e) {
		super(message, e);
	}
}
