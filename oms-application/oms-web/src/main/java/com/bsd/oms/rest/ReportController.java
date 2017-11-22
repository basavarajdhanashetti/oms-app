package com.bsd.oms.rest;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.chart.ChartContent;
import com.bsd.oms.entity.Report;
import com.bsd.oms.entity.User;


@Controller
@RequestMapping("/api/reports")
public class ReportController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${oms-rest-url}")
	private String omsRestRootURL;
	
	/**
	 * 
	 * @return
	 */
	@GetMapping
	public @ResponseBody ResponseEntity<?> getAllReports(HttpSession session){
		
		User user = (User) session.getAttribute("Session_UserDetails");
		
		if(user == null){
			return ResponseEntity.status(500).build();
		}
		
		ResponseEntity<Report[]> reportResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/users/"+user.getUserName()+"/reports", Report[].class);
		
		if (reportResp.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(Arrays.asList(reportResp.getBody()));
		} else {
			return ResponseEntity.status(500).build();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<?> getReport(@PathVariable long id, HttpSession session){
		
		ResponseEntity<ChartContent> reportResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/reports/" + id +"/content", ChartContent.class);
		
		if (reportResp.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(reportResp.getBody());
		} else {
			return ResponseEntity.status(500).build();
		}
	}
}
