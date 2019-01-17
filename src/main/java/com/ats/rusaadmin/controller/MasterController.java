package com.ats.rusaadmin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class MasterController {
	
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addCategory");
		try {
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addSubCategory", method = RequestMethod.GET)
	public ModelAndView addSubCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSubCategory");
		try {
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/addSection", method = RequestMethod.GET)
	public ModelAndView addSection(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSection");
		try {
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

}
