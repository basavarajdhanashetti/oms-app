package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.PurchaseOrderItem;

public interface PurchaseOrderItemRepository  extends CrudRepository<PurchaseOrderItem, Long> {

}
