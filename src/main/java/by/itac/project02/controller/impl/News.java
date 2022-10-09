package by.itac.project02.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import by.itac.project02.bean.NewsData;
import by.itac.project02.controller.Atribute;
import by.itac.project02.controller.JSPPageName;
import by.itac.project02.controller.JSPParameter;
import by.itac.project02.controller.Role;
import by.itac.project02.controller.SessionAtribute;
import by.itac.project02.service.NewsService;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.ServiceProvider;
import by.itac.project02.service.validation.NewsValidationException;
import by.itac.project02.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/news")
public class News {

	private final NewsService newsService = ServiceProvider.getInstance().getNewsService();

	@RequestMapping("/go_to_news_list")
	public String newsList(RedirectAttributes redirectAttributes, HttpServletRequest request, Model model)
			throws ServletException, IOException {

		HttpSession session;
		List<NewsData> newsList;
		List<Integer> pageList;
		String guestRole;

		session = request.getSession(false);
		guestRole = (String) session.getAttribute(Role.ROLE.getTitle());

		try {
			pageList = newsService.pageList();
			int pageNumber = takePageNumber(request);

			if (!Role.GUEST.getTitle().equals(guestRole)) {
				newsList = newsService.newsListByPageNumber(pageNumber);
				model.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			} else {
				newsList = newsService.latestList(Constant.MAX_NEWS_NUMBER_PER_PAGE);
			}

			if (Role.ADMIN.getTitle().equals(guestRole)) {
				model.addAttribute(Atribute.NO_NEWS_MODEL, request.getParameter(Atribute.NO_NEWS_MODEL));
			}

			model.addAttribute(Atribute.NEWS_LIST_MODEL, newsList);
			model.addAttribute(Atribute.PAGE_MODEL, pageList);
			model.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);

			session.setAttribute(SessionAtribute.PAGE_URL, Util.pageURL(JSPPageName.NEWS_LIST,
					JSPParameter.PAGE_NUMBER, String.valueOf(pageNumber)));

		} catch (ServiceException | NewsValidationException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/go_to_view_news")
	public String viewNews(HttpServletRequest request,
			@RequestParam(JSPParameter.ID_NEWS) int newsID,
			@RequestParam(JSPParameter.PAGE_NUMBER) int pageNumber,
			Model model)
			throws ServletException, IOException {

		NewsData news;
		HttpSession session = request.getSession(false);

		try {

			news = newsService.findById(newsID);

			model.addAttribute(Atribute.PRESENTATION, Atribute.VIEW_NEWS);
			model.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			model.addAttribute(Atribute.NEWS_MODEL, news);

			session.setAttribute(SessionAtribute.PAGE_URL, Util.pageURL(JSPPageName.GO_TO_VIEW_NEWS,
					JSPParameter.ID_NEWS, String.valueOf(newsID),
					Atribute.PRESENTATION, Atribute.VIEW_NEWS));

		} catch (ServiceException | NewsValidationException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}
		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/go_to_edit_news")
	public String goToEditNews(HttpServletRequest request, @RequestParam("newsId") int newsID,
			@RequestParam(JSPParameter.PAGE_NUMBER) int pageNumber,
			@RequestParam(JSPParameter.PREVIOUS_PRESENTAION) String prePresentation, Model model)
			throws ServletException, IOException {

		NewsData news;
		HttpSession session = request.getSession(false);

		try {
			news = newsService.findById(newsID);

			model.addAttribute(Atribute.PRESENTATION, Atribute.EDIT_NEWS);
			model.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			model.addAttribute(JSPParameter.PREVIOUS_PRESENTAION, prePresentation);

			session.setAttribute(SessionAtribute.PAGE_URL,
					Util.pageURL(JSPPageName.GO_TO_EDIT_NEWS, JSPParameter.ID_NEWS, String.valueOf(newsID),
							JSPParameter.PREVIOUS_PRESENTAION, prePresentation, JSPParameter.PAGE_NUMBER,
							String.valueOf(pageNumber)));

			model.addAttribute(Atribute.NEWS_MODEL, news);

		} catch (ServiceException | NewsValidationException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}
		return JSPPageName.BASE_PAGE;

	}

	@RequestMapping("/go_to_add_news")
	public String goToAddNews(HttpServletRequest request,
			@RequestParam(JSPParameter.PAGE_NUMBER) int pageNumber,
			@RequestParam(JSPParameter.PREVIOUS_PRESENTAION) String prePresentation,
			Model model)
					throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		model.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
		model.addAttribute(JSPParameter.PREVIOUS_PRESENTAION, prePresentation);
		model.addAttribute(Atribute.PRESENTATION, Atribute.ADD_NEWS);

		session.setAttribute(SessionAtribute.PAGE_URL,
				Util.pageURL(JSPPageName.GO_TO_ADD_NEWS, JSPParameter.PREVIOUS_PRESENTAION, prePresentation,
						JSPParameter.PAGE_NUMBER, String.valueOf(pageNumber)));
		model.addAttribute(Atribute.NEWS_MODEL, new NewsData());

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/do_add_news")
	public String doAddNews(HttpServletRequest request,
			@ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			Model model)
					throws ServletException, IOException {

		HttpSession session;
		int reporterID;
		int newsID;

		session = request.getSession(false);
		reporterID = Util.takeNumber(session.getAttribute(SessionAtribute.USER_ID).toString());

		try {
			newsID = newsService.save(newsData, reporterID);
			session.setAttribute(Atribute.NEWS, newsData);

			return "redirect:/" + Util.pageURL(JSPPageName.GO_TO_VIEW_NEWS,
					Atribute.PRESENTATION, Atribute.VIEW_NEWS,
					Atribute.NEWS_ID, String.valueOf(newsID),
					JSPParameter.PAGE_NUMBER,	String.valueOf(Constant.ONE_PAGE));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		} catch (NewsValidationException e) {

			model.addAttribute(Atribute.ERROR_LIST, e.getErrorList());
			model.addAttribute(Atribute.PRESENTATION, Atribute.ADD_NEWS);
			model.addAttribute(JSPParameter.PREVIOUS_PRESENTAION, Atribute.NEWS_LIST);
			model.addAttribute(JSPParameter.PAGE_NUMBER, String.valueOf(Constant.ONE_PAGE));

			return JSPPageName.BASE_PAGE;
		}

	}

	@RequestMapping("/do_edit_news")
	public String doEditNews(HttpServletRequest request,
			@ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			@RequestParam(JSPParameter.PAGE_NUMBER) int pageNumber,
			Model model,
			RedirectAttributes redirectAttributes)
					throws ServletException, IOException {

		HttpSession session;
		int reporterID;
		int newsID;		

		session = request.getSession(false);

		reporterID = Util.takeNumber(session.getAttribute(SessionAtribute.USER_ID).toString());
		newsID = Util.takeNumber(newsData.getNewsID().toString());
		
		if (!(request.getParameter(JSPParameter.DATE_NEWS).isEmpty())) {
			newsData.setNewsDate(LocalDate.parse(request.getParameter(JSPParameter.DATE_NEWS)));
		} 
		
		try {
			newsService.updateNews(newsData, reporterID);
			session.setAttribute(Atribute.NEWS, newsData);
			redirectAttributes.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);

			return "redirect:/" + Util.pageURL(JSPPageName.GO_TO_VIEW_NEWS,
					Atribute.PRESENTATION, Atribute.VIEW_NEWS,
					Atribute.NEWS_ID, String.valueOf(newsID));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		} catch (NewsValidationException e) {

			model.addAttribute(Atribute.ERROR_LIST, e.getErrorList());
			model.addAttribute(Atribute.PRESENTATION, Atribute.EDIT_NEWS);
			model.addAttribute(JSPParameter.PREVIOUS_PRESENTAION, Atribute.VIEW_NEWS);
			model.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			model.addAttribute(Atribute.NEWS_ID, String.valueOf(newsID));
			
			return JSPPageName.BASE_PAGE;
		}

	}

	@RequestMapping("/do_cancel")
	public String doCancel(HttpServletRequest request,
			@RequestParam(JSPParameter.PREVIOUS_PRESENTAION) String prePresentaion,
			@RequestParam(JSPParameter.PAGE_NUMBER) int pageNumber,
			@ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			RedirectAttributes redirectAttributes)
					throws ServletException, IOException {

		if (Atribute.VIEW_NEWS.equals(prePresentaion)) {
			redirectAttributes.addAttribute(JSPParameter.ID_NEWS, newsData.getNewsID());
			redirectAttributes.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			return "redirect:/" + JSPPageName.GO_TO_VIEW_NEWS;

		} else {

			redirectAttributes.addAttribute(JSPParameter.PAGE_NUMBER, pageNumber);
			return "redirect:/" + JSPPageName.NEWS_LIST;
		}
	}

	@RequestMapping("/do_delete_news")
	public String doDeleteNews(HttpServletRequest request,
			Model model,
			RedirectAttributes redirectAttributes)
					throws ServletException, IOException {

		String[] newsIDArr;
		HttpSession session;
		String role;

		session = request.getSession(false);

		role = (String) session.getAttribute(Role.ROLE.getTitle());
		newsIDArr = request.getParameterValues(JSPParameter.ID_NEWS);

		try {
			if (Role.USER.getTitle().equals(role)) {
				redirectAttributes.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);
				return "redirect:/" + JSPPageName.NEWS_LIST;
			} else {
				newsService.deleteNews(newsIDArr);
				session.removeAttribute(Atribute.NEWS);
				redirectAttributes.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);

				return "redirect:/" + JSPPageName.NEWS_LIST;
			}
		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		} catch (NewsValidationException e) {

			redirectAttributes.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);
			redirectAttributes.addAttribute(Atribute.NO_NEWS_MODEL, e.getErrorList());

			return "redirect:/" + JSPPageName.NEWS_LIST;
		}

	}

	private int takePageNumber(HttpServletRequest request) {
		String pageNumber = request.getParameter(JSPParameter.PAGE_NUMBER);
		if (pageNumber != null) {
			return Integer.parseInt(pageNumber);
		}
		return Constant.ONE_PAGE;
	}
}
