package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.PurchaseRequest;

public interface PurchaseRequestRepository  extends CrudRepository<PurchaseRequest, Long> {

}
