package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.PurchaseOrder;

public interface PurchaseOrderRepository  extends CrudRepository<PurchaseOrder, Long> {

	PurchaseOrder getByIdQuotation(long quotationId);

}
