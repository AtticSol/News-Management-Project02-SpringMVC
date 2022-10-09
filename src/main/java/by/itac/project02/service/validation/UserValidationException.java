package by.itac.project02.service.validation;

import java.util.List;

public class UserValidationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private List <String> errorList;

	
	public UserValidationException() {
		super();
	}

	public UserValidationException(String message) {
		super(message);
	}

	public UserValidationException(Exception e) {
		super(e);
	}

	public UserValidationException(String message, Exception ex) {
		super(message, ex);
	}
	
	public UserValidationException(List<String> errorList, String message) {
		super(message);
		this.errorList = errorList;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
