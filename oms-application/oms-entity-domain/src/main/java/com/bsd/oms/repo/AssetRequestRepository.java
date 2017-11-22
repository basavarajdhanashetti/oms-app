package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.AssetRequest;
import com.bsd.oms.entity.StoreInwardRegister.Status;

public interface AssetRequestRepository  extends CrudRepository<AssetRequest, Long> {

	List<AssetRequest> getByStatus(Status status);
	
	List<AssetRequest> getByUserId(String userId);
	
}
