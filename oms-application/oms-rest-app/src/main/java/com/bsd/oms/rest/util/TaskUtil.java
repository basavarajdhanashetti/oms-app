package com.bsd.oms.rest.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.bsd.oms.process.OrderDetails;

public class TaskUtil {

	/**
	 * 
	 * @param taskName
	 * @param taskData
	 * @return
	 */
	public static Map<String, Object> getMapperByTask(String taskName,
			Map<String, Object> taskData) {
		System.out.println("Task Data: "+ taskData);
		Map<String, Object> outputData = new HashMap<String, Object>();
		ObjectMapper objMapper = new ObjectMapper();
		if(taskName.equals(TaskEnum.ModifyOrderDetails.getTaskName())){
			outputData.put("orderOut",	objMapper.convertValue(taskData.get("orderOut"),OrderDetails.class));
		}
		System.out.println("Converted Task Data: "+outputData);
		return outputData;
	}

}