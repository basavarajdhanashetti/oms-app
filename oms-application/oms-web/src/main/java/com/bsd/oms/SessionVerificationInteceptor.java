package com.bsd.oms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.bsd.oms.entity.User;

public class SessionVerificationInteceptor implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger(SessionVerificationInteceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) throws Exception {
		// Dont have anything here now

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3) throws Exception {
		// Dont have anything here now

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		final HttpSession session = request.getSession(false);
		if (session == null || session.isNew()) {
			request.getSession(true);
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		} else {
			User user = (User) request.getSession(false).getAttribute("Session_UserDetails");
			if (user == null) {
				LOG.debug("Session doesn't exists. Navigate to login page to re-login.");
				response.sendRedirect(request.getContextPath() + "/logout");
				return false;
			}
		}
		return true;
	}
}