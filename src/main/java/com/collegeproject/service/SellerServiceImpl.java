package com.collegeproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegeproject.codetype.RecordStatus;
import com.collegeproject.model.Seller;
import com.collegeproject.model.User;
import com.collegeproject.repository.SellerRepository;

@Transactional
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	UserService userService;

	@Autowired
	SellerRepository sellerRepository;

	@Override
	public Seller createSeller(Seller seller) {
		User user = userService.readById(seller.getUser().getUserid());
		if (null == user) {
			user = userService.createCustomer(seller.getUser());
		}
		seller.setUser(user);

		return sellerRepository.save(seller);
	}

	@Override
	public Seller readById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validate(Seller seller) {
		List<Object> args = new ArrayList<Object>();
		args.add(seller.getClass().getSimpleName() + " name");
		args.add(seller.getName());
		List<Seller> caterers = sellerRepository.findByNameIgnoreCaseAndRecordStatus(seller.getName(),
				RecordStatus.Active);
		if (seller.getName() == null || seller.getName().isEmpty()) {
			// transactionInfo.generateException("EMPTY_FILED", args,
			// NotificationInfo.ERROR, 501);
		} else if (null != caterers && !caterers.isEmpty()) {
			// transactionInfo.generateException("ALREADY_EXISTS", args,
			// NotificationInfo.ERROR, 501);
		}
	}

	@Override
	public Seller validateAndCreate(Seller seller) {
		validate(seller);
		return createSeller(seller);
	}

	@Override
	public Seller readActiveByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> readAllInActiveByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seller update(Seller seller) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> readAllInActiveCaterers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Seller getByUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

}
