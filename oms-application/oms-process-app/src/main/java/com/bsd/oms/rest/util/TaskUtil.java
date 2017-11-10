package com.bsd.oms.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.bsd.oms.process.PRQuotations;
import com.bsd.oms.process.PurchaseRequest;
import com.bsd.oms.utils.TaskEnum;

public class TaskUtil {

	/**
	 * 
	 * @param taskName
	 * @param taskData
	 * @return
	 */
	public static Map<String, Object> getMapperByTask(String taskName,
			Map<String, Object> taskData) {
		System.out.println("taskName:"+ taskName+", Task Data: "+ taskData);
		Map<String, Object> outputData = new HashMap<String, Object>();
		outputData.putAll(taskData);
		ObjectMapper objMapper = new ObjectMapper();
		if(taskName.equals(TaskEnum.ModifyPurchaseRequest.getTaskName()) ||
				taskName.equals(TaskEnum.ApprovePurchaseRequest.getTaskName())){
			outputData.put("purchaseRequestOUT",	objMapper.convertValue(taskData.get("purchaseRequestOUT"),PurchaseRequest.class));
		}else if(taskName.equals(TaskEnum.QuotationSubmission.getTaskName())){
			outputData.put("prQuotationsOUT",	objMapper.convertValue(taskData.get("prQuotationsOUT"),PRQuotations.class));
		}
		System.out.println("Converted Task Data: "+outputData);
		return outputData;
	}

}
