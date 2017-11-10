package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.PurchaseItem;

public interface PurchaseItemRepository  extends CrudRepository<PurchaseItem, Long> {

}
