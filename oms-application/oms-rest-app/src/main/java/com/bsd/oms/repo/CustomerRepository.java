package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
