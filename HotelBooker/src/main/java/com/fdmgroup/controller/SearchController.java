package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.entity.User;
import com.fdmgroup.pojo.Option;
import com.fdmgroup.pojo.SearchParameter;
import com.fdmgroup.search.OptionHandler;


@Controller
public class SearchController {
	
	/**
	 * On navigating to the home page, load a new SearchParameter instance. In future, this could maintain previous
	 * parameters so the user can modify their search rather than enter them again from scratch 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView indexPage(Model model){
		return new ModelAndView("index", "searchParameter", new SearchParameter());
	}
	
	/**
	 * Instructs Spring Form how to bind date input
	 * @param binder
	 */
	@InitBinder    
	public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class,
			new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
	
	/**
	 * Submits search parameters, adds optionList to the model
	 * @param searchParameters
	 * @param model
	 * @param result
	 * @return
	 */
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String runSearch(
//		@ModelAttribute("searchParameters") @Valid SearchParameter searchParameters,
		@ModelAttribute("searchParameters") SearchParameter searchParameters,
		BindingResult result,
		Model model		
	){
		if (result.hasErrors()) {
			System.out.println("error - bad search parameters");
			model.addAttribute("searchParameters", searchParameters);
			return "index";
		}
		// TODO: add proper validation on parameters not being left null, etc.
		searchParameters.findLatLong();
//		System.out.println("Location: " + searchParameters.getLocationString());
//		System.out.println("Lat / Long: " + searchParameters.getLatitude() + " / " + searchParameters.getLongitude());
//		System.out.println("Number of Guests: " + searchParameters.getNumberOfGuests());
//		System.out.println("Checkin: " + searchParameters.getCheckin());
//		System.out.println("Checkout: " + searchParameters.getCheckout());
		
		
		List<Option> optionList = OptionHandler.calculateOptions(searchParameters);
		
		model.addAttribute("optionList", optionList);
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
//	@RequestMapping(value="/submit", method=POST)
//	public String submitHandler(Model model, User user){
//		model.addAttribute("user",user);
//		return "view";
//	}

}
