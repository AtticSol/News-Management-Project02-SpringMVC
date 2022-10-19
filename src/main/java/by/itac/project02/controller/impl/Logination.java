package by.itac.project02.controller.impl;

import java.io.IOException;

import by.itac.project02.bean.LoginData;
import by.itac.project02.controller.atribute.Atribute;
import by.itac.project02.controller.atribute.JSPPageName;
import by.itac.project02.controller.atribute.Role;
import by.itac.project02.controller.atribute.SessionAtribute;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/login")
public class Logination {

	@Autowired
	private UserService userService;

	@RequestMapping("/logination_form")
	public String loginationForm(Model model,
				     HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		session.setAttribute(SessionAtribute.USER_STATUS, SessionAtribute.LOGINATION);
		session.setAttribute(SessionAtribute.PAGE_URL, JSPPageName.LOGINATION_FORM);
		model.addAttribute(Atribute.LOGINATION_MODEL, new LoginData());

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/do_sign_in")
	public String doSignIn(HttpServletRequest request,
			       @ModelAttribute(Atribute.LOGINATION_MODEL) LoginData loginData,
			       Model model,
			       RedirectAttributes redirectAttributes)
					throws ServletException, IOException {

		String login;
		String password;
		HttpSession session;

		login = loginData.getLogin();
		password = loginData.getPassword();

		try {
			int idUser = userService.userID(login, password);
			String role = userService.role(idUser);
			boolean guestRole = role.equals(Role.GUEST.getTitle());

			session = request.getSession(true);

			if (!guestRole) {
				session.setAttribute(SessionAtribute.USER_STATUS, SessionAtribute.ACTIVE);
				session.setAttribute(Role.ROLE.getTitle(), role);
				session.setAttribute(SessionAtribute.USER_ID, idUser);
				session.setAttribute(SessionAtribute.PAGE_URL, JSPPageName.GO_TO_NEWS_LIST);

				return "redirect:/" + JSPPageName.GO_TO_NEWS_LIST;
			} else {
				model.addAttribute(Atribute.AUTHENTICATION_ERROR, Atribute.AUTHENTICATION_ERROR);
				return JSPPageName.BASE_PAGE;
			}

		} catch (ServiceException e) {
			return "redirect:/" + JSPPageName.GO_TO_ERROR_PAGE;
		}
	}

	@RequestMapping("/do_sign_out")
	public String execute(HttpServletRequest request) throws ServletException, IOException {
		HttpSession session;
		session = request.getSession(false);

		session.removeAttribute(SessionAtribute.USER_STATUS);
		session.removeAttribute(Role.ROLE.getTitle());
		session.removeAttribute(SessionAtribute.USER_ID);

		return "redirect:/" + JSPPageName.GO_TO_BASE_PAGE;
	}
}
