package by.itac.project02.controller.impl;

import java.io.IOException;

import by.itac.project02.bean.UserData;
import by.itac.project02.controller.Atribute;
import by.itac.project02.controller.JSPPageName;
import by.itac.project02.controller.Role;
import by.itac.project02.controller.SessionAtribute;
import by.itac.project02.service.ServiceException;
import by.itac.project02.service.ServiceProvider;
import by.itac.project02.service.UserService;
import by.itac.project02.service.validation.UserValidationException;
import by.itac.project02.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class Registration {

	private final UserService userService = ServiceProvider.getInstance().getUserService();

	@RequestMapping("/go_to_registration_page")
	public String goToRegistrationPage(HttpServletRequest request,
			Model model)
					throws ServletException, IOException {

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
			Model model)
					throws ServletException, IOException {

		HttpSession session;
		int userID;
		String role;

		role = Role.GUEST.getTitle();

		try {
			userID = userService.registration(userRegisterData);
			session = request.getSession(true);

			if (userID != Constant.NO_NUMBER) {
				role = Role.USER.getTitle();

				session.setAttribute(SessionAtribute.USER_STATUS, SessionAtribute.ACTIVE);
				session.setAttribute(Role.ROLE.getTitle(), role);
				session.setAttribute(SessionAtribute.USER_ID, userID);

				return "redirect:/" + JSPPageName.NEWS_LIST;

			} else {
				return JSPPageName.BASE_PAGE;
			}

		} catch (ServiceException e) {
			return JSPPageName.BASE_PAGE;

		} catch (UserValidationException e) {

			userRegisterData.setPassword(null);
			userRegisterData.setConfirmPassword(null);
			model.addAttribute(Atribute.REGISTRATION_MODEL, userRegisterData);
			model.addAttribute(Atribute.REGISTRATION_ERROR, Atribute.REGISTRATION_ERROR);
			model.addAttribute(Atribute.ERROR_LIST, e.getErrorList());

			return JSPPageName.BASE_PAGE;
		}
	}
}
