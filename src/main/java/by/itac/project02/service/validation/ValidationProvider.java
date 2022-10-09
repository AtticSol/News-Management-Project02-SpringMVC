package by.itac.project02.service.validation;

import by.itac.project02.service.validation.impl.NewsValidationServiceImpl;
import by.itac.project02.service.validation.impl.UserValidationServiceImpl;

public class ValidationProvider {
	private static final ValidationProvider instance = new ValidationProvider();
	private final UserValidationService userValidationService = new UserValidationServiceImpl();
	private final NewsValidationService newsValidationService = new NewsValidationServiceImpl();
	
	private ValidationProvider() {}
	
	public static ValidationProvider getInstance() {
		return instance;
	}
	
	public UserValidationService getUserValidationService() {
		return userValidationService;
	}
	
	public NewsValidationService getNewsValidationService() {
		return newsValidationService;
	}

}
