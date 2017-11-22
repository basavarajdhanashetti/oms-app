package com.bsd.oms.controllers;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.entity.Department;
import com.bsd.oms.service.EntityService;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class DepartmentController {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityService entityService;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/departments")
	public String getDepartments(Model model, HttpSession session) {
		LOG.debug("In getDepartments");
		Department[] departmentLst = entityService.getByURL(this.omsRestRootURL + "/departments", Department[].class);
		if (departmentLst != null) {
			model.addAttribute("departmentLst", Arrays.asList(departmentLst));
		} else {
			model.addAttribute("no.result", "Product Categories are not configured.");
		}
		return "departmentLst";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/departments/{id}")
	public String getNewDepartment(Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In getNewDepartment");

		Department department = null;
		if (id == 0) {
			department = new Department();
		} else {
			department = entityService.getByURL(this.omsRestRootURL + "/departments/" + id, Department.class);
		}
		model.addAttribute("departmentForm", department);
		return "departmentAddUpdate";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/departments")
	public String saveDepartment(Model model, HttpSession session, @ModelAttribute Department department) {
		LOG.debug("In saveDepartment");
		if (department.getId() == 0) {
			entityService.postByURL(this.omsRestRootURL + "/departments", department, Department.class);
		} else {
			entityService.putByURL(this.omsRestRootURL + "/departments/" + department.getId(), department);
		}
		return "redirect:/departments";
	}
}
