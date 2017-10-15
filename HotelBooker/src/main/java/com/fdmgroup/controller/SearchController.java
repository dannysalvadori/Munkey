package com.fdmgroup.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		// Search location must be converted to latitude/longitude to find hotels 
		searchParameters.findLatLong();
		Boolean locationError = false;
		if (searchParameters.getLatitude() == null || searchParameters.getLongitude() == null) {
			locationError = true;
		}
		
		if (result.hasErrors() || locationError) {
			return "search";
		}
		
		// Get options and add to model
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hb_persistence_unit");
		List<Option> optionList = OptionHandler.calculateOptions(searchParameters, emf);
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
