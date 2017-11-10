package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.QuotationItem;

public interface QuotationItemRepository  extends CrudRepository<QuotationItem, Long> {

}
