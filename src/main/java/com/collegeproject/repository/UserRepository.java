package com.collegeproject.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.collegeproject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Serializable> {

	public User findByUserid(long id);

	public User findByEmail(String email);

	public User findByUserName(String userName);

	public User findByEmailAndPassword(String email, String password);

}
