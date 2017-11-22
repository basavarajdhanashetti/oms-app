package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.StockRegister;

public interface StockRegisterRepository   extends CrudRepository<StockRegister, Long> {

	List<StockRegister> getByIdInwardItem(long itemId);

}
