package by.itac.project02.service;

import by.itac.project02.service.impl.NewsServiceImpl;
import by.itac.project02.service.impl.UserServiceImpl;

public class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider();
	private final NewsService newsService = new NewsServiceImpl();
	private final UserService userService = new UserServiceImpl();
	private ServiceProvider() {}
	
	public static ServiceProvider getInstance() {
		return instance;
	}
	
	public NewsService getNewsService() {
		return newsService;
	}
	
	public UserService getUserService() {
		return userService;
	}
	
	
}
