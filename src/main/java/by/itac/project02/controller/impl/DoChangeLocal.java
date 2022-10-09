package by.itac.project02.controller.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import by.itac.project02.controller.SessionAtribute;

@Controller
@RequestMapping("/local")
public class DoChangeLocal{

	
	@RequestMapping("/do_change_local")
	public String doChangeLocal(HttpServletRequest request) throws ServletException, IOException {
		String local;
		String url;

		HttpSession session = request.getSession(false);

		local = request.getParameter(SessionAtribute.LOCAL);
		url = session.getAttribute(SessionAtribute.PAGE_URL).toString();
		session.setAttribute(SessionAtribute.LOCAL, local);
		
		return "redirect:/" + url;

	}
}
