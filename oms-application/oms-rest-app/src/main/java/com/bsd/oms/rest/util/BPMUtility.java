package com.bsd.oms.rest.util;

import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.client.KieServicesClient;

public class BPMUtility {

	public static boolean isContainerDeployed(KieServicesClient kieServerClient, String jbpmContainerId){
		boolean deployContainer = true;
		KieContainerResourceList containers = kieServerClient
				.listContainers().getResult();
		// check if the container is not yet deployed, if not deploy it
		if (containers != null) {
			for (KieContainerResource kieContainerResource : containers
					.getContainers()) {
				if (kieContainerResource.getContainerId().equals(
						jbpmContainerId)) {
					System.out.println("\t######### Found container "
							+ jbpmContainerId + " skipping deployment...");
					deployContainer = false;
					break;
				}
			}
		}
		return deployContainer;
	}
}
