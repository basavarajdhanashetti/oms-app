package com.bsd.oms.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.entity.Report;
import com.bsd.oms.entity.ReportMatrics;
import com.bsd.oms.entity.User;
import com.bsd.oms.service.EntityService;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
public class CustomReportController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomReportController.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EntityService entityService;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/reports")
	public String getMyReports(Model model, HttpSession session) {
		LOG.debug("In getMyReports ");
		User user = (User) session.getAttribute("Session_UserDetails");

		ResponseEntity<Report[]> reportResp = restTemplate.getForEntity(this.omsRestRootURL + "/users/" + user.getUserName() + "/reports",
				Report[].class);

		if (reportResp.getStatusCode() == HttpStatus.OK) {
			model.addAttribute("reportsList", reportResp.getBody());
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
			return "redirect:/process/tasks";
		}
		return "myReportsForm";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reports")
	public String saveReports(@ModelAttribute Report report, Model model, HttpSession session) {
		LOG.debug("In getMyReports ");

		User user = (User) session.getAttribute("Session_UserDetails");

		if (report.getId() == 0) {
			ResponseEntity<Report> reportResp = restTemplate.postForEntity(this.omsRestRootURL + "/users/" + user.getUserName()
					+ "/reports", report, Report.class);
			if (reportResp.getStatusCode() == HttpStatus.OK) {
				model.addAttribute("message", "Custom Report Configured Successfully.");
			} else {
				model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
			}
		} else {
			restTemplate.put(this.omsRestRootURL + "/users/" + user.getUserName() + "/reports/" + report.getId(), report);
		}
		return "redirect:/reports";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/reports/{id}")
	public String getReportByID(Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In getReportByID ");

		if (id == 0) {
			model.addAttribute("reportForm", new Report());
			model.addAttribute("tableViewsList", entityService.getTableViews());
			return "myReportViewForm";
		}

		ResponseEntity<Report> reportResp = restTemplate.getForEntity(this.omsRestRootURL + "/reports/" + id, Report.class);

		if (reportResp.getStatusCode() == HttpStatus.OK) {
			Report report = reportResp.getBody();
			model.addAttribute("reportForm", report);
			model.addAttribute("tableViewsList", entityService.getTableViews());
			model.addAttribute("tableViewColumnsList", entityService.getReportView(report.getIdView()).getViewColumns());
		} else {
			model.addAttribute("message", "Problem fetching tasks for you. Please contact Admin.");
			return "redirect:/process/tasks";
		}
		return "myReportViewForm";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/reports/{id}/delete")
	public String deleteReportByID(Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In getReportByID ");
		restTemplate.delete(this.omsRestRootURL + "/reports/" + id);
		return "redirect:/reports";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reports/{id}/refresh")
	public String refresh(@ModelAttribute Report report, Model model, HttpSession session, @PathVariable long id) {
		model.addAttribute("reportForm", report);
		model.addAttribute("tableViewsList", entityService.getTableViews());
		if (report.getIdView() != 0) {
			model.addAttribute("tableViewColumnsList", entityService.getReportView(report.getIdView()).getViewColumns());
		}
		return "myReportViewForm";
	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reports/{id}/metrices")
	public String addMatric(@ModelAttribute Report report, Model model, HttpSession session, @PathVariable long id) {
		LOG.debug("In addNewMatric ");

		if (report.getMatrices() == null) {
			report.setMatrices(new ArrayList<ReportMatrics>());
		}
		ReportMatrics matrics = new ReportMatrics();
		matrics.setIdReport(id);
		report.getMatrices().add(matrics);
		model.addAttribute("reportForm", report);
		model.addAttribute("tableViewsList", entityService.getTableViews());
		if (report.getIdView() != 0) {
			model.addAttribute("tableViewColumnsList", entityService.getReportView(report.getIdView()).getViewColumns());
		}
		return "myReportViewForm";

	}

	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/reports/{reportId}/metrices/{index}")
	public String removeMatric(@ModelAttribute Report report, Model model, HttpSession session, @PathVariable long reportId,
			@PathVariable int index) {
		LOG.debug("In removeMatric ");

		if (report.getMatrices() == null) {
			report.setMatrices(new ArrayList<ReportMatrics>());
		}

		// set this to 0 then remove in service
		ReportMatrics reportMatrics = report.getMatrices().get(index);
		if (reportMatrics.getId() == 0) {
			report.getMatrices().remove(index);
		} else {
			reportMatrics.setIdReport(0);
		}

		model.addAttribute("reportForm", report);
		model.addAttribute("tableViewsList", entityService.getTableViews());
		if (report.getIdView() != 0) {
			model.addAttribute("tableViewColumnsList", entityService.getReportView(report.getIdView()).getViewColumns());
		}
		return "myReportViewForm";

	}
}
