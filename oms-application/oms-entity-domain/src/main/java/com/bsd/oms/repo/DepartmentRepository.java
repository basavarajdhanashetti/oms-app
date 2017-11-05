package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Long> {

}
