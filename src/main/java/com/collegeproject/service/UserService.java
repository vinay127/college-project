package com.collegeproject.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.collegeproject.model.User;

public interface UserService {

	public User readById(long id);

	public User createCustomer(User customer);

	public void validate(User customer);

	public User validateAndCreateCustomer(User customer) throws NoSuchAlgorithmException;

	public User update(User customer) throws NoSuchAlgorithmException;

	public User readByEmail(String email);

	public User readByUserName(String userName);

	public User findByEmailAndPassword(String email, String password);

	public List<User> getAllNonApprovedMerchants();

}
