package by.itac.project02.util;

public enum InputUserDataError {
	
	LOGIN_MIN_LENGTH("loginLength"),
	LOGIN_EXISTS("loginExists"),
	PASSWORD_CREATE_ERROR("passwordCreateError"),
	EMAIL_EXISTS("emailExists"),
	EMAIL_INCORRECT("emailIncorrect"),
	NO_ERROR("noError");
	
	private String title;
	
	InputUserDataError(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

}
