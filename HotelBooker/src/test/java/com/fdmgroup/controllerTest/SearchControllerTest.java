package com.fdmgroup.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.fdmgroup.controller.SearchController;
import com.fdmgroup.pojo.SearchParameter;

public class SearchControllerTest {

	private SearchController controller;
	private Model model;

	@Before
	public void setUp() throws Exception {
		controller = new SearchController();
		model = new ExtendedModelMap();
	}

	@Test
	public void indexPageReturnsModelAndViewToIndexTest() {
		ModelAndView mv = controller.indexPage(model);
		assertEquals("Wrong view name", "index", mv.getViewName());
	}
	
	@Test
	public void indexPageAddsNewSearchParameterToModelTest() {
		ModelAndView mv = controller.indexPage(model);
		Map<String, Object> modelMap = mv.getModel();
		assertTrue("No attribute added named \"searchParameter\"", modelMap.containsKey("searchParameter"));
		assertTrue("\"searchParameter\" attribute is of the wrong type",
			modelMap.get("searchParameter") instanceof SearchParameter);
	}

}
