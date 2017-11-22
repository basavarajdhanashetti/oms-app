package com.bsd.oms.controllers;

import java.util.ArrayList;
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

import com.bsd.oms.entity.ReportView;
import com.bsd.oms.entity.ReportViewColumn;
import com.bsd.oms.service.EntityService;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class ViewController {

	private static final Logger LOG = LoggerFactory.getLogger(ViewController.class);

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
	@RequestMapping(method = RequestMethod.GET, path = "/reportingviews")
	public String getReportViews(Model model, HttpSession session) {
		LOG.debug("In getReportViews");
		ReportView[] reportingviewLst = entityService.getByURL(this.omsRestRootURL + "/reportviews", ReportView[].class);
		if (reportingviewLst != null) {
			model.addAttribute("reportingviewList", Arrays.asList(reportingviewLst));
		} else {
			model.addAttribute("no.result", "Reporting Views are not configured.");
		}
		return "reportingviewList";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/reportingviews/{id}")
	public String getNewCategory(Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In getNewCategory");

		ReportView reportingview = null;
		if (id == 0) {
			reportingview = new ReportView();
		} else {
			reportingview = entityService.getByURL(this.omsRestRootURL + "/reportviews/" + id, ReportView.class);
		}
		model.addAttribute("reportingViewForm", reportingview);
		return "reportingviewAddUpdate";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reportingviews")
	public String saveCategory(Model model, HttpSession session, @ModelAttribute ReportView reportingview) {
		LOG.debug("In saveCategory");
		if (reportingview.getId() == 0) {
			entityService.postByURL(this.omsRestRootURL + "/reportviews", reportingview, ReportView.class);
		} else {
			entityService.putByURL(this.omsRestRootURL + "/reportviews/" + reportingview.getId(), reportingview);
		}
		return "redirect:/reportingviews";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reportingviews/{id}/columns/{colId}")
	public String addNewColumn(@ModelAttribute ReportView reportingview, Model model, HttpSession session, @PathVariable long id, @PathVariable long colId) {
		LOG.debug("In getNewCategory");

		if(reportingview.getViewColumns() == null){
			reportingview.setViewColumns(new ArrayList<ReportViewColumn>());
		}
		ReportViewColumn viewColumn = new ReportViewColumn();
		viewColumn.setIdReportView(id);
		reportingview.getViewColumns().add(viewColumn);
		
		model.addAttribute("reportingViewForm", reportingview);
		return "reportingviewAddUpdate";
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reportingviews/{id}/columns/{index}/delete")
	public String deleteColumn(@ModelAttribute ReportView reportingview, Model model, HttpSession session, @PathVariable long id, @PathVariable int index) {
		LOG.debug("In getNewCategory");

		if(reportingview.getViewColumns() == null){
			reportingview.setViewColumns(new ArrayList<ReportViewColumn>());
		}
		
		ReportViewColumn  viewColumn = reportingview.getViewColumns().get(index);
		
		if(viewColumn.getId() == 0){
			reportingview.getViewColumns().remove(index);
		}else{
			viewColumn.setIdReportView(0);
		}
		
		model.addAttribute("reportingViewForm", reportingview);
		return "reportingviewAddUpdate";
	}
}
