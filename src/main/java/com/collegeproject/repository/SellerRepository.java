package com.collegeproject.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.collegeproject.codetype.RecordStatus;
import com.collegeproject.model.Seller;
import com.collegeproject.model.User;

@Repository
public interface SellerRepository extends CrudRepository<Seller, Serializable> {

	public Seller findBySellerId(long sellerId);

	public List<Seller> findByNameIgnoreCaseAndRecordStatus(String name, RecordStatus recordStatus);

	public List<Seller> findByRecordStatus(RecordStatus recordStatus);

	public Seller findByUser(User user);
}
