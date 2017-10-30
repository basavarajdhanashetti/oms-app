package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {

}
