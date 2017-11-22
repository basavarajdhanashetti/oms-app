package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.StoreInwardRegister;

public interface StoreInwardRegisterRepository  extends CrudRepository<StoreInwardRegister, Long> {

	
	StoreInwardRegister getByIdPurchaseOrder(long poId);

}
