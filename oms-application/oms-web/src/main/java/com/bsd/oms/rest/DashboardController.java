package com.bsd.oms.rest;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.bsd.oms.entity.PurchaseOrder;
import com.bsd.oms.entity.User;
import com.bsd.oms.utils.OMSDateUtil;

@Controller
@RequestMapping("/api/dashboard")
public class DashboardController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${oms-rest-url}")
	private String omsRestRootURL;

	@Value("${oms-process-url}")
	private String omsProcessRootURL;

	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/tasks")
	public @ResponseBody ResponseEntity<?> getTasks(HttpSession session) {

		User user = (User) session.getAttribute("Session_UserDetails");

		if (user == null) {
			return ResponseEntity.status(500).build();
		}

		ResponseEntity<List> taskResponse = restTemplate.getForEntity(this.omsProcessRootURL + "/users/" + user.getUserName() + "/tasks",
				List.class);

		if (taskResponse.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(taskResponse.getBody());
		} else {
			return ResponseEntity.status(500).build();
		}
	}

	/**
	 * 
	 * @return
	 */
	@GetMapping(path = "/purchaseorders")
	public @ResponseBody ResponseEntity<?> getPurchaseOrders(HttpSession session) {

		User user = (User) session.getAttribute("Session_UserDetails");

		if (user == null) {
			return ResponseEntity.status(500).build();
		}

		Calendar toDate = Calendar.getInstance();
		Calendar fromDate = Calendar.getInstance();
		fromDate.add(Calendar.MONTH, -1);

		ResponseEntity<PurchaseOrder[]> taskResponse = restTemplate.getForEntity(this.omsRestRootURL + "/purchaseorders?fromDate="
				+ OMSDateUtil.getDBDateTime(fromDate.getTime()) + "&toDate=" + OMSDateUtil.getDBDateTime(toDate.getTime()),
				PurchaseOrder[].class);

		if (taskResponse.getStatusCode() == HttpStatus.OK) {
			return ResponseEntity.ok(taskResponse.getBody());
		} else {
			return ResponseEntity.status(500).build();
		}
	}
}
