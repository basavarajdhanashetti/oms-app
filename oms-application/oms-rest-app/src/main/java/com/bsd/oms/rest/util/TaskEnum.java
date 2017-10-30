package com.bsd.oms.rest.util;

/**
 * 
 * @author vagrant
 *
 */
public enum TaskEnum {
	ModifyOrderDetails("Modify Order Details");

    private final String taskName;

    TaskEnum(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskName() {
        return this.taskName;
    }
}
