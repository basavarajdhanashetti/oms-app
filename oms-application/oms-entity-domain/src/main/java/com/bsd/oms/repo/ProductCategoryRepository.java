package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.ProductCategory;

public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {

}