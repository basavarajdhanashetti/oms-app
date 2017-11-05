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

	@Value("${jbpms.procurement-container}")
	private String procurementContainer;

	@Value("${jbpms.procurement-pr-process}")
	private String purchaseRequestProcessId;

	public String getBpmServerUrl() {
		return bpmServerUrl;
	}

	public String getBpmServerUserName() {
		return bpmServerUserName;
	}

	public String getBpmServerPassword() {
		return bpmServerPassword;
	}

	public String getPurchaseRequestProcessId() {
		return purchaseRequestProcessId;
	}

	public String getProcurementContainer() {
		return procurementContainer;
	}

}
