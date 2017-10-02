package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
		@ModelAttribute("searchParameters") SearchParameter searchParameters,
		BindingResult result,
		Model model		
	){
		if (result.hasErrors()) {
			//TODO: Proper error handlnig
			System.out.println("error - bad search parameters");
			model.addAttribute("searchParameters", searchParameters);
			return "index";
		}
		// TODO: Translate search location into latitude and longitude 
		searchParameters.findLatLong();		
		
		// Get options and add to model
		List<Option> optionList = OptionHandler.calculateOptions(searchParameters);
		model.addAttribute("optionList", optionList);
		return "search";
	}

	/**
	 * TODO: log in functionality
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login", method=POST)
	public String homeHandler(Model model){
		User user = new User();
		model.addAttribute("userObj", user);
		return "loginPage";
	}

}
