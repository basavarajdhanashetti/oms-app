package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
