package com.ats.rusaadmin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.ats.rusaadmin.common.Constant;
import com.ats.rusaadmin.model.Category;
import com.ats.rusaadmin.model.GetCategory;
import com.ats.rusaadmin.model.Info;
import com.ats.rusaadmin.model.Section;

@Controller
@Scope("session")
public class MasterController {
	
	RestTemplate rest = new RestTemplate();
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addCategory");
		try {
 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("delStatus", 0);
			GetCategory[] category = rest.postForObject(Constant.url + "/getAllCatList", map,
					GetCategory[].class);
			List<GetCategory> categoryList = new ArrayList<GetCategory>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);
			
			Section[] section = rest.getForObject(Constant.url + "/getAllSectionList", 
					Section[].class);
			List<Section> sectionList = new ArrayList<Section>(Arrays.asList(section));
			model.addObject("sectionList", sectionList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/insertCategory", method = RequestMethod.POST)
	public String insertCategory(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String catId = request.getParameter("catId");
			String catCode = request.getParameter("catCode");
			String catDesc = request.getParameter("catDesc");
			String catName = request.getParameter("catName");
			int sectionId = Integer.parseInt(request.getParameter("sectionId"));
			int seqNo = Integer.parseInt(request.getParameter("seqNo"));

			Category category = new Category();

			if (catId == "" || catId == null)
				category.setCatId(0);
			else
				category.setCatId(Integer.parseInt(catId));
			category.setCatDesc(catDesc);
			category.setCatCode(catCode);
			category.setCatName(catName);
			category.setSectionId(sectionId);
			category.setCatSortNo(seqNo);
			
			System.out.println("category" + category);

			Category res = rest.postForObject(Constant.url + "/saveUpdateCategory", category, Category.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addCategory";
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
			
			 
			Section[] section = rest.getForObject(Constant.url + "/getAllSectionList", 
					Section[].class);
			List<Section> sectionList = new ArrayList<Section>(Arrays.asList(section));
			model.addObject("sectionList", sectionList);
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/insertSection", method = RequestMethod.POST)
	public String insertSection(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd"); 
			
			String sectionId = request.getParameter("sectionId");
			String sectionNo = request.getParameter("sectionNo");
			String sectionName = request.getParameter("sectionName");
			String sectionDesc = request.getParameter("sectionDesc");
			String seqNo =  request.getParameter("seqNo") ; 
			Section section = new Section();

			if (sectionId == "" || sectionId == null) {
				section.setSectionId(0);
			}else {
				section.setSectionId(Integer.parseInt(sectionId));
				section.setSectionEditDate(sf.format(date));
			} 
			section.setSectionName(sectionName);
			section.setSectionNo(sectionNo);
			section.setSectionDesc(sectionDesc);
			section.setSectionSortNo(seqNo);
			section.setSectionAddDate(sf.format(date));
			section.setSectionDateTime(sf.format(date));
			section.setDelStatus(1);
			section.setIsActive(1);
			
			System.out.println("section" + section);

			Info res = rest.postForObject(Constant.url + "/saveSection", section, Info.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSection";
	}
	
	@RequestMapping(value = "/editSection/{sectionId}", method = RequestMethod.GET)
	public ModelAndView editSection(@PathVariable int sectionId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSection");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("sectionId", sectionId);

			Section res = rest.postForObject(Constant.url + "/getSectionBySectionId", map, Section.class);
			model.addObject("editSection", res);

			System.out.println(res);
			
			Section[] section = rest.getForObject(Constant.url + "/getAllSectionList", 
					Section[].class);
			List<Section> sectionList = new ArrayList<Section>(Arrays.asList(section));
			model.addObject("sectionList", sectionList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteSection/{sectionId}", method = RequestMethod.GET)
	public String deleteSection(@PathVariable int sectionId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("sectionId", sectionId); 
			Info res = rest.postForObject(Constant.url + "/deleteSection", map, Info.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSection";
	}

}
