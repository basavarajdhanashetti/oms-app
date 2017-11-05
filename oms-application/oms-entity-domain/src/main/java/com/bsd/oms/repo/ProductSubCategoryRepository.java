package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.ProductSubCategory;

public interface ProductSubCategoryRepository extends CrudRepository<ProductSubCategory, Long> {

	List<ProductSubCategory> getByProductCategoryId(long productCategoryId );
}
