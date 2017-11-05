package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	List<Product> getByProductSubCategoryId(long productSubCategoryId);

}
