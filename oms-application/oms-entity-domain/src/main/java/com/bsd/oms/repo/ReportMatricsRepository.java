package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.ReportMatrics;

public interface ReportMatricsRepository extends CrudRepository<ReportMatrics, Long> {

	List<ReportMatrics> getByIdReport(long idReport);

}