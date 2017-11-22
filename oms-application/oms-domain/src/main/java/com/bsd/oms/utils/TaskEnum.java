package com.bsd.oms.utils;

/**
 * 
 * @author Basavaraj Dhanashetti
 *
 */
public enum TaskEnum {
	
	/**
	 * Procurement Process Human Tasks
	 */
	ModifyPurchaseRequest("Modify Purchase Request"),
	ApprovePurchaseRequest("Approve Purchase Request"),
	QuotationSubmission("Quotation Submission"),
	QuotationSelection("Quotation Selection"),
	AmendQuotation("Amend Quotation"),
	QuotationApproval("Quotation Approval"),
	PurchaseOrderApproval("Purchase Order Approval"),
	InwardMaterials("Inward Materials"),
	InwardMaterialsApproval("Inward Materials Approval"),
	StockRegistration("Stock Registration");

    private final String taskName;

    TaskEnum(String taskName) {
        this.taskName = taskName;
    }
    
    public String getTaskName() {
        return this.taskName;
    }
}
