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
import com.ats.rusaadmin.model.GetSubCategory;
import com.ats.rusaadmin.model.Info;
import com.ats.rusaadmin.model.Section;
import com.ats.rusaadmin.model.SubCategory;

@Controller
@Scope("session")
public class MasterController {
	
	RestTemplate rest = new RestTemplate();
	GetCategory editcat = new GetCategory();
	Section editSection = new Section();
	GetSubCategory editSubCategory = new GetSubCategory();
	
	@RequestMapping(value = "/addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addCategory");
		try {
			editcat = new GetCategory();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("delStatus", 1);
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

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			
			 

			if (catId == "" || catId == null) {
				editcat.setCatId(0);
				editcat.setCatAddDate(sf.format(date));
			}else {
				editcat.setCatId(Integer.parseInt(catId));
				editcat.setCatEditDate(sf.format(date));
			}
			editcat.setCatDesc(catDesc);
			editcat.setCatCode(catCode);
			editcat.setCatName(catName);
			editcat.setSectionId(sectionId);
			editcat.setCatSortNo(seqNo); 
			editcat.setIsActive(1);
			editcat.setDelStatus(1);
			System.out.println("category" + editcat);

			Category res = rest.postForObject(Constant.url + "/saveUpdateCategory", editcat, Category.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addCategory";
	}
	
	@RequestMapping(value = "/editCategory/{catId}", method = RequestMethod.GET)
	public ModelAndView editCategory(@PathVariable int catId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addCategory");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("catId", catId);

			 editcat = rest.postForObject(Constant.url + "/getCategoryByCatId", map, GetCategory.class);
			model.addObject("editCategory", editcat);

			System.out.println(editcat);
			
			Section[] section = rest.getForObject(Constant.url + "/getAllSectionList", 
					Section[].class);
			List<Section> sectionList = new ArrayList<Section>(Arrays.asList(section));
			model.addObject("sectionList", sectionList);
			
			 map = new LinkedMultiValueMap<String, Object>();
			map.add("delStatus", 1);
			GetCategory[] category = rest.postForObject(Constant.url + "/getAllCatList", map,
					GetCategory[].class);
			List<GetCategory> categoryList = new ArrayList<GetCategory>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteCategory/{catId}", method = RequestMethod.GET)
	public String deleteCategory(@PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("catIdList", catId); 
			map.add("delStatus", 0); 
			Info res = rest.postForObject(Constant.url + "/deleteMultiCategory", map, Info.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addCategory";
	}
	
	@RequestMapping(value = "/addSubCategory", method = RequestMethod.GET)
	public ModelAndView addSubCategory(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSubCategory");
		try {
			editSubCategory = new GetSubCategory();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("delStatus", 1);
			GetCategory[] category = rest.postForObject(Constant.url + "/getAllCatList", map,
					GetCategory[].class);
			List<GetCategory> categoryList = new ArrayList<GetCategory>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);
			
			GetSubCategory[] getSubCategory = rest.postForObject(Constant.url + "/getAllSubCatList", map,
					GetSubCategory[].class);
			List<GetSubCategory> subCategoryList = new ArrayList<GetSubCategory>(Arrays.asList(getSubCategory));
			model.addObject("subCategoryList", subCategoryList);
 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}
	
	@RequestMapping(value = "/insertSubCategory", method = RequestMethod.POST)
	public String insertSubCategory(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/addEmployee");
		try {

			String subCatId = request.getParameter("subCatId");
			String subCatCode = request.getParameter("subCatCode");
			String subCatDesc = request.getParameter("subCatDesc");
			String subCatName = request.getParameter("subCatName");
			int catId = Integer.parseInt(request.getParameter("catId"));
			int seqNo = Integer.parseInt(request.getParameter("seqNo"));

			Date date = new Date();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			
			

			if (subCatId == "" || subCatId == null) {
				editSubCategory.setSubCatId(0);
				editSubCategory.setSubCatAddDate(sf.format(date));
			}else {
				editSubCategory.setSubCatId(Integer.parseInt(subCatId));
				editSubCategory.setSubCatEditDate(sf.format(date));
			}
			editSubCategory.setCatId(catId); 
			editSubCategory.setSubCatCode(subCatCode);
			editSubCategory.setSubCatDesc(subCatDesc);
			editSubCategory.setSubCatName(subCatName);
			editSubCategory.setSubCatSortNo(seqNo);
			editSubCategory.setIsActive(1);
			editSubCategory.setDelStatus(1);
			System.out.println("sub category" + editSubCategory);

			SubCategory res = rest.postForObject(Constant.url + "/saveUpdateSubCategory", editSubCategory, SubCategory.class);

			System.out.println("res " + res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSubCategory";
	}
	
	@RequestMapping(value = "/editSubCategory/{subCatId}", method = RequestMethod.GET)
	public ModelAndView editSubCategory(@PathVariable int subCatId, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSubCategory");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subCatId", subCatId);
			map.add("delStatus", 1);
			editSubCategory = rest.postForObject(Constant.url + "/getSubCategoryBySubCatId", map, GetSubCategory.class);
			model.addObject("editSubCategory", editSubCategory);

			System.out.println(editSubCategory);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("delStatus", 1);
			GetCategory[] category = rest.postForObject(Constant.url + "/getAllCatList", map,
					GetCategory[].class);
			List<GetCategory> categoryList = new ArrayList<GetCategory>(Arrays.asList(category));
			model.addObject("categoryList", categoryList);
			
			GetSubCategory[] getSubCategory = rest.postForObject(Constant.url + "/getAllSubCatList", map,
					GetSubCategory[].class);
			List<GetSubCategory> subCategoryList = new ArrayList<GetSubCategory>(Arrays.asList(getSubCategory));
			model.addObject("subCategoryList", subCategoryList);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;
	}

	@RequestMapping(value = "/deleteSubCategory/{subCatId}", method = RequestMethod.GET)
	public String deleteSubCategory(@PathVariable int subCatId, HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView model = new ModelAndView("masters/empDetail");
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("subCatIdList", subCatId); 
			map.add("delStatus", 0); 
			Info res = rest.postForObject(Constant.url + "/deleteMultiSubCategory", map, Info.class);
			System.out.println(res);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/addSubCategory";
	}
	
	@RequestMapping(value = "/addSection", method = RequestMethod.GET)
	public ModelAndView addSection(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("master/addSection");
		try {
			
			 editSection = new Section();
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
				editSection.setSectionId(0);
				editSection.setSectionAddDate(sf.format(date));
			}else {
				editSection.setSectionId(Integer.parseInt(sectionId));
				editSection.setSectionEditDate(sf.format(date));
			} 
			editSection.setSectionName(sectionName);
			editSection.setSectionNo(sectionNo);
			editSection.setSectionDesc(sectionDesc);
			editSection.setSectionSortNo(seqNo); 
			editSection.setSectionDateTime(sf.format(date));
			editSection.setDelStatus(1);
			editSection.setIsActive(1);
			
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

			 editSection = rest.postForObject(Constant.url + "/getSectionBySectionId", map, Section.class);
			model.addObject("editSection", editSection);

			System.out.println(editSection);
			
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
