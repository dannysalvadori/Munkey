package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.entity.User;


@Controller
public class DemoController {
	
	@RequestMapping(value="/")
	public String indexPage(Model model){
		return "index";
	}

	@RequestMapping(value="/login", method=POST)
	public String homeHandler(Model model){
		/* Login page contains a Spring form.
		 * Before we load loginPage.jsp, we need to add a User object to the Model
		 * for the form to populate
		 */
		User user = new User();
		model.addAttribute("userObj", user);
		return "loginPage";
	}

	//The user object here is being passed from the Spring form
	@RequestMapping(value="/submit", method=POST)
	public String submitHandler(Model model, User user){
		model.addAttribute("user",user);
		return "view";
	}

}
