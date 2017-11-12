package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Report;

public interface ReportRepository  extends CrudRepository<Report, Long> {

	List<Report> getByUserId(String userId);

}

