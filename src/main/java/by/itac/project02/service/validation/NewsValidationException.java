package by.itac.project02.service.validation;

import java.util.List;

public class NewsValidationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private List <String> errorList;
	
	public NewsValidationException() {
		super();
	}

	public NewsValidationException(String message) {
		super(message);
	}

	public NewsValidationException(Exception e) {
		super(e);
	}

	public NewsValidationException(String message, Exception ex) {
		super(message, ex);
	}
	
	public NewsValidationException(List<String> errorList, String message) {
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
