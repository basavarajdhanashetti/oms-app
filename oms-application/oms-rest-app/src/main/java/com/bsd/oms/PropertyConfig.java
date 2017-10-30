package com.bsd.oms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertyConfig {

	@Value("${jbpms.serverUrl}")
	private String bpmServerUrl;

	@Value("${jbpms.userName}")
	private String bpmServerUserName;

	@Value("${jbpms.password}")
	private String bpmServerPassword;

	@Value("${jbpms.containerId}")
	private String jbpmContainerId;

	@Value("${jbpms.order-validation-processId}")
	private String orderValidationProcessId;

	public String getBpmServerUrl() {
		return bpmServerUrl;
	}

	public String getBpmServerUserName() {
		return bpmServerUserName;
	}

	public String getJbpmContainerId() {
		return jbpmContainerId;
	}

	public String getOrderValidationProcessId() {
		return orderValidationProcessId;
	}

	public String getBpmServerPassword() {
		return bpmServerPassword;
	}

}
