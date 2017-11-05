package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
