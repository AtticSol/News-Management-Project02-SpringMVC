package by.itac.project02.controller.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import by.itac.project02.bean.InfoAboutUpdatedNews;
import by.itac.project02.bean.NewsData;
import by.itac.project02.controller.atribute.Atribute;
import by.itac.project02.controller.atribute.Constant;
import by.itac.project02.controller.atribute.JSPPageName;
import by.itac.project02.controller.atribute.Role;
import by.itac.project02.controller.atribute.SessionAtribute;
import by.itac.project02.service.NewsService;
import by.itac.project02.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysql.cj.util.StringUtils;

@Controller
@RequestMapping("/news")
public class News {

	@Autowired
	private NewsService newsService;

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
				model.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
			} else {
				newsList = newsService.latestList(Constant.MAX_NEWS_NUMBER_PER_PAGE);
			}

			if (Role.ADMIN.getTitle().equals(guestRole)) {
				model.addAttribute(Atribute.NO_NEWS_MODEL, request.getParameter(Atribute.NO_NEWS_MODEL));
			}

			model.addAttribute(Atribute.NEWS_LIST_MODEL, newsList);
			model.addAttribute(Atribute.PAGE_MODEL, pageList);
			model.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);

			session.setAttribute(SessionAtribute.PAGE_URL,
					pageURL(JSPPageName.GO_TO_NEWS_LIST, Atribute.PAGE_NUMBER, String.valueOf(pageNumber)));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/go_to_view_news")
	public String viewNews(HttpServletRequest request, @RequestParam(Atribute.NEWS_ID) int newsID,
			@RequestParam(Atribute.PAGE_NUMBER) int pageNumber, Model model) throws ServletException, IOException {

		NewsData news;
		HttpSession session = request.getSession(false);

		try {

			news = newsService.findById(newsID);

			model.addAttribute(Atribute.PRESENTATION, Atribute.VIEW_NEWS);
			model.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
			model.addAttribute(Atribute.NEWS_MODEL, news);

			session.setAttribute(SessionAtribute.PAGE_URL, pageURL(JSPPageName.GO_TO_VIEW_NEWS, Atribute.NEWS_ID,
					String.valueOf(newsID), Atribute.PRESENTATION, Atribute.VIEW_NEWS));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}
		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/go_to_edit_news")
	public String goToEditNews(HttpServletRequest request, @RequestParam("newsId") int newsID,
			@RequestParam(Atribute.PAGE_NUMBER) int pageNumber,
			@RequestParam(Atribute.PREVIOUS_PRESENTAION) String prePresentation, Model model)
			throws ServletException, IOException {

		NewsData news;
		HttpSession session = request.getSession(false);

		try {
			news = newsService.findById(newsID);

			model.addAttribute(Atribute.PRESENTATION, Atribute.EDIT_NEWS);
			model.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
			model.addAttribute(Atribute.PREVIOUS_PRESENTAION, prePresentation);

			session.setAttribute(SessionAtribute.PAGE_URL,
					pageURL(JSPPageName.GO_TO_EDIT_NEWS, Atribute.NEWS_ID, String.valueOf(newsID),
							Atribute.PREVIOUS_PRESENTAION, prePresentation, Atribute.PAGE_NUMBER,
							String.valueOf(pageNumber)));

			model.addAttribute(Atribute.NEWS_MODEL, news);
			model.addAttribute(Atribute.UPDATED_NEWS_MODEL, new InfoAboutUpdatedNews());

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}
		return JSPPageName.BASE_PAGE;

	}

	@RequestMapping("/go_to_add_news")
	public String goToAddNews(HttpServletRequest request, @RequestParam(Atribute.PAGE_NUMBER) int pageNumber,
			@RequestParam(Atribute.PREVIOUS_PRESENTAION) String prePresentation, Model model)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		model.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
		model.addAttribute(Atribute.PREVIOUS_PRESENTAION, prePresentation);
		model.addAttribute(Atribute.PRESENTATION, Atribute.ADD_NEWS);

		session.setAttribute(SessionAtribute.PAGE_URL, pageURL(JSPPageName.GO_TO_ADD_NEWS,
				Atribute.PREVIOUS_PRESENTAION, prePresentation, Atribute.PAGE_NUMBER, String.valueOf(pageNumber)));
		model.addAttribute(Atribute.NEWS_MODEL, new NewsData());

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/do_add_news")
	public String doAddNews(HttpServletRequest request, @ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			Model model) throws ServletException, IOException {

		HttpSession session;
		int reporterID;
		int newsID;

		session = request.getSession(false);
		reporterID = takeNumber(session.getAttribute(SessionAtribute.USER_ID).toString());

		try {
			newsID = newsService.save(newsData, reporterID);
			session.setAttribute(Atribute.NEWS, newsData);

			return "redirect:/" + pageURL(JSPPageName.GO_TO_VIEW_NEWS, Atribute.PRESENTATION, Atribute.VIEW_NEWS,
					Atribute.NEWS_ID, String.valueOf(newsID), Atribute.PAGE_NUMBER, String.valueOf(Constant.ONE_PAGE));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		}

	}

	@RequestMapping("/do_edit_news")
	public String doEditNews(HttpServletRequest request, @ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			@RequestParam(Atribute.PAGE_NUMBER) int pageNumber,
			@ModelAttribute(Atribute.UPDATED_NEWS_MODEL) InfoAboutUpdatedNews info, Model model,
			RedirectAttributes redirectAttributes) throws ServletException, IOException {

		HttpSession session;
		int reporterID;
		int newsID;

		session = request.getSession(false);

		reporterID = takeNumber(session.getAttribute(SessionAtribute.USER_ID).toString());
		newsID = takeNumber(newsData.getNewsID().toString());

		if (!(request.getParameter(Atribute.DATE_NEWS).isEmpty())) {
			newsData.setNewsDate(LocalDate.parse(request.getParameter(Atribute.DATE_NEWS)));
		}

		info.setUpdatedNews(newsData);

		try {
			newsService.updateNews(info, reporterID, newsData);

			session.setAttribute(Atribute.NEWS, newsData);
			redirectAttributes.addAttribute(Atribute.PAGE_NUMBER, pageNumber);

			return "redirect:/" + pageURL(JSPPageName.GO_TO_VIEW_NEWS, Atribute.PRESENTATION, Atribute.VIEW_NEWS,
					Atribute.NEWS_ID, String.valueOf(newsID));

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		}

	}

	@RequestMapping("/do_cancel")
	public String doCancel(HttpServletRequest request,
			@RequestParam(Atribute.PREVIOUS_PRESENTAION) String prePresentaion,
			@RequestParam(Atribute.PAGE_NUMBER) int pageNumber, @ModelAttribute(Atribute.NEWS_MODEL) NewsData newsData,
			RedirectAttributes redirectAttributes) throws ServletException, IOException {

		if (Atribute.VIEW_NEWS.equals(prePresentaion)) {
			redirectAttributes.addAttribute(Atribute.NEWS_ID, newsData.getNewsID());
			redirectAttributes.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
			return "redirect:/" + JSPPageName.GO_TO_VIEW_NEWS;

		} else {

			redirectAttributes.addAttribute(Atribute.PAGE_NUMBER, pageNumber);
			return "redirect:/" + JSPPageName.GO_TO_NEWS_LIST;
		}
	}

	@RequestMapping("/do_delete_news")
	public String doDeleteNews(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
			throws ServletException, IOException {

		String[] newsIDArr;
		HttpSession session;
		String role;

		session = request.getSession(false);

		role = (String) session.getAttribute(Role.ROLE.getTitle());
		newsIDArr = request.getParameterValues(Atribute.NEWS_ID);

		try {
			if (Role.USER.getTitle().equals(role)) {
				redirectAttributes.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);
				return "redirect:/" + JSPPageName.GO_TO_NEWS_LIST;
			} else {
				newsService.deleteNews(newsIDArr);
				session.removeAttribute(Atribute.NEWS);
				redirectAttributes.addAttribute(Atribute.PRESENTATION, Atribute.NEWS_LIST);

				return "redirect:/" + JSPPageName.GO_TO_NEWS_LIST;
			}
		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.ERROR_PAGE;
		}

	}

	private int takePageNumber(HttpServletRequest request) {
		String pageNumber = request.getParameter(Atribute.PAGE_NUMBER);
		if (pageNumber != null) {
			return Integer.parseInt(pageNumber);
		}
		return Constant.ONE_PAGE;
	}

	private int takeNumber(String inputNumber) {
		if (!StringUtils.isStrictlyNumeric(inputNumber)) {
			return Constant.NO_NUMBER;
		} else if (inputNumber.isEmpty()) {
			return Constant.NO_NUMBER;
		} else {
			return Integer.parseInt(inputNumber);
		}
	}

	// first argument - command, others - attributes with values
	private String pageURL(String command, String... param) {
		StringBuffer sb = new StringBuffer();
		sb.append(command);
		sb.append(Atribute.QUERY_SEPARATOR);
		for (int i = 0; i < param.length; i = i + 2) {
			sb.append(param[i]);
			sb.append(Atribute.EQUALS);
			sb.append(param[i + 1]);
			sb.append(Atribute.SEPARATOR);
		}
		return sb.toString();
	}
}
