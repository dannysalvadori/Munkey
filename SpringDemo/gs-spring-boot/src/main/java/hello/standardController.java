/**
 * 
 */
package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Danny.Salvadori
 *
 */
@Controller
public class standardController {
	
	@RequestMapping(value="/view_register.htm", method = RequestMethod.POST)
	/** Navigates to the register page */
	public String navigateToRegistration(ModelMap model) {
		return "index.html";
	}

	@RequestMapping(value="/register.htm", method = RequestMethod.POST)
	/** Handles request from the registration page and registers the user */
	public String registerUser(ModelMap model) {
		// hard stuff to register the user
		return "welcome";
	}	
	
}
