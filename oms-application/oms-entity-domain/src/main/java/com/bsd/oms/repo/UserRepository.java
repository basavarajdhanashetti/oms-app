package com.bsd.oms.repo;

import org.springframework.data.repository.CrudRepository;

import com.bsd.oms.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User getUserByUserName(String userName);
}
