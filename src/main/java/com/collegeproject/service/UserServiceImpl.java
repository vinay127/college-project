package com.collegeproject.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.collegeproject.codetype.UserRole;
import com.collegeproject.core.PasswordMasker;
import com.collegeproject.model.User;
import com.collegeproject.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordMasker passwordMasker;

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createCustomer(User user) {
		return userRepository.save(user);
	}

	@Override
	public void validate(User customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public User validateAndCreateCustomer(User user) throws NoSuchAlgorithmException {
		if (null != user) {
			validate(user);
			user.setPassword(getHashedPassword(user));
			if (!user.getUserRole().equals(UserRole.Seller)) {
				user.setIsApproved(true);
			} else if (user.getUserRole().equals(UserRole.Seller)) {
				// Caterer caterer = new Caterer();
				// caterer.setUser(user);
				// caterer.setName(user.getUserName());
				// caterer.setDescription(user.getUserName());
				// catererService.validateAndCreate(caterer);
			}
			user = createCustomer(user);
			// createAddresses(customer);
			Map<String, Object> model = new HashMap<>();
			model.put("user", user);
			// emailService.sendEmail(customer, customer.getEmail(), "Successfully
			// Registered",
			// "/templates/email/EmailRegistration.vm", model);
			return user;
		}
		return null;
	}

	private String getHashedPassword(User user) throws NoSuchAlgorithmException {
		return passwordMasker.getHashedPassword(user.getPassword());
	}

	@Override
	public User update(User user) throws NoSuchAlgorithmException {
		user.setPassword(readById(user.getUserid()).getPassword());
		return validateAndCreateCustomer(user);
	}

	@Override
	public User readByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User readByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public User findByEmailAndPassword(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
	}

	@Override
	public List<User> getAllNonApprovedMerchants() {
		return userRepository.findByIsApprovedAndUserRole(false, UserRole.Seller);
	}

	@Override
	public User readById(long id) {
		return userRepository.findByUserid(id);
	}

}
