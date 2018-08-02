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
		return sellerRepository.findBySellerId(id);
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
		List<Seller> result = sellerRepository.findByNameIgnoreCaseAndRecordStatus(name, RecordStatus.Active);
		if (null != result && !result.isEmpty())
			return result.get(0);
		return null;
	}

	@Override
	public List<Seller> readAllInActiveByName(String name) {
		return sellerRepository.findByNameIgnoreCaseAndRecordStatus(name, RecordStatus.InActive);
	}

	@Override
	public Seller update(Seller seller) {
		return validateAndCreate(seller);
	}

	@Override
	public List<Seller> readAllInActiveSellers() {
		return sellerRepository.findByRecordStatus(RecordStatus.InActive);
	}

	@Override
	public Seller getByUser(User user) {
		return sellerRepository.findByUser(user);
	}

}
