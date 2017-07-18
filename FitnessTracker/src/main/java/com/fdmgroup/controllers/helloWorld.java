package com.fdmgroup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helloWorld {
	
	@RequestMapping(value ="/greeting")
	public String sayHello (Model model) {
		
		model.addAttribute("greeting", "Hello World");
		
		return "hello";
	}
	
	@RequestMapping(value ="/goodbye")
	public String sayBye (Model model) {
		
		model.addAttribute("greeting", "Bye friend!");
		
		return "goodbye";
	}
	
}