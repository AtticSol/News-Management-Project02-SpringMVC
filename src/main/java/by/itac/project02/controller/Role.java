package by.itac.project02.controller;

public enum Role {
	
	ROLE("role"),
	ADMIN("admin"),
	REPORTER("reporter"),
	USER("user"),
	GUEST("guest");

	private String title;

	Role(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	
}
