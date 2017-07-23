package com.fdmgroup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fdmgroup.model.Exercise;

@Controller
public class MinutesController {

	@RequestMapping(value = "/exercise")
	public String goToExercise() {
		return "addMinutes";
	}
	
	@RequestMapping(value = "/addMinutes")
	public String addMinutes(@ModelAttribute("exercise") Exercise exercise) {
		System.out.println("Did " + exercise.getMinutes() + " minutes!");
		return "addMinutes";
	}
	
//	@RequestMapping(value = "/addMoreMinutes")
//	public String addMoreMinutes(@ModelAttribute("exercise") Exercise exercise) {
//		System.out.println("I SAID, did " + exercise.getMinutes() + " minutes!");
//		return "addMinutes";
//	}
	
}
