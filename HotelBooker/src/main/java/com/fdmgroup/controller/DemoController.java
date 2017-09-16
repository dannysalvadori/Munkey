package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.dev.DevUtils;
import com.fdmgroup.entity.User;

import pojo.Option;


@Controller
public class DemoController {
	
	@RequestMapping(value="/")
	public String indexPage(Model model){
		return "index";
	}
	
	@RequestMapping(value="/search")
	public String runSearch(Model model){
		List<Option> myList = new ArrayList<Option>();
		for (int i = 0; i < 10; i++) {
			Option o = new Option();
			o.setCapacity(DevUtils.randomIntBetween(2, 6));
			o.setHotelName("Happy Hotel Number " + i + "!");
			o.setDescription("1x Big room\netc\netc");
			o.setPrice(DevUtils.roundToNDecimalPlaces(Double.valueOf(o.getCapacity()*15 + Math.random()*2), 5));
			myList.add(o);
		}
		model.addAttribute("optionList", myList);
		return "search";
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
