package com.bsd.oms.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Quotation;

public interface QuotationRepository  extends CrudRepository<Quotation, Long> {

	List<Quotation> getByIdPurchaseRequest(long idPurchaseRequest);

}
