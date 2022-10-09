package by.itac.project02.util;

public enum InputNewsDataError {

	TITLE_ERROR("titleError"),
	EMPTY_DATE_ERROR("dateError"),
	BRIEF_ERROR("briefError"),
	CONTENT_ERROR("contentError"),
	NO_NEWS_TO_DELETE_ERROR("noNewsToDelete"),	
	NO_ERROR("noError");

	
	private String title;

	InputNewsDataError(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
