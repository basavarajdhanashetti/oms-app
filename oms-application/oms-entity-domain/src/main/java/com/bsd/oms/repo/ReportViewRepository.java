package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.ReportView;

public interface ReportViewRepository extends CrudRepository<ReportView, Long> {

	ReportView getByName(String name);
}
