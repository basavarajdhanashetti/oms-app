package com.bsd.web.orders;

import java.util.HashMap;
import java.util.Map;

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

import com.bsd.oms.domain.Message;
import com.bsd.oms.process.OrderDetails;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
@Controller
@RequestMapping("/process/orders")
public class OrderControler {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderControler.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${oms-rest-url}")
	private String omsRestRootURL;
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String newOrder(Model model) {
		System.out.println("Presenting Order Process Form.");
		model.addAttribute("orderDetails", getOrderDetails());
		return "orderForm";
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, path="/{taskId}")
	public String newOrder(Model model,@PathVariable long taskId) {
		System.out.println("Getting Order details");
		
		ResponseEntity<OrderDetails> orderDetailsResp = restTemplate.getForEntity(
				this.omsRestRootURL + "/process/orders/"+taskId, OrderDetails.class);

		if (orderDetailsResp.getStatusCode() == HttpStatus.OK) {
			OrderDetails orderDetails = orderDetailsResp.getBody();
			orderDetails.setTaskId(taskId);
			model.addAttribute("orderDetails", orderDetails);
		} else {
			model.addAttribute("message",
					"Problem fetching tasks for you. Please contact Admin.");
		}
		return "orderForm";
	}
	
	private OrderDetails getOrderDetails() {
		OrderDetails details = new OrderDetails();
		details.setCustomerId(1l);
		details.setDeliveryAddressId(1l);
		details.setDiscount(0.1);
		details.setId(1l);
		details.setIsOrderPlaced(true);
		details.setIsPaymentReceived(true);
		details.setOrderDate("2014-10-25");
		details.setProductId(1l);
		details.setProductName("Prod");
		details.setQuantity(6);
		details.setSalePrice(540.0);
		details.setUnitPrice(100.0);
		return details;
	}

	/**
	 * 
	 * @param model
	 * @param orderDetails
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String processOrder(@ModelAttribute OrderDetails orderDetails,
			Model model) {
		System.out.println("Received order with details: "
				+ orderDetails.toString());
		orderDetails.setIsOrderPlaced(true);
		ResponseEntity<Long> processResponse = restTemplate.postForEntity(this.omsRestRootURL+"/process/orders", orderDetails, Long.class);
		
		if(processResponse.getStatusCode() == HttpStatus.CREATED){
			String processEntityURL = processResponse.getHeaders().getFirst("location");
			LOG.debug("Order process URL:" + processEntityURL);
			model.addAttribute("message", "Process Initiated Successfully");
		}else{
			model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}

	/**
	 * 
	 * @param model
	 * @param orderDetails
	 * @return
	 */
	@RequestMapping(path="/complete", method = RequestMethod.POST)
	public String completeOrderTask(@ModelAttribute OrderDetails orderDetails,
			Model model, HttpSession session) {
		System.out.println("Updated order with details: "
				+ orderDetails.toString());
		orderDetails.setIsOrderPlaced(true);
		
		String userId = (String) session.getAttribute("userId");
		
		if(userId == null){
			userId = "bpmsAdmin";
		}
		Map<String, Object> taskData = new HashMap<String, Object>();
		
		taskData.put("orderOut", orderDetails);
		
		ResponseEntity<Message> processResponse = restTemplate.postForEntity(this.omsRestRootURL+"/process/"+userId +"/tasks/"+orderDetails.getTaskId()+"/complete", taskData, Message.class);
		
		if(processResponse.getStatusCode() == HttpStatus.OK){
			LOG.debug("Task completed now.");
			model.addAttribute("message", "Process Re-initiated Successfully ("+processResponse.getBody().getMessage()+")");
		}else{
			model.addAttribute("message", "Problem initiating order process. Please contact Admin.");
		}
		return "redirect:/process/tasks";
	}
}
