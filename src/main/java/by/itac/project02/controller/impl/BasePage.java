package by.itac.project02.controller.impl;

import java.io.IOException;
import java.util.List;

import by.itac.project02.bean.NewsData;
import by.itac.project02.controller.Atribute;
import by.itac.project02.controller.JSPPageName;
import by.itac.project02.controller.SessionAtribute;
import by.itac.project02.service.NewsService;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.validation.NewsValidationException;
import by.itac.project02.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/command")
public class BasePage {
	
	@Autowired
	private NewsService newsService;

	@RequestMapping("/go_to_base_page")
	public String goToBasePage(HttpServletRequest request, Model model)
			throws ServletException, IOException {
		List<NewsData> latestNews;
		HttpSession session;
		String userStatus;

		try {
			latestNews = newsService.latestList(Constant.MAX_NEWS_NUMBER_PER_PAGE);

			session = request.getSession(true);
			session.setAttribute(SessionAtribute.PAGE_URL, JSPPageName.GO_TO_BASE_PAGE);
			request.setAttribute(Atribute.NEWS, latestNews);

			userStatus = request.getParameter(SessionAtribute.USER_STATUS);
			if (SessionAtribute.NOT_ACTIVE.equals(userStatus)) {
				session.setAttribute(SessionAtribute.USER_STATUS, userStatus);
			}

		} catch (ServiceException | NewsValidationException e) {
			return JSPPageName.ERROR_PAGE;
		}
		return JSPPageName.BASE_PAGE;
	}
	
	
	@RequestMapping("/go_to_error_page")
	public String goToErrorPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return JSPPageName.ERROR_PAGE;
	}
	
}
