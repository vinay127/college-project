package com.collegeproject.service;

import java.util.List;

import com.collegeproject.model.Seller;
import com.collegeproject.model.User;

public interface SellerService {

	public Seller createSeller(Seller seller);

	public Seller readById(long id);

	public void validate(Seller seller);

	public Seller validateAndCreate(Seller seller);

	public Seller readActiveByName(String name);

	public List<Seller> readAllInActiveByName(String name);

	public Seller update(Seller seller);

	public List<Seller> readAllInActiveSellers();

	public Seller getByUser(User user);

}
