package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.HumanTaskMapping;

public interface HumanTaskMappingRepository  extends CrudRepository<HumanTaskMapping, Long> {

	HumanTaskMapping getByTaskName(String taskName);
	
}
