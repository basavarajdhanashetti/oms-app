package com.bsd.oms.rest.util;

/**
 * 
 * @author vagrant
 *
 */
public enum TaskEnum {
	ModifyPurchaseRequest("ModifyPurchaseRequest");

    private final String taskName;

    TaskEnum(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskName() {
        return this.taskName;
    }
}
