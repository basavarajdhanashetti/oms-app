package com.bsd.jbpm.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.KieContainerResourceList;
import org.kie.server.api.model.KieContainerStatus;
import org.kie.server.api.model.KieScannerResource;
import org.kie.server.api.model.KieScannerStatus;
import org.kie.server.api.model.KieServerInfo;
import org.kie.server.api.model.ReleaseId;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.api.model.ServiceResponse.ResponseType;
import org.kie.server.api.model.definition.ProcessDefinition;
import org.kie.server.api.model.instance.ProcessInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.UserTaskServicesClient;

import com.bsd.oms.process.OrderDetails;

public class DecisionServer {

	private static final String URL = "http://localhost:8230/kie-server/services/rest/server";
	private static final String USER = "bpmsAdmin";
	private static final String PASSWORD = "bpmsuite1!";
	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;
	private KieServicesConfiguration conf;
	private KieServicesClient kieServicesClient;

	public DecisionServer() {
		conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD,
				3000);
		// If you use custom classes, such as Obj.class, add them to the
		// configuration
		Set<Class<?>> extraClassList = new HashSet<Class<?>>();
		extraClassList.add(OrderDetails.class);
		conf.addExtraClasses(extraClassList);
		conf.setMarshallingFormat(FORMAT);
		kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
	}

	public static void main(String[] args) {
		DecisionServer server = new DecisionServer();
		server.listCapabilities();
		server.listContainers();
		//server.disposeAndCreateContainer();
		server.listProcesses();
		//server.startProcess();
		//server.completeTaskInDetails();
	}

	/**
	 * 
	 */
	public void listCapabilities() {
		KieServerInfo serverInfo = kieServicesClient.getServerInfo()
				.getResult();
		System.out.print("Server capabilities:");
		for (String capability : serverInfo.getCapabilities()) {
			System.out.print(" " + capability);
		}
		System.out.println();
	}

	/**
	 * 
	 */
	public void listContainers() {
		KieContainerResourceList containersList = kieServicesClient
				.listContainers().getResult();
		List<KieContainerResource> kieContainers = containersList
				.getContainers();
		System.out.println("Available containers: ");
		for (KieContainerResource container : kieContainers) {
			System.out.println("\t" + container.getContainerId() + " ("
					+ container.getReleaseId() + ")");
		}
	}

	/**
	 * 
	 */
	public void disposeAndCreateContainer() {
		System.out.println("== Disposing and creating containers ==");
		/*List<KieContainerResource> kieContainers = kieServicesClient
				.listContainers().getResult().getContainers();
		if (kieContainers.size() == 0) {
			System.out.println("No containers available...");
			return;
		}*/

		//KieContainerResource container = kieContainers.get(0);
		String containerId = "oms-process";//container.getContainerId();
		//container.setScanner(new KieScannerResource(KieScannerStatus.STARTED));
		ServiceResponse<Void> responseDispose = kieServicesClient
				.disposeContainer(containerId);
		if (responseDispose.getType() == ResponseType.FAILURE) {
			System.out.println("Error disposing " + containerId + ". Message:");
			System.out.println(responseDispose.getMsg());
			return;
		}

		// String containerId = "oms-process"; 
	/*	 KieContainerResource container	  = new KieContainerResource(); 
		 container.setReleaseId(new ReleaseId("com.bsd.oms", "oms-process", "1.0.0"));
		  //container.setResolvedReleaseId(new ReleaseId("com.bsd.oms",  "oms-process", "1.0.0")); 
		 container.setContainerId(containerId);
		 	 
		container.setStatus(KieContainerStatus.STARTED);
		System.out.println("Success Disposing container " + containerId);
		System.out.println("Trying to recreate the container...");
		ServiceResponse<KieContainerResource> createResponse = kieServicesClient.createContainer(containerId, container);
		if (createResponse.getType() == ResponseType.FAILURE) {
			System.out.println("Error creating " + containerId + ". Message:");
			System.out.println(createResponse.getMsg());
			return;
		} */

		System.out.println("Container recreated with success!");
	}

	/**
	 * 
	 */
	public void listProcesses() {
		System.out.println("== Listing Business Processes ==");
		QueryServicesClient queryClient = kieServicesClient
				.getServicesClient(QueryServicesClient.class);

		List<ProcessDefinition> findProcessesByContainerId = queryClient
				.findProcessesByContainerId("oms-container", 0, 1000);

		for (ProcessDefinition def : findProcessesByContainerId) {
			System.out.println(def.getName() + " - " + def.getId() + " v"
					+ def.getVersion());

		}
		System.out.println("== Listing Process Instances ==");
		List<ProcessInstance> instances = queryClient.findProcessInstances(0,
				10);
		for (ProcessInstance processInstance : instances) {
			System.out.println("ContainerId:"
					+ processInstance.getContainerId() + ", ID:"
					+ processInstance.getId() + ", ProcessId:"
					+ processInstance.getProcessId() + ",ProcessName:"
					+ processInstance.getProcessName() + ", status:"
					+ processInstance.getState());
		}
	}

	/**
	 * 
	 */
	public void startProcess() {

		ProcessServicesClient processServicesClient = kieServicesClient
				.getServicesClient(ProcessServicesClient.class);
		// Create an instance of the custom class
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("order", getOrderDetails());
		// Start the process with custom class
		long id =  processServicesClient.startProcess("oms-container",
				"OMS-Process.OrderProcessing", variables);
		System.out.println("Process started with id:" + id);
	}

	private OrderDetails getOrderDetails() {
		OrderDetails details = new OrderDetails();
		details.setCustomerId(1l);
		details.setDeliveryAddressId(1l);
		details.setDiscount(17.9);
		details.setId(1l);
		details.setIsOrderPlaced(true);
		details.setIsPaymentReceived(true);
		details.setOrderDate("2017/10/25");
		details.setProductId(1l);
		details.setProductName("Prod");
		details.setQuantity(6);
		details.setSalePrice(540.0);
		details.setUnitPrice(100.0);
		return details;
	}

	/**
	 * @return
	 * 
	 */
	public void completeTaskInDetails() {
		UserTaskServicesClient taskClient = kieServicesClient
				.getServicesClient(UserTaskServicesClient.class);

		List<TaskSummary> mytasks = taskClient.findTasks(USER, 0, 10);

		for (TaskSummary taskSummary : mytasks) {
			Map<String, Object> inputData = taskClient
					.getTaskInputContentByTaskId("oms-container",
							taskSummary.getId());

			System.out.println("status:" + taskSummary.getStatus());
			OrderDetails order = (OrderDetails) inputData.get("orderIn");
			System.out.println("Order Input for HS: " + order.toString());

			if(taskSummary.getStatus().equals("Reserved")){
				taskClient.startTask("oms-container", taskSummary.getId(), USER);
			}
			order.setDiscount(15.0);
			order.setIsOrderPlaced(true);

			Map<String, Object> outputData = new HashMap<String, Object>();
			outputData.put("orderOut", order);

			if (taskSummary.getStatus().equals("InProgress")) {
				System.out.println("Completing task id:" + taskSummary.getId());
				taskClient.completeTask("oms-container", taskSummary.getId(),
						USER, outputData);
			}
		}
	}

}
