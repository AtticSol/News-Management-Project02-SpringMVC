package by.itac.project02.controller.impl;

import java.io.IOException;

import by.itac.project02.bean.UserData;
import by.itac.project02.bean.UserDetail;
import by.itac.project02.controller.atribute.Atribute;
import by.itac.project02.controller.atribute.Constant;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registration")
public class Registration {

	@Autowired
	private UserService userService;

	@RequestMapping("/go_to_registration_page")
	public String goToRegistrationPage(HttpServletRequest request, Model model) throws ServletException, IOException {

		HttpSession session;
		session = request.getSession(false);

		session.setAttribute(SessionAtribute.USER_STATUS, SessionAtribute.REGISTERATION);
		session.setAttribute(SessionAtribute.PAGE_URL, JSPPageName.GO_TO_REGISTRATION_PAGE);

		model.addAttribute(Atribute.REGISTRATION_MODEL, new UserData());

		return JSPPageName.BASE_PAGE;
	}

	@RequestMapping("/do_registration")
	public String doRegistration(HttpServletRequest request,
			@ModelAttribute(Atribute.REGISTRATION_MODEL) UserData userRegisterData,
			@RequestParam(Atribute.NAME) String name, @RequestParam(Atribute.EMAIL) String email, Model model)
			throws ServletException, IOException {

		HttpSession session;
		int userID;
		String role;

		role = Role.GUEST.getTitle();

		userRegisterData.setUserDetail(new UserDetail(name, email));

		try {
			userID = userService.registration(userRegisterData);
			session = request.getSession(true);

			if (userID != Constant.NO_NUMBER) {
				role = Role.USER.getTitle();

				session.setAttribute(SessionAtribute.USER_STATUS, SessionAtribute.ACTIVE);
				session.setAttribute(Role.ROLE.getTitle(), role);
				session.setAttribute(SessionAtribute.USER_ID, userID);

				return "redirect:/" + JSPPageName.GO_TO_NEWS_LIST;

			} else {
				return JSPPageName.BASE_PAGE;
			}

		} catch (ServiceException e) {
			return JSPPageName.BASE_PAGE;

		}
	}
}
