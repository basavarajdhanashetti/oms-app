package com.bsd.oms.jbpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.bsd.oms.PropertyConfig;

@Component
public class JBPMConnectionFactory {

	@Autowired
	private PropertyConfig propertyConfig;
	
	@Bean(name = "jbpmConnection")
	public JBPMConnection publicInstance() {
		System.out.println("Creating instance for JBPMCOnnection");
		return JBPMConnection.getInstance(propertyConfig);
	}
}
