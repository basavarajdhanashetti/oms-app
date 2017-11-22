package com.bsd.oms.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.bsd.oms.dto.LoginDetails;
import com.bsd.oms.entity.User;
import com.bsd.oms.service.LoginService;


@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	@GetMapping("/")
	public String loginPage(Model model, HttpSession session) {
		LOG.debug("Opening login page");
		model.addAttribute("loginForm", new LoginDetails());
		return "login";
	}

	@PostMapping(path="/login")
	public String validateLogin(@Valid @ModelAttribute(value="loginForm") LoginDetails login, BindingResult result, Model model, HttpSession session){
		if(result.hasErrors()){
			return "login";
		}else{
			User user = loginService.getUserDetails(login.getUserName());
			if(user != null && user.getPassword().equals(login.getPassword())){
				session.setAttribute("Session_UserDetails", user);
				session.setAttribute("Session_Role", user.getRole());
				return "index";
			}else{
				model.addAttribute("errorMsg", "Invalid Credentials.");
				return "login";
			}
		}
	} 
	
	@GetMapping(path="/home")
	public String homePage(@Valid @ModelAttribute(value="loginForm") LoginDetails login, BindingResult result, Model model, HttpSession session){
		User user = (User) session.getAttribute("Session_UserDetails");
		if (user == null) {
			return "redirect:/logout";
		}
		return "index";
	} 
	
	/**
	 * 
	 * @param login
	 * @param result
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping(path="/logout")
	public String logout(@Valid @ModelAttribute(value="loginForm") LoginDetails login, BindingResult result, Model model, HttpSession session){
		session.invalidate();
		return "login";
	} 
}
