package by.itac.project02.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import by.itac.project02.controller.JSPPageName;

@Controller
public class IndexPage {

	@RequestMapping("/")
	public String goToWelcomePage() {
		return "redirect:/" + JSPPageName.GO_TO_BASE_PAGE;
	}

}
