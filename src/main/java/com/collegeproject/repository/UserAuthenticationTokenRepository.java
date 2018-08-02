package com.collegeproject.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.collegeproject.codetype.RecordStatus;
import com.collegeproject.model.User;
import com.collegeproject.model.UserAuthenticationToken;

@Repository
public interface UserAuthenticationTokenRepository extends JpaRepository<UserAuthenticationToken, Serializable> {

	public UserAuthenticationToken findByToken(String token);

	public UserAuthenticationToken findByUser(User user);

	public UserAuthenticationToken findByUserAndToken(User user, String token);

	public UserAuthenticationToken findByUserAndStatus(User user, RecordStatus status);

}
